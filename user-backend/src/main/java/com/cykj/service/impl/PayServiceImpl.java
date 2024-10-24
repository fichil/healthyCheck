package com.cykj.service.impl;

import com.alipay.api.AlipayApiException;
import com.cykj.model.AlipayBean;
import com.cykj.service.PayService;
import com.cykj.util.Alipay;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by zhangzhenhua on 2024/8/14.
 * 支付宝支付实现类
 */
@Service
public class PayServiceImpl implements PayService {

    @Resource
    private Alipay aliPay;

    @Override
    public String aliPay(AlipayBean alipayBean) throws AlipayApiException {
        return aliPay.pay(alipayBean);
    }
}
