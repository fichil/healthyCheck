package com.cykj.mapper;

import com.cykj.pojo.Item;

import java.util.List;

/**
 * Created by zhangzhenhua on 2024/8/12.
 * 项目模块Mapper
 */
public interface ItemMapper {
    //修改项目信息
    int updateItemById(Item item);

    //根据项目名称模糊搜索
    List<Item> getItemByNameLike(String itemName);

    //添加项目
    int addItem(Item item);

    //根据套餐id查询所有项目
    List<Item> getItemsByPackageId(Long packageId);



}
