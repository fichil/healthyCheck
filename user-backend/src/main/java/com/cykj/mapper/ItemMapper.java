package com.cykj.mapper;

import com.cykj.pojo.Item;

import java.util.List;

/**
 * Created by zhangzhenhua on 2024/8/23.
 */
public interface ItemMapper {

    //根据套餐id查询所有项目
    List<Item> getItemsByPackageId(Long packageId);
}
