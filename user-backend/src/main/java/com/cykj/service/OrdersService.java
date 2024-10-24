package com.cykj.service;

import com.cykj.dto.ResponseDto;
import com.cykj.pojo.Orders;

/**
 * Created by zhangzhenhua on 2024/8/14.
 * 订单模块服务层
 */
public interface OrdersService {

    // 创建订单
    ResponseDto createOrder(Orders orders);

    //修改订单为已支付
    int updateOrder(Orders orders);

    //查找订单
    Orders findByOrderNumber(String orderNumber);

    //添加订单详情
    int addOrderDetail(Orders orders);

    //根据用户id查询已支付的套餐订单
    ResponseDto getOrdersByUserId(int userId);

}
