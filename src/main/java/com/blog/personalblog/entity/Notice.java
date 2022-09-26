package com.blog.personalblog.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 通知公告
 */

@Data
public class Notice {

    /**
     * 主键
     */
    private Integer noticeId;

    /**
     * 公告标题
     */
    private String noticeTitle;

    /**
     * 公告类型，默认0, 0-公告, 1-通知, 2-提醒
     */
    private Integer noticeType;

    /**
     * 状态，默认0, true-正常, false-关闭
     */
    private Boolean noticeStatus;

    /**
     * 公告内容
     */
    private String noticeContent;

    /**
     * 创建者
     */
    private String createBy;

    /**
     * 创建时间
     */
    @JsonFormat(timezone = "GMT+8",pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @JsonFormat(timezone = "GMT+8",pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

}
