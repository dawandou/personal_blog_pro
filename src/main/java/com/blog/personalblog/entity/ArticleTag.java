package com.blog.personalblog.entity;

import lombok.Builder;
import lombok.Data;

/**
 * @author: SuperMan
 * @create: 2022-01-24
 **/
@Builder
@Data
public class ArticleTag {

    /**
     * id
     */
    private Integer id;

    /**
     * 文章id
     */
    private Integer articleId;

    /**
     * 标签id
     */
    private Integer tagId;

}
