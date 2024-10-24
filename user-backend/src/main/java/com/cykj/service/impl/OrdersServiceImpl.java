package com.cykj.service.impl;

import com.cykj.dto.ResponseDto;
import com.cykj.mapper.OrdersMapper;
import com.cykj.pojo.OrderDetail;
import com.cykj.pojo.Orders;
import com.cykj.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhangzhenhua on 2024/8/14.
 * 订单模块实现
 */
@Service
public class OrdersServiceImpl implements OrdersService {

    @Autowired
    private
    OrdersMapper ordersMapper;

    /**
     * 创建订单
     * @param orders
     * @return
     */
    @Override
    public ResponseDto createOrder(Orders orders) {
        int order = ordersMapper.createOrder(orders);
        if (order > 0) {
            return ResponseDto.success("创建订单成功", null);
        } else {
            return ResponseDto.fail("创建订单失败");
        }
    }

    /***
     * 修改订单为已支付
     * @param orders
     * @return
     */
    @Override
    public int updateOrder(Orders orders) {
        return ordersMapper.updateOrder(orders);
    }

    /**
     * 根据订单号查询订单
     * @param orderNumber
     * @return
     */
    @Override
    public Orders findByOrderNumber(String orderNumber) {
        return null;
    }


    /**
     * 添加套餐详情
     * @param orders
     * @return
     */
    @Override
    public int addOrderDetail(Orders orders) {
        orders.getOrderDetail().setOrderNumber(orders.getOrderNumber());
        return ordersMapper.addOrderDetail(orders.getOrderDetail());
    }

    /**
     * 根据用户id查询已支付的套餐订单
     * @param userId
     * @return
     */
    @Override
    public ResponseDto getOrdersByUserId(int userId) {
        List<Orders> orders = ordersMapper.getOrdersByUserId(userId);
        if (orders != null && orders.size() > 0) {
            return ResponseDto.success("查询成功", orders);
        } else {
            return ResponseDto.fail("查询失败，未找到相关的订单");
        }
    }

}
