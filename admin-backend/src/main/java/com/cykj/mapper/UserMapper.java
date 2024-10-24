package com.cykj.mapper;

import com.cykj.pojo.Packages;
import com.cykj.pojo.User;

import java.util.List;

/**
 * Created by zhangzhenhua on 2024/8/20.
 * 用户Mapper
 */
public interface UserMapper {

    //修改用户信息
    int updateUserById(User user);

    //根据用户姓名模糊搜索
    List<User> getUserByNameLike(String userName);

    //添加用户
    int addUser(User user);

    //修改用户余额
    int updateUserBalance(User user);

    //根据Id查找用户
    User getUserById(int userId);
}
