package com.cykj.mapper;

import com.cykj.pojo.Packages;

import java.util.List;

/**
 * Created by zhangzhenhua on 2024/8/16.
 * 套餐的Mapper
 */
public interface PackageMapper {

    //查找所有套餐并附带项目
    List<Packages> getAllPackages();

    //根据订单号查询套餐
    Packages getPackageByOrderNumber(String orderNumber);
}
