package com.blog.personalblog.entity;

import lombok.Data;

/**
 * @author: SuperMan
 * @create: 2022-03-25
 **/

@Data
public class LoginModel {

    /**
     * username: admin
     * password: 123456
     */

    private String username;
    private String password;
}
