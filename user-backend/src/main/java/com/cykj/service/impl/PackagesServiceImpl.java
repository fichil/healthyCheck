package com.cykj.service.impl;

import com.cykj.dto.ResponseDto;
import com.cykj.mapper.PackageMapper;
import com.cykj.service.PackagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zhangzhenhua on 2024/8/16.
 * 套餐服务层接口实现类
 */
@Service
public class PackagesServiceImpl implements PackagesService {

    @Autowired
    private PackageMapper packageMapper;

    /**
     * 获取所有菜单
     * @return
     */
    @Override
    public ResponseDto getAllPackages() {
        return ResponseDto.success("获取套餐成功", packageMapper.getAllPackages());
    }
}
