package com.cykj.service;

import com.cykj.dto.ResponseDto;
import com.cykj.pojo.Staff;

/**
 * Created by zhangzhenhua on 2024/8/8.
 * 员工模块的Service层
 */
public interface StaffService {
    // 员工登陆
    ResponseDto getStaffByLogin(String account, String password);
}
