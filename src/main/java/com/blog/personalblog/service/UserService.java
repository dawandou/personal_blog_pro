package com.blog.personalblog.service;

import com.blog.personalblog.entity.User;
import io.swagger.models.auth.In;

import java.util.List;

/**
 * @author: SuperMan
 * 欢迎关注我的公众号：码上言
 * @create: 2021-11-03
 */
public interface UserService {

    /**
     * 查询所有用户列表
     * @return
     */
    List<User> findAll();

    /**
     * 添加用户
     * @param user
     */
    void createUser(User user);

    /**
     * 修改用户信息
     * @param user
     */
    void updateUser(User user);

    /**
     * 删除用户
     * @param id
     */
    void deleteUser(int id);

    /**
     * 根据用户id查找用户
     * @param userId
     * @return
     */
    User findByUserId(Integer userId);

    /**
     * username
     * @param userName
     * @return
     */
    User getUserByUserName(String userName);

    /**
     * 更新上次登录时间
     * @param userId
     */
    void updateLoginTime(Integer userId);
}
