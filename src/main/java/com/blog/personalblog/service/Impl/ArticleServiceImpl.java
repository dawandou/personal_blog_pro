package com.blog.personalblog.service.Impl;

import com.blog.personalblog.bo.ArticleBO;
import com.blog.personalblog.config.mail.MailInfo;
import com.blog.personalblog.config.mail.SendMailConfig;
import com.blog.personalblog.entity.Article;
import com.blog.personalblog.entity.ArticleTag;
import com.blog.personalblog.entity.Tag;
import com.blog.personalblog.entity.User;
import com.blog.personalblog.mapper.ArticleMapper;
import com.blog.personalblog.service.ArticleService;
import com.blog.personalblog.service.ArticleTagService;
import com.blog.personalblog.service.TagService;
import com.blog.personalblog.service.UserService;
import com.github.pagehelper.PageHelper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author: SuperMan
 * @create: 2021-12-01
 */
@Log4j2
@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    ArticleMapper articleMapper;
    @Resource
    ArticleTagService articleTagService;
    @Resource
    UserService userService;
    @Resource
    TagService tagService;

    /**
     * key:文章id
     * value: 文章
     */
    Map<Integer, Article> articleMap = new LinkedHashMap<>();


    @Override
    @PostConstruct
    public void init() {
        List<Article> articleList = articleMapper.findAll();
        try {
            for(Article article : articleList) {
                articleMap.put(article.getId(), article);
            }
            log.info("文章缓存数据加载完成");
        } catch (Exception e) {
            log.error("文章缓存数据加载失败！", e.getMessage());
        }
    }

    @Override
    public List<Article> getArticlePage(ArticleBO articleBO) {
        int pageNum = articleBO.getPageNum();
        int pageSize = articleBO.getPageSize();
        PageHelper.startPage(pageNum,pageSize);
        List<Article> articleList = articleMapper.getArticlePage(articleBO);
        List<Tag> tagList = new ArrayList<>();
        if (articleList != null) {
            for (Article article : articleList) {
                List<ArticleTag> articleTags = articleTagService.findArticleTagById(article.getId());
                if (articleTags != null) {
                    for (ArticleTag articleTag : articleTags) {
                        Tag tag = tagService.findTagById(articleTag.getTagId());
                        tagList.add(tag);
                    }
                }
                article.setTagList(tagList);
            }
        }
        return articleList;
    }

    @Override
    public void saveArticle(Article article) {
        articleMapper.createArticle(article);
        articleMap.put(article.getId(), article);
        //添加文章标签
        if (article.getTagIdList() != null) {
            List<ArticleTag> articleTagList = article.getTagIdList().stream().map(tagId -> ArticleTag.builder()
                    .tagId(tagId)
                    .articleId(article.getId())
                    .build()).collect(Collectors.toList());
            articleTagService.insertBatch(articleTagList);
        }
        User user = userService.findByUserId(article.getUserId());
        //添加文章发送邮箱提醒
        String content = "【{0}】您好：\n" +
                "您已成功发布了标题为: {1} 的文章 \n" +
                "请注意查收！\n";
        MailInfo build = MailInfo.builder()
                .receiveMail(user.getEmail())
                .content(MessageFormat.format(content, user.getUserName(), article.getTitle()))
                .title("文章发布")
                .build();
        SendMailConfig.sendMail(build);
    }

    @Override
    public void updateArticle(Article article) {
        articleMapper.updateArticle(article);
        articleMap.put(article.getId(), article);
        //更新文章先把原来的标签删除掉
        articleTagService.deleteTag(article.getId());
        //添加文章标签
        if (article.getTagIdList() != null) {
            List<ArticleTag> articleTagList = article.getTagIdList().stream().map(tagId -> ArticleTag.builder()
                    .tagId(tagId)
                    .articleId(article.getId())
                    .build()).collect(Collectors.toList());
            articleTagService.insertBatch(articleTagList);
        }

    }

    @Override
    public void deleteArticle(Integer articleId) {
        articleMapper.deleteArticle(articleId);
        articleMap.remove(articleId);
        //关联标签删除掉
        articleTagService.deleteTag(articleId);
    }

    @Override
    public Article findById(Integer articleId) {
        Article article = articleMap.get(articleId);
        if (article == null) {
            Article art = articleMapper.getById(articleId);
            return art;
        }
        return article;
    }

}
