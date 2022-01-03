package com.blog.personalblog.mapper;

import com.blog.personalblog.bo.ArticleBO;
import com.blog.personalblog.entity.Article;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: SuperMan
 * @create: 2021-12-01
 */
@Repository
public interface ArticleMapper {

    /**
     * 查询所有的文章列表
     * @return
     */
    List<Article> findAll();

    /**
     * 创建文章
     * @param article
     * @return
     */
    int createArticle(Article article);

    /**
     * 修改文章
     * @param article
     * @return
     */
    int updateArticle(Article article);

    /**
     * 分类列表（分页）
     * @param articleBO
     * @return
     */
    List<Article> getArticlePage(@Param("articleBO") ArticleBO articleBO);

    /**
     * 删除文章
     * @param id
     */
    void deleteArticle(Integer id);

    /**
     * 根据id查找分类
     * @param id
     * @return
     */
    Article getById(Integer id);

}
