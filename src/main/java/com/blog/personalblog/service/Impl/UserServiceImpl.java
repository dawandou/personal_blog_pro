package com.blog.personalblog.service.Impl;

import com.blog.personalblog.entity.User;
import com.blog.personalblog.mapper.UserMapper;
import com.blog.personalblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 业务实现层
 *
 * @author: SuperMan
 * 欢迎关注我的公众号：码上言
 * @create: 2021-11-03
 *
 */

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public List<User> findAll() {
        List<User> userList = userMapper.findAll();
        return userList;
    }

    @Override
    public void createUser(User user) {
        userMapper.insert(user);
    }

    @Override
    public void updateUser(User user) {
        userMapper.update(user);
    }

    @Override
    public void deleteUser(int id) {
        userMapper.delete(id);
    }

    @Override
    public User findByUserId(Integer userId) {
        User user = userMapper.getUserById(userId);
        return user;
    }

    @Override
    public User getUserByUserName(String userName) {
        if (userName == null) {
            return null;
        }
        User user = userMapper.findByUsername(userName);
        return user;
    }

    @Override
    public void updateLoginTime(Integer userId) {
        User user = new User();
        user.setId(userId);
        user.setLastLoginTime(LocalDateTime.now());
        userMapper.updateById(user);
    }

}
