package com.cykj.service;

import com.cykj.dto.ResponseDto;
import com.cykj.pojo.Orders;
import com.cykj.pojo.User;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by zhangzhenhua on 2024/8/8.
 * 用户模块服务层
 */
public interface UserService {

    //用户登陆
    ResponseDto login(String idOrPhone,String password);

    //用户注册
    ResponseDto register(User user);

    //用户充值
    boolean recharge(Orders orders);

    //用户体检单生成
    boolean createResultForm(String orderNumber);




}
