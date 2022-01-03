package com.blog.personalblog.entity;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author: SuperMan
 * @create: 2021-12-01
 */
@Data
public class Article {
    /**
     * 文章id
     */
    private Integer id;

    /**
     * 作者
     */
    private String author;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 分类id
     */
    private Integer categoryId;

    /**
     * 文章内容
     */
    private String content;

    /**
     * 文章浏览量
     */
    private Long views;

    /**
     * 文章总字数
     */
    private Long totalWords;

    /**
     * 评论id
     */
    private Integer commentableId;

    /**
     * 发布，默认0, 0-发布, 1-草稿
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
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 文章标签(页面展示)
     */
    private List<Tag> tagList;

    /**
     * 分类名称(页面展示)
     */
    private String categoryName;

}
