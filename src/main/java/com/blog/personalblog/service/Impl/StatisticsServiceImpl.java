package com.blog.personalblog.service.Impl;

import cn.hutool.core.collection.CollUtil;
import com.blog.personalblog.bo.TagBO;
import com.blog.personalblog.entity.Article;
import com.blog.personalblog.entity.Tag;
import com.blog.personalblog.mapper.StatisticsMapper;
import com.blog.personalblog.service.ArticleService;
import com.blog.personalblog.service.StatisticsService;
import com.blog.personalblog.service.TagService;
import com.blog.personalblog.vo.StatisticsBaseCountVO;
import com.blog.personalblog.vo.StatisticsTagCountVO;
import com.blog.personalblog.vo.StatisticsTopCountVO;
import com.blog.personalblog.vo.StatisticsWordCloudVO;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @author: SuperMan
 * @create: 2023-05-20
 **/

@Service
public class StatisticsServiceImpl implements StatisticsService {

    @Resource
    private StatisticsMapper statisticsMapper;
    @Resource
    private ArticleService articleService;
    @Resource
    private TagService tagService;

    private Map<String, Long> users = new HashMap<>();

    /**
     * key:时间，HH:mm
     * value: 人数
     */
    private Map<String, Long> countUser = new HashMap<>();

    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM-dd");
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

    @Override
    public StatisticsTopCountVO getTopCount() {
        StatisticsTopCountVO topCount = statisticsMapper.getTopCount();
        return topCount;
    }

    @Override
    public List<StatisticsBaseCountVO> getArticleCount() {
        List<StatisticsBaseCountVO> list = new ArrayList<>();
        LocalDate today = LocalDate.now();

        //过滤近7天数据
        List<Article> articles = articleService.getAll();
        List<Article> articlesInLastWeek = articles.stream()
                .filter(article -> article.getCreateTime().toLocalDate().isAfter(today.minusDays(7)))
                .collect(Collectors.toList());

        Map<LocalDate, Long> map = articlesInLastWeek.stream()
                .collect(Collectors.groupingBy(article -> article.getCreateTime().toLocalDate(), Collectors.counting()));

        for (int i = 0; i < 7; i++) {
            LocalDate date = today.minusDays(i);
            map.putIfAbsent(date, 0L);
            StatisticsBaseCountVO articleCount = new StatisticsBaseCountVO(date.format(dateFormat), map.get(date));
            list.add(articleCount);
        }
        //排序
        list = list.stream().sorted(Comparator.comparing(StatisticsBaseCountVO::getDate)).collect(Collectors.toList());
        return list;
    }

    @Override
    public List<StatisticsBaseCountVO> getOnline() {
        Map<String, StatisticsBaseCountVO> map = new HashMap<>();

        //分钟划分
        Date date = new Date();
        List<String> res = new ArrayList<>();
        if (date != null) {
            Calendar ca = Calendar.getInstance();
            ca.setTime(date);
            for (int i = 0; i < 60; i++) {
                ca.add(Calendar.MINUTE, -1);
                res.add(sdf.format(ca.getTime()));
            }
        }
        countUser.forEach((key, v) -> {
            StatisticsBaseCountVO baseCount = new StatisticsBaseCountVO();
            baseCount.setDate(key);
            baseCount.setCount(v);
            map.put(key, baseCount);
        });

        res.forEach(m -> {
            map.computeIfAbsent(m, k -> {
                StatisticsBaseCountVO count = new StatisticsBaseCountVO(k, 0L);
                return count;
            });
        });
        List<StatisticsBaseCountVO> sort = CollUtil.sort(map.values(), Comparator.comparing(StatisticsBaseCountVO::getDate));
        return sort;
    }

    @Override
    public void login(String username, Long date) {
        users.put(username, date);
    }

    @Override
    public void logout(String username, Long date) {
        users.remove(username, date);
    }


    @Scheduled(cron = "0 */1 * * * ?")
    public void getOnlineUsers() {
        long currentTime = System.currentTimeMillis();
        Long count = 0L;
        for (long loginTime : users.values()) {
            if (currentTime - loginTime <= 60000) {
                count++;
            }
        }
        Date date= new Date(currentTime);
        countUser.putIfAbsent(sdf.format(date), count);
    }

    @Override
    public List<StatisticsTagCountVO> getTagCount() {
        List<StatisticsTagCountVO> tagCount = statisticsMapper.getTagCount();
        return tagCount;
    }

    @Override
    public List<StatisticsWordCloudVO> getWordCloud() {
        List<StatisticsWordCloudVO> list = new ArrayList<>();
        //获取全部标签
        List<Tag> tags = tagService.getTagsByTagName(new TagBO());

        tags.forEach(t -> {
            int n = ((int) (Math.random() * (100 - 0))) + 0;
            StatisticsWordCloudVO cloud = new StatisticsWordCloudVO();
            Random rng = new Random();
            int red = rng.nextInt(256);
            int green = rng.nextInt(256);
            int blue = rng.nextInt(256);
            String colorString = String.format("rgb(%d, %d, %d, %.2f)", red, green, blue, 0.12f);

            cloud.setBgColor(colorString);
            cloud.setTagName(t.getTagName());
            String hexColor = String.format("#%02X%02X%02X", red, green, blue);
            cloud.setColor(hexColor);
            cloud.setValue(String.valueOf(n));
            list.add(cloud);
        });

        return list;
    }

}
