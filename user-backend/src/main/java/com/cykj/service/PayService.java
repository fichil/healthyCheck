package com.cykj.service;

import com.alipay.api.AlipayApiException;
import com.cykj.model.AlipayBean;

/**
 * Created by zhangzhenhua on 2024/8/14.
 * 支付相关的Service
 */
public interface PayService {

    //支付宝支付接口
    String aliPay(AlipayBean alipayBean) throws AlipayApiException;
}
