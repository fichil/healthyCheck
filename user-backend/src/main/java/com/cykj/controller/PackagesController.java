package com.cykj.controller;

import com.cykj.dto.ResponseDto;
import com.cykj.service.PackagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by zhangzhenhua on 2024/8/16.
 */
@Controller
@RequestMapping("/packages")
public class PackagesController {

    @Autowired
    private PackagesService packagesService;

    /**
     * 获取所有套餐
     * @return
     */
    @RequestMapping("/getAllPackages")
    @ResponseBody
    public ResponseDto getAllPackages() {
       return packagesService.getAllPackages();
    }

}
