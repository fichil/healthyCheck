package com.cykj.controller;

import com.cykj.dto.ResponseDto;
import com.cykj.pojo.Staff;
import com.cykj.service.StaffService;
import com.cykj.util.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by zhangzhenhua on 2024/8/8.
 * 员工模块控制层
 */
@Controller
@RequestMapping("/staff")
public class StaffController {
    // 引入service
    @Autowired
    private StaffService staffService;

    /**
     * 员工登陆
     * @param staff
     * @return
     */
    @RequestMapping("/login")
    @ResponseBody
    public ResponseDto login(@RequestBody Staff staff) {
        System.out.println("进入登陆");
        return staffService.getStaffByLogin(staff.getAccount(),staff.getPassword());
    }

    /**
     * 根据token获取员工信息
     * @param token
     * @return
     */
    @RequestMapping("/getStaffInfo")
    @ResponseBody
    public ResponseDto getStaffInfo(String token){
        // 获取token中进行解析，得到用户信息
        Claims claims = JwtUtils.parseToken(token);
        return ResponseDto.success("success",claims);
    }
}
