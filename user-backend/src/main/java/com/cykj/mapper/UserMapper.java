package com.cykj.mapper;

import com.cykj.pojo.Orders;
import com.cykj.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

/**
 * Created by zhangzhenhua on 2024/8/8.
 *  用户Mapper
 */
public interface UserMapper {
    //用户手机号登陆
    User loginByPhone(@Param("phone") String idOrPhone, @Param("password") String password);

    //用户注册
    int register(User user);

    //用户通过身份证登陆
    User loginByIdNumber(@Param("idNumber") String idOrPhone, @Param("password") String password);

    //用户充值
    int updateUserBalance(Orders orders);

}
