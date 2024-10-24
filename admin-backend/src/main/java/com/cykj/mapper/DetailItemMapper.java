package com.cykj.mapper;

import com.cykj.pojo.DetailItem;

import java.util.List;

/**
 * Created by zhangzhenhua on 2024/8/12.
 * 细项模块Mapper
 */
public interface DetailItemMapper {
    //获取所有细项
    List<DetailItem> getAllDetail();

    //修改细项信息
    int updateDetailItemById(DetailItem detailItem);

    //根据细项名称模糊搜索
    List<DetailItem> getDetailItemByNameLike(String detailItemName);

    //添加细项
    int addDetailItem(DetailItem detailItem);

    //根据项目id查询所有细项
    List<DetailItem> getDetailItemsByItemId(Long itemId);
}
