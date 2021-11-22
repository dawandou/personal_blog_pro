package com.blog.personalblog.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 博客分类
 *
 * @author: SuperMan
 * @create: 2021-11-21
 */
@Data
public class Category {

    /**
     * 主键id
     */
    private Integer categoryId;

    /**
     * 分类名称
     */
    private String categoryName;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

}
