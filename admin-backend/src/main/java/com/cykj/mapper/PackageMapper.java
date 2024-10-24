package com.cykj.mapper;

import com.cykj.pojo.Item;
import com.cykj.pojo.Orders;
import com.cykj.pojo.Packages;

import java.util.List;

/**
 * Created by zhangzhenhua on 2024/8/12.
 * 套餐Mapper
 */
public interface PackageMapper {
    //修改套餐信息
    int updatePackageById(Packages packages);

    //根据套餐名称模糊搜索
    List<Packages> getPackageByNameLike(String packageName);

    //添加套餐
    int addPackage(Packages packages);

    //根据用户余额获取可购买套餐
    List<Packages> getAvailablePackages(double maxPrice);

    //根据套餐id查询套餐
    Packages getPackageById(int packageId);

    //根据订单号查询套餐
    Packages getPackageByOrderNumber(String orderNumber);


}
