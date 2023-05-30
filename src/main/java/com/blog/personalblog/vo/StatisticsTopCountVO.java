package com.blog.personalblog.vo;

import lombok.Data;

/**
 * @author: SuperMan
 * @create: 2023-05-20
 **/

@Data
public class StatisticsTopCountVO {

    /**
     * 文章总数
     */
    private Integer articleCount;
    /**
     * 分类总数
     */
    private Integer categoryCount;
    /**
     * 用户总数
     */
    private Integer userCount;
    /**
     * 标签总数
     */
    private Integer tagCount;

}
