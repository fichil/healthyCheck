package com.cykj.controller;

import com.cykj.dto.ResponseDto;
import com.cykj.pojo.Packages;
import com.cykj.service.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by zhangzhenhua on 2024/8/12.
 */
@Controller
@RequestMapping("/package")
public class PackageController {

    @Autowired
    private PackageService packageService;

    /**
     * 添加套餐
     * @param packages
     * @return
     */
    @ResponseBody
    @RequestMapping("addPackage")
    public ResponseDto addItem(@RequestBody Packages packages){
        return packageService.addPackage(packages);
    }


    /**
     * 根据套餐名称模糊搜索
     * @param packageName
     * @param pageCurrent
     * @param pageSize
     * @return
     */
    @ResponseBody
    @RequestMapping("getPackageByNameLike")
    public ResponseDto getPackageByNameLike(String packageName,int pageCurrent, int pageSize){
        System.out.println("模糊搜索用户名："+packageName);
        return packageService.getPackageByNameLike(packageName, pageCurrent, pageSize);
    }

    /**
     * 修改套餐信息
     * @param packages
     * @return
     */
    @ResponseBody
    @RequestMapping("updatePackageById")
    public ResponseDto updatePackageById(@RequestBody Packages packages){
        return packageService.updatePackageById(packages);
    }

    /**
     * 根据用户余额获取可购买套餐
     * @param maxPrice
     * @return
     */
    @RequestMapping("getAvailablePackages")
    @ResponseBody
    public ResponseDto getAvailablePackages(double maxPrice){
        return packageService.getAvailablePackages(maxPrice);
    }

}
