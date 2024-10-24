package com.cykj.service;

import com.cykj.dto.ResponseDto;
import com.cykj.pojo.Packages;

/**
 * Created by zhangzhenhua on 2024/8/12.
 * 套餐服务层接口
 */
public interface PackageService {
    //修改套餐信息
    ResponseDto updatePackageById(Packages packages);
    //根据套餐名称模糊搜索
    ResponseDto getPackageByNameLike(String packageName,int pageCurrent, int pageSize);
    //添加套餐
    ResponseDto addPackage(Packages packages);
    //根据用户余额获取可购买套
    ResponseDto getAvailablePackages(double maxPrice);


}
