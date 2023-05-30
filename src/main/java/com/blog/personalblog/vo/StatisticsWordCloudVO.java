package com.blog.personalblog.vo;

import lombok.Data;

/**
 * @author: SuperMan
 * @create: 2023-05-20
 **/

@Data
public class StatisticsWordCloudVO {

    /**
     * 标签名称
     */
    private String tagName;
    /**
     * 背景颜色
     */
    private String bgColor;
    /**
     * 颜色
     */
    private String color;

    /**
     * 数值
     */
    private String value;

}
