package com.blog.personalblog.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: SuperMan
 * @create: 2022-10-10
 **/

@Getter
@AllArgsConstructor
public enum ArticleArtStatusEnum {
    /**
     * 发布
     */
    PUBLISH(1, "发布"),
    /**
     * 仅我可见
     */
    ONLYME(2, "仅我可见"),
    /**
     * 草稿
     */
    DRAFT(3, "草稿");

    /**
     * 状态
     */
    private final Integer status;

    /**
     * 描述
     */
    private final String desc;
}
