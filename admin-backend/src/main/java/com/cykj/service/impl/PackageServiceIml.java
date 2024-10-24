package com.cykj.service.impl;

import com.cykj.dto.ResponseDto;
import com.cykj.mapper.PackageMapper;
import com.cykj.pojo.Item;
import com.cykj.pojo.Packages;
import com.cykj.service.PackageService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhangzhenhua on 2024/8/12.
 * 套餐服务层实现类
 */
@Service
public class PackageServiceIml implements PackageService {

    @Autowired
    private PackageMapper packagesMapper;

    /**
     * 修改套餐信息
     * @param packages
     * @return
     */
    @Override
    public ResponseDto updatePackageById(Packages packages) {
        int i = packagesMapper.updatePackageById(packages);
        if (i > 0) {
            return ResponseDto.success("修改套餐信息成功",null);
        } else {
            return ResponseDto.fail("修改套餐信息失败");
        }
    }

    /**
     * 根据套餐名称模糊搜索套餐
     * @param packageName
     * @param pageCurrent
     * @param pageSize
     * @return
     */
    @Override
    public ResponseDto getPackageByNameLike(String packageName, int pageCurrent, int pageSize) {
        //启动分页拦截器
        Page<Object> pages = PageHelper.startPage(pageCurrent, pageSize);
        List<Packages> packages = packagesMapper.getPackageByNameLike(packageName);
        PageInfo<Object> pageInfo = new PageInfo<>(pages);

        return ResponseDto.success("根据套餐名称模糊搜索成功", pageInfo);
    }

    /**
     * 添加套餐
     * @param packages
     * @return
     */
    @Override
    public ResponseDto addPackage(Packages packages) {
        int i = packagesMapper.addPackage(packages);
        if (i > 0) {
            return ResponseDto.success("添加套餐成功",null);
        } else {
            return ResponseDto.fail("添加套餐失败");
        }
    }

    /**
     * 根据用户余额获取可购买套餐
     * @param maxPrice
     * @return
     */
    @Override
    public ResponseDto getAvailablePackages(double maxPrice) {
        List<Packages> packages = packagesMapper.getAvailablePackages(maxPrice);
        if (packages!=null && packages.size()>0){
            return ResponseDto.success("根据用户余额获取可购买套餐成功", packages);
        }else {
            return ResponseDto.fail("根据用户余额获取可购买套餐失败，未找到相关的套餐");
        }
    }


}
