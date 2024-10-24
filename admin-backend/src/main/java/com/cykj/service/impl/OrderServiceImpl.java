package com.cykj.service.impl;

import com.cykj.dto.ResponseDto;
import com.cykj.mapper.*;
import com.cykj.pojo.*;
import com.cykj.service.ItemService;
import com.cykj.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * Created by zhangzhenhua on 2024/8/21.
 * OrderServiceImpl 实现 OrderService 接口
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    PackageMapper packageMapper;
    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private DetailItemMapper detailItemMapper;
    @Autowired
    private ResultMapper resultMapper;

    /**
     * 创建订单
     * @param orders
     * @return
     */
    @Override
    public int createOrder(Orders orders) {
        return orderMapper.createOrder(orders);
    }

    /**
     * 查找细项还没有检查的订单
     * @return
     */
    @Override
    public ResponseDto getUnDetailOrders() {
        List<Orders> unDetailOrders = orderMapper.getUnDetailOrders();
        if (unDetailOrders!=null && unDetailOrders.size()>0){
            return ResponseDto.success("查询未检查的订单成功", unDetailOrders);
        } else {
            return ResponseDto.fail("查询未检查的订单失败，未找到相关的订单");
        }
    }

    /**
     * 根据订单号查询订单包含的项目
     * @return
     */
    @Override
    public ResponseDto getItemsByOrderNumber(String orderNumber) {
        Packages packag = packageMapper.getPackageByOrderNumber(orderNumber);
        List<Item> items ;
        if (packag!=null){
            items = itemMapper.getItemsByPackageId(packag.getPackageId());
            //循环获取细项
            for (Item item : items) {
                //根据项目id查询所有细项
                List<DetailItem> detailItems = detailItemMapper.getDetailItemsByItemId(item.getItemId());
                item.setDetailItems(detailItems);
            }
            return ResponseDto.success("根据订单号查询订单包含的项目成功", items);
        }else {
            return ResponseDto.fail("根据订单号查询订单包含的项目失败，未找到相关的订单");
        }

    }

    /**
     * 提交项目检查结果
     * @param results
     * @return
     */
    @Override
    public ResponseDto submitResults(List<ItemSummary> results, String orderNumber) {
        // 设置项目评价
        int itemSummaryCount = 0;
            //根据订单号查询订单id
        Long orderId = orderMapper.getOrderIdByOrderNumber(orderNumber);
        //遍历results
        for (ItemSummary itemSummary : results) {
            //根据orderId和itemId查询羡慕结果表主键id
            resultMapper.updateItemSummary(itemSummary);
            itemSummaryCount++;
        }
        //设置细项检测值
        int detailResultCount = 0;
        DetailResult detailResult1 = new DetailResult();
        for (ItemSummary itemSummary : results) {
            List<DetailResult> details = itemSummary.getDetails();
            //通过细项id、订单号查询细项主键
            for (DetailResult detailResult : details) {
                long detailItemId = detailResult.getDetailItemId();
                long detailResultId = resultMapper.getDetailResultIdByDetailItemIdAndOrderId(detailItemId, orderNumber);
                detailResult1.setResultId(detailResultId);
                detailResult1.setResult(detailResult.getResult());
                detailResult1.setDoctor(detailResult.getDoctor());
                resultMapper.updateDetailResult(detailResult1);
                detailResultCount++;
            }
            }


        //返回是否插入成功
        if(itemSummaryCount>0 && itemSummaryCount > 0){
            return ResponseDto.success("提交项目检查结果成功", null);
        }else {
            return ResponseDto.fail("提交项目检查结果失败");
        }

    }


}
