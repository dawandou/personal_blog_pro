package com.blog.personalblog.vo;

import lombok.Data;

/**
 * @author: SuperMan
 * @create: 2023-05-20
 **/

@Data
public class StatisticsBaseCountVO {


    /**
     * 时间，例如：02-01
     */
    private String date;

    /**
     * 条数
     */
    private Long count;

    public StatisticsBaseCountVO() {

    }

    public StatisticsBaseCountVO(String date, Long count) {
        this.date = date;
        this.count = count;
    }

}
