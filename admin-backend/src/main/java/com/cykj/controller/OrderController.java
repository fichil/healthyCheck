package com.cykj.controller;

import com.cykj.dto.ResponseDto;
import com.cykj.service.OrderService;
import com.cykj.vo.ItemResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by zhangzhenhua on 2024/8/22.
 * 订单模块控制类
 */
@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 查找细项还没有检查的订单
     * @return
     */
    @ResponseBody
    @RequestMapping("/getUnDetailOrders")
    public ResponseDto getUnDetailOrders(){
        return orderService.getUnDetailOrders();
    }

    /**
     * 根据订单号查询订单包含的项目
     * @return
     */
    @RequestMapping("/getItemsByOrderNumber")
    @ResponseBody
    public ResponseDto getItemsByOrderNumber(String orderNumber){
        return orderService.getItemsByOrderNumber(orderNumber);
    }

    /**
     * 提交项目检查结果
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping("/submitResults")
    public ResponseDto submitResults(@RequestBody ItemResultVO itemResultVO){
        return orderService.submitResults(itemResultVO.getResults(), itemResultVO.getNowOrderNumber());

    }




}
