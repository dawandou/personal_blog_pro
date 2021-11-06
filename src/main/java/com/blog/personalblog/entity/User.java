package com.blog.personalblog.entity;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author: SuperMan
 * 欢迎关注公众号：码上言
 * @create: 2021-11-02
 */
@Data
public class User {

    /**
     * 主键id
     */
    private Integer id;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String passWord;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 上次登录时间
     */
    private Date lastLoginTime;

    /**
     * 手机号
     */
    private Integer phone;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;


}
