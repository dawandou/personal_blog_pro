package com.blog.personalblog.vo;

import lombok.Data;

/**
 * @author: SuperMan
 * @create: 2023-05-20
 **/
@Data
public class StatisticsTagCountVO {

    /**
     * 标签名称
     */
    private String tagName;

    /**
     * 标签总数
     */
    private Integer tagCount;
}
