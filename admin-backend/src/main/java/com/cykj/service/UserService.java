package com.cykj.service;

import com.cykj.dto.ResponseDto;
import com.cykj.pojo.Packages;
import com.cykj.pojo.User;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by zhangzhenhua on 2024/8/20.
 * 用户Service
 */
public interface UserService {
    //修改用户信息
    ResponseDto updateUserById(User user);
    //根据用户姓名模糊搜索
    ResponseDto getUserByNameLike(String userName,int pageCurrent, int pageSize);
    //添加用户
    ResponseDto addUser(User user);
    //导入Excel中的用户数据
    int importUsersFromExcel(MultipartFile file);
    //用户开单
    ResponseDto purchase(int userId, int packageId);
}
