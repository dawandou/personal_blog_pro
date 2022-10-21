package com.blog.personalblog.bo;

import lombok.Data;

import java.util.List;

/**
 * @author: SuperMan
 * @create: 2022-10-02
 */

@Data
public class ArticleInsertBO {
    /**
     * 文章id
     */
    private Integer id;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 分类id
     */
    private Integer categoryId;

    /**
     * 文章内容
     */
    private String content;

    /**
     * 发布，默认1, 1-发布, 2-仅我可见  3-草稿
     */
    private Integer artStatus;

    /**
     * 描述
     */
    private String description;

    /**
     * 文章logo
     */
    private String imageUrl;

    /**
     * 分类名称
     */
    private String categoryName;

    /**
     * 文章标签
     */
    private List<String> tagNameList;
}
