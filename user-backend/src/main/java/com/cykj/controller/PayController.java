package com.cykj.controller;

import com.alipay.api.AlipayApiException;
import com.cykj.dto.ResponseDto;
import com.cykj.model.AlipayBean;
import com.cykj.pojo.Orders;
import com.cykj.service.OrdersService;
import com.cykj.service.PayService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by zhangzhenhua on 2024/8/14.
 * 支付宝支付控制类
 */
@RestController
@RequestMapping("/pay")
@CrossOrigin
public class PayController {

    //支付接口
    @Resource
    private PayService payService;

    //订单接口
    @Resource
    private OrdersService ordersService;

    /**
     * 支付方法
     * @param alipayBean
     * @return
     * @throws AlipayApiException
     */
    @RequestMapping("/alipay")
    public ResponseDto alipay( @RequestBody AlipayBean alipayBean) throws AlipayApiException {
        String msg = payService.aliPay(alipayBean);
        Orders orders = new Orders();
        orders.setOrderNumber(alipayBean.getOut_trade_no());
        System.out.println(alipayBean);
        orders.setUserId(Long.parseLong(alipayBean.getSubject().split(":")[0]) );
        orders.setTotalAmount(Double.parseDouble(alipayBean.getTotal_amount()));
        ResponseDto order = ordersService.createOrder(orders);
//        System.out.println(order.getMessage());
        return ResponseDto.success("获取支付请求成功", msg);
    }
}
