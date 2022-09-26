package com.blog.personalblog.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 标签
 *
 * @author: SuperMan
 * @create: 2021-11-28
 */
@Data
public class Tag {

    /**
     * 主键id
     */
    private Integer id;

    /**
     * 标签名称
     */
    private String tagName;

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
