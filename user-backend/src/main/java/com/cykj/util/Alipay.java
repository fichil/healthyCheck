package com.cykj.util;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.cykj.model.AlipayBean;
import org.springframework.stereotype.Component;

/**
 * Created by zhangzhenhua on 2024/8/14.
 * 支付宝支付接口
 */
@Component
public class Alipay {



    public String pay(AlipayBean alipayBean) throws AlipayApiException {
        //1、获得初始化的AlipayClient
//        String serverUrl = AlipayConfig.URL;
        String serverUrl = "https://openapi-sandbox.dl.alipaydev.com/gateway.do";

        String appId = AlipayConfig.APPID;
        System.out.println("alipayConfig.getAppId(): " + appId);
        String privateKey = AlipayConfig.RSA_PRIVATE_KEY;
        String format = AlipayConfig.FORMAT;
        String charset = AlipayConfig.CHARSET;
        String alipayPublicKey = AlipayConfig.ALIPAY_PUBLIC_KEY;
        String signType = AlipayConfig.SIGNTYPE;
        String returnUrl = AlipayConfig.return_url;
        String notifyUrl = AlipayConfig.notify_url;
        AlipayClient alipayClient = new DefaultAlipayClient(serverUrl, appId, privateKey, format, charset, alipayPublicKey,signType);

        //2、设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        //页面跳转同步通知页面路径
        alipayRequest.setReturnUrl(returnUrl);
        //后台异步通知页面路径
        alipayRequest.setNotifyUrl(notifyUrl);
        //封装参数
        alipayRequest.setBizContent(JSON.toJSONString(alipayBean));
        //3、请求支付宝进行付款，并获取字符结果
        String result = alipayClient.pageExecute(alipayRequest).getBody();
        System.out.println(result);


//        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipaydev.com/gateway.do","9021000140601166",privateKey,"json","utf-8",alipayPublicKey,"RSA2");
//        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
//        AlipayTradePrecreateModel model = new AlipayTradePrecreateModel();
//        request.setBizModel(model);
//        model.setOutTradeNo(alipayBean.getOut_trade_no());
//        model.setTotalAmount(alipayBean.getTotal_amount());
//        model.setSubject(alipayBean.getSubject());
//        AlipayTradePrecreateResponse response = alipayClient.execute(request);
//        String result = alipayClient.pageExecute(request).getBody();

//        System.out.print(response.getBody());
//        System.out.print(response.getQrCode());
        System.out.println(result);
        return result;
    }
}
