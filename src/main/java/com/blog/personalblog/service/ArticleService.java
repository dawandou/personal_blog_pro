package com.blog.personalblog.service;

import com.blog.personalblog.bo.ArticleBO;
import com.blog.personalblog.bo.ArticleInsertBO;
import com.blog.personalblog.entity.Article;
import com.blog.personalblog.vo.ArticleVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author: SuperMan
 * @create: 2021-12-01
 */
public interface ArticleService {

    /**
     * 初始化数据
     */
    void init();

    /**
     * 获取所有的文章（分页）
     * @return
     */
    List<Article> getArticlePage(ArticleBO articleBO);

    /**
     * 新建文章
     * @param bo
     * @return
     */
    void insertOrUpdateArticle(ArticleInsertBO bo);

    /**
     * 修改文章
     * @param article
     * @return
     */
    void updateArticle(Article article);

    /**
     * 删除文章
     * @param articleId
     */
    void deleteArticle(Integer articleId);

    /**
     * 根据文章id查找文章
     * @param articleId
     * @return
     */
    ArticleVO findById(Integer articleId);

    /**
     * 上传文件
     *
     * @param file
     * @return
     */
    String uploadFile(MultipartFile file);

    List<Article> getAll();

}
