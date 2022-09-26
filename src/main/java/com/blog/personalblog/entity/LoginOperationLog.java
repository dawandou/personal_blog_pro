package com.blog.personalblog.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * @author: SuperMan
 * @create: 2022-04-05
 **/
@Data
public class LoginOperationLog {

    /**
     * 主键id
     */
    private Integer id;

    /**
     * 登录账号
     */
    private String loginName;

    /**
     * 登录IP地址
     */
    private String ipAddress;

    /**
     * 登录地点
     */
    private String loginLocation;

    /**
     * 浏览器类型
     */
    private String browserType;

    /**
     * 操作系统
     */
    private String os;

    /**
     * 登录状态，默认0, 0-成功, 1-失败
     */
    private Integer loginStatus;

    /**
     * 创建时间
     */
    @JsonFormat(timezone = "GMT+8",pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}
