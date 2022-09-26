package com.blog.personalblog.service.Impl;

import com.blog.personalblog.entity.ArticleTag;
import com.blog.personalblog.mapper.ArticleTagMapper;
import com.blog.personalblog.service.ArticleTagService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: SuperMan
 * @create: 2022-01-24
 **/
@Log4j2
@Service
public class ArticleTagServiceImpl implements ArticleTagService {

    @Resource
    ArticleTagMapper articleTagMapper;

    @Override
    public void insertBatch(List<ArticleTag> articleTagList) {
        try {
            articleTagMapper.insertBatch(articleTagList);
        } catch (Exception e) {
            log.error("批量添加文章标签失败！" + e.getMessage());
        }
    }

    @Override
    public void deleteTag(Integer articleId) {
        articleTagMapper.deleteByArticleId(articleId);
    }

    @Override
    public List<ArticleTag> findArticleTagById(Integer articleId) {
        List<ArticleTag> articleTagList = articleTagMapper.getArticleTagById(articleId);
        return articleTagList;
    }


}
