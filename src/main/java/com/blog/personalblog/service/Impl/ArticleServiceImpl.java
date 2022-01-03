package com.blog.personalblog.service.Impl;

import com.blog.personalblog.bo.ArticleBO;
import com.blog.personalblog.entity.Article;
import com.blog.personalblog.mapper.ArticleMapper;
import com.blog.personalblog.service.ArticleService;
import com.github.pagehelper.PageHelper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: SuperMan
 * @create: 2021-12-01
 */
@Log4j2
@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    ArticleMapper articleMapper;

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
        return articleList;
    }

    @Override
    public void saveArticle(Article article) {
        articleMapper.createArticle(article);
        articleMap.put(article.getId(), article);
    }

    @Override
    public void updateArticle(Article article) {
        articleMapper.updateArticle(article);
        articleMap.put(article.getId(), article);
    }

    @Override
    public void deleteArticle(Integer articleId) {
        articleMapper.deleteArticle(articleId);
        articleMap.remove(articleId);
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
