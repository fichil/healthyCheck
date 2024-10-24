package com.cykj.mapper;

import com.cykj.pojo.DetailResult;
import com.cykj.pojo.ItemSummary;
import com.cykj.pojo.Summary;

/**
 * Created by zhangzhenhua on 2024/8/22.
 * 检查结果Mapper
 */
public interface ResultMapper {
    //插入细项检查单
    int insertDetailResult(DetailResult detailResult);
    //插入项目检查单
    int insertItemSummary(ItemSummary itemSummary);
    //插入体检总结检查单
    int insertSummary(Summary summary);

    //设置细项检查结果
    int updateDetailResult(DetailResult detailResult);
    //设置项目检查结果
    int updateItemSummary(ItemSummary itemSummary);
    //设置体检总结检查结果
    int updateSummary(Summary summary);
    //根据订单id和项目id查询项目结果id
    Long getItemSummaryIdByOrderIdAndItemId(Long orderId, Long itemId);
    //通过细项id、订单号查询细项主键
    Long getDetailResultIdByDetailItemIdAndOrderId(Long detailItemId, String order);
}
