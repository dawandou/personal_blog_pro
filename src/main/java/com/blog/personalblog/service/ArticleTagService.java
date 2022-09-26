package com.blog.personalblog.service;

import com.blog.personalblog.entity.ArticleTag;

import java.util.List;

/**
 * @author: SuperMan
 * @create: 2022-01-24
 **/
public interface ArticleTagService {

    /**
     * 批量插入文章标签数据
     *
     * @param articleTagList
     */
    void insertBatch(List<ArticleTag> articleTagList);

    /**
     * 根据文章id删除关联表的标签数据
     *
     * @param articleId
     */
    void deleteTag(Integer articleId);


    /**
     * 根据文章id查找出所有的关联标签数据
     *
     * @param articleId
     * @return
     */
    List<ArticleTag> findArticleTagById(Integer articleId);

}
