package com.blog.personalblog.service;

import com.blog.personalblog.vo.StatisticsBaseCountVO;
import com.blog.personalblog.vo.StatisticsTagCountVO;
import com.blog.personalblog.vo.StatisticsTopCountVO;
import com.blog.personalblog.vo.StatisticsWordCloudVO;

import java.util.List;

/**
 * @author: SuperMan
 * @create: 2023-05-20
 **/
public interface StatisticsService {

    /**
     * 首页顶部数据统计
     * @return
     */
    StatisticsTopCountVO getTopCount();

    /**
     * 文章近一周统计数据
     * @return
     */
    List<StatisticsBaseCountVO> getArticleCount();

    /**
     * 获取在线人数
     * @return
     */
    List<StatisticsBaseCountVO> getOnline();

    void login(String username, Long date);

    void logout(String username, Long date);

    /**
     * 获取标签数据
     * @return
     */
    List<StatisticsTagCountVO> getTagCount();

    List<StatisticsWordCloudVO> getWordCloud();

}
