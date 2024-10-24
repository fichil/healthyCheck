package com.cykj.service;

import com.cykj.dto.ResponseDto;
import com.cykj.pojo.DetailResult;
import com.cykj.pojo.ItemSummary;
import com.cykj.pojo.Orders;

import java.util.HashMap;
import java.util.List;

/**
 * Created by zhangzhenhua on 2024/8/21.
 * 订单服务层接口
 */
public interface OrderService {

    // 创建订单
    int createOrder(Orders orders);

    //查找细项还没有检查的订单
    ResponseDto getUnDetailOrders();

    //根据订单号查询订单包含的项目
    ResponseDto getItemsByOrderNumber(String orderNumber);

    // 提交项目检查结果
    ResponseDto submitResults(List<ItemSummary> results, String orderNumber);
}
