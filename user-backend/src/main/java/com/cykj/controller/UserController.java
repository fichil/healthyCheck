package com.cykj.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.cykj.dto.ResponseDto;
import com.cykj.pojo.Orders;
import com.cykj.pojo.User;
import com.cykj.service.OrdersService;
import com.cykj.service.UserService;
import com.cykj.util.AlipayConfig;
import com.cykj.util.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhangzhenhua on 2024/8/8.
 * 用户Controller
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private OrdersService ordersService;

    /**
     * 用户登陆
     * @param idOrPhone
     * @param password
     * @return
     */
    @RequestMapping("/login")
    @ResponseBody
    public ResponseDto login(@RequestParam("idOrPhone") String idOrPhone, @RequestParam("password") String password) {
        return userService.login(idOrPhone, password);

    }

    /**
     * 用户注册
     * @param user
     * @return
     */
    @RequestMapping("/register")
    @ResponseBody
    public ResponseDto register(@RequestBody User user) {
        return userService.register(user);
    }

    /**
     * 获取用户信息
     * @param token
     * @return
     */
    @RequestMapping("/getUserInfo")
    @ResponseBody
    public ResponseDto getUserInfo(String token){
        // 获取token中进行解析，得到用户信息
        Claims claims = JwtUtils.parseToken(token);
        System.out.println(claims.get("userInfo").toString());

        // 获取userInfo并将其转化为Map，然后通过构造函数或其他方式构建User对象
        Map<String, Object> userInfoMap = (Map<String, Object>) claims.get("userInfo");
        User user = new User();
        user.setPhone((String) userInfoMap.get("phone"));
        user.setPassword((String) userInfoMap.get("password"));


        // 执行登录并获取新Token
        ResponseDto dto = userService.login(user.getPhone(), user.getPassword());
        String newToken = (String) dto.getData();

        // 解析新Token并返回结果
        claims = JwtUtils.parseToken(newToken);
        return ResponseDto.success("success", claims);
    }

    /**
     * 充值
     * @param request
     * @return
     */
    @PostMapping("/notify")
    public String recharge(HttpServletRequest request) {
        System.out.println("进入支付成功回调");
        Map<String, String> params = new HashMap<>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (String name : requestParams.keySet()) {
            String[] values = requestParams.get(name);
            String valueStr = String.join(",", values);
            params.put(name, valueStr);
            System.out.println("name: " + name + ", value: " + valueStr);
        }

        try {
            // 调用SDK验证签名

            boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.CHARSET, AlipayConfig.SIGNTYPE);
            System.out.println("签名验证结果: " + signVerified);
            if (signVerified) {
                // 验证成功
                String outTradeNo = params.get("out_trade_no");
                String tradeStatus = params.get("trade_status");

                if ("TRADE_SUCCESS".equals(tradeStatus)) {
                    // 查询订单状态，避免重复处理
                    Orders existingOrder = ordersService.findByOrderNumber(outTradeNo);
                    if (existingOrder != null && "已支付".equals(existingOrder.getPaymentStatus())) {
                        return "success"; // 已经处理过，直接返回
                    }

                    // 支付成功，更新用户余额
                    String totalAmount = params.get("total_amount");
                    Orders orders = new Orders();
                    orders.setOrderNumber(outTradeNo);
                    orders.setTotalAmount(Double.parseDouble(totalAmount));
                    orders.setPaymentStatus("已支付");

                    // 修改用户余额
                    boolean recharge = userService.recharge(orders);

                    // 修改订单状态
                    int updateResult = ordersService.updateOrder(orders);

                    if (updateResult > 0 && recharge) {
                        return "success";
                    } else {
                        return "fail"; // 更新失败
                    }
                }

                return "fail"; // 非TRADE_SUCCESS状态
            } else {
                // 验证失败
                return "fail";
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
            return "fail";
        }
    }


    /**
     * 用户购买套餐
     * @param orders
     * @return
     */
    @RequestMapping("/buyPackage")
    @ResponseBody
    public ResponseDto buyPackage(@RequestBody Orders orders) {
        // 生成订单
        ResponseDto orderResponse = ordersService.createOrder(orders);

        if (orderResponse.getMessage().equals("创建订单成功")) {
            // 修改用户余额
            orders.setTotalAmount(-orders.getTotalAmount());
            boolean recharge = userService.recharge(orders);

            if (recharge) {
                // 设置订单状态为已支付
                orders.setPaymentStatus("已支付");
                int updateResult = ordersService.updateOrder(orders);
                //添加订单详情
                ordersService.addOrderDetail(orders);
                boolean resultForm = userService.createResultForm(orders.getOrderNumber());

                if (updateResult > 0) {
                    return ResponseDto.success("购买成功", null);
                } else {
                    return ResponseDto.fail("订单状态更新失败");
                }
            } else {
                return ResponseDto.fail("余额扣除失败");
            }
        } else {
            return ResponseDto.fail("订单创建失败");
        }
    }

    /**
     * 获取用户的全部订单
     * @param userId
     * @return
     */
    @RequestMapping("/getOrdersByUserId")
    @ResponseBody
    public ResponseDto getOrdersByUserId(int userId) {
        return ordersService.getOrdersByUserId(userId);
    }
    
}
