package com.cykj.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.cykj.dto.ResponseDto;
import com.cykj.mapper.*;
import com.cykj.pojo.*;
import com.cykj.service.UserService;
import com.cykj.util.AlipayConfig;
import com.cykj.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangzhenhua on 2024/8/8.
 * 用户模块服务层实现类
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private DetailItemMapper detailItemMapper;

    @Autowired
    private ResultMapper resultMapper;

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private PackageMapper packageMapper;

    @Autowired
    private ItemMapper itemMapper;

    /**
     * 用户登陆
     * @param idOrPhone
     * @param password
     * @return
     */
    @Override
    public ResponseDto login(String idOrPhone, String password) {
        User user;
        //判断是否为手机号
        System.out.println(idOrPhone);
        if (idOrPhone.matches("\\d{11}")) {
            user = userMapper.loginByPhone(idOrPhone, password);
        //判断是否为身份证
        }else {
            user = userMapper.loginByIdNumber(idOrPhone, password);
        }
        if(user != null) {
            //生成token
            HashMap<String, Object> data = new HashMap<>();
            data.put("userInfo", user);
            String token = JwtUtils.generateToken(data);
            return ResponseDto.success(user.getName()+"登陆成功", token);
        }else {
            return ResponseDto.fail("账号或密码错误");
        }
    }

    /**
     * 用户注册
     * @param user
     * @return
     */
    @Override
    public ResponseDto register(User user) {
        return userMapper.register(user) > 0? ResponseDto.success(user.getName()+"注册成功",null) : ResponseDto.fail("注册失败") ;
    }

    /**
     * 用户充值
     * @param orders
     * @return
     */
    @Override
    public boolean recharge(Orders orders) {
        int i = userMapper.updateUserBalance(orders);
        if (i > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 创建3个结果表
     * @param orderNumber
     * @return
     */
    @Override
    public boolean createResultForm(String orderNumber){
        //通过订单id查到订单号
        Long orderId = ordersMapper.getOrderIdByOrderNumber(orderNumber);
        //插入总结检查单
        Summary summary = new Summary();
        summary.setOrderId(orderId);
        int summaryCount = resultMapper.insertSummary(summary);
        //插入项目检查单
        ItemSummary itemSummary = new ItemSummary();
        itemSummary.setOrderId(orderId);
        int itemSummaryCount = 0;
        //根据订单订单号查套餐
        Packages packages = packageMapper.getPackageByOrderNumber(orderNumber);
        //通过套餐id查询项目
        List<Item> itemsByPackageId = itemMapper.getItemsByPackageId(packages.getPackageId());
        //循环所有项目插入羡慕检查单
        for (Item item : itemsByPackageId) {
            itemSummary.setItemId(item.getItemId());
            resultMapper.insertItemSummary(itemSummary);
            itemSummaryCount++;
        }
        //插入细项检查单
        DetailResult detailResult = new DetailResult();
        detailResult.setOrderId(orderId);
        int detailResultCount = 0;
        //遍历套餐中的所有项目，在遍历中每一细项插入细项检查单
        for (Item item : itemsByPackageId) {
            long itemId = item.getItemId();
            List<DetailItem> detailItems = detailItemMapper.getDetailItemsByItemId(itemId);
            for (DetailItem detailItem : detailItems) {
                detailResult.setDetailItemId(detailItem.getDetailItemId());
                resultMapper.insertDetailResult(detailResult);
                detailResultCount++;
            }
        }
        return summaryCount > 0 && itemSummaryCount > 0 && detailResultCount > 0;
    }

}
