package com.cykj.mapper;

import com.cykj.pojo.OrderDetail;
import com.cykj.pojo.Orders;

/**
 * Created by zhangzhenhua on 2024/8/21.
 * 订单详情Mapper
 */
public interface OrderDetailMapper {

    // 创建订单详情
    int createOrderDetail(OrderDetail orderDetail);
}
