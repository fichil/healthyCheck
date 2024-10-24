package com.cykj.service;

import com.cykj.dto.ResponseDto;
import com.cykj.pojo.Item;

/**
 * Created by zhangzhenhua on 2024/8/12.
 * 项目模块Service
 */
public interface ItemService {
    //修改项目信息
    ResponseDto updateItemById(Item item);
    //根据项目名称模糊搜索
    ResponseDto getItemByNameLike(String itemName,int pageCurrent, int pageSize);
    //添加项目
    ResponseDto addItem(Item item);
}
