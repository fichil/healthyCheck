package com.cykj.mapper;

import com.cykj.pojo.DetailItem;

import java.util.List;

/**
 * Created by zhangzhenhua on 2024/8/23.
 */
public interface DetailItemMapper {

    //根据项目id查询所有细项
    List<DetailItem> getDetailItemsByItemId(Long itemId);
}
