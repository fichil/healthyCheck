package com.cykj.mapper;

import com.cykj.pojo.OrderDetail;
import com.cykj.pojo.Orders;

import java.util.List;

/**
 * Created by zhangzhenhua on 2024/8/14.
 * 订单Mapper
 */
public interface OrdersMapper {

    // 创建订单
    int createOrder(Orders orders);

    //修改订单
    int updateOrder(Orders orders);

    //查找订单
    Orders findByOrderNumber(String orderNumber);

    //添加套餐详情
    int addOrderDetail(OrderDetail orderDetail);

    //根据用户id查询已支付的套餐订单
    List<Orders> getOrdersByUserId(int userId);

    //通过订单号查询订单id
    Long getOrderIdByOrderNumber(String orderNumber);
}
