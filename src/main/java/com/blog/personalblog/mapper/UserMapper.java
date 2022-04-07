package com.blog.personalblog.mapper;


import com.blog.personalblog.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: SuperMan
 * 欢迎关注我的公众号：码上言
 * @create: 2021-11-03
 */
@Repository
public interface UserMapper {

    /**
     * 查询全部用户信息
     *
     * @return
     */
    List<User> findAll();

    /**
     * 添加用户
     * @param user
     */
    void insert(User user);

    /**
     * 更新用户
     * @param user
     */
    void update(User user);

    /**
     * 删除用户
     * @param id
     * @return
     */
    int delete(int id);

    /**
     * 根据用户id查找
     * @param userId
     * @return
     */
    User getUserById(Integer userId);

    /**
     * username
     * @param userName
     * @return
     */
    User findByUsername(String userName);
}
