package com.cykj.service;

import com.cykj.dto.ResponseDto;
import com.cykj.pojo.Department;
import com.cykj.pojo.DetailItem;

/**
 * Created by zhangzhenhua on 2024/8/12.
 * 细项相关的Service
 */
public interface DetailItemService {
    //获取所有细项
    ResponseDto getAllDetail(int pageCurrent, int pageSize);
    //修改细项信息
    ResponseDto updateDetailItemById(DetailItem detailItem);
    //根据细项名称模糊搜索
    ResponseDto getDetailItemByNameLike(String detailItemName,int pageCurrent, int pageSize);
    //添加细项
    ResponseDto addDetail(DetailItem detailItem);
}
