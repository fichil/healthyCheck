package com.cykj.mapper;

import com.cykj.pojo.Orders;

import java.util.List;

/**
 * Created by zhangzhenhua on 2024/8/21.
 * 订单Mapper
 */
public interface OrderMapper {
    //生成订单
    int createOrder(Orders orders);

    //通过订单号查询订单id
    Long getOrderIdByOrderNumber(String orderNumber);

    //查找细项还没有检查的订单
    List<Orders> getUnDetailOrders();
}
