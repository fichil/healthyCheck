package com.cykj.service.impl;

import com.cykj.dto.ResponseDto;
import com.cykj.mapper.StaffMapper;
import com.cykj.pojo.Staff;
import com.cykj.service.StaffService;
import com.cykj.util.JwtUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * Created by zhangzhenhua on 2024/8/8.
 * 员工Service实现类
 */
@Service
public class StaffServiceImpl implements StaffService {

    // 引入Mapper
    @Autowired
    private StaffMapper staffMapper;

    /**
     *  员工登陆
     * @param account
     * @param password
     * @return
     */
    @Override
    public ResponseDto getStaffByLogin(String account, String password) {
        Staff loginStaff = staffMapper.getStaffByLogin(account,password);
        if (loginStaff != null) {
            //生成token
            HashMap<String, Object> data = new HashMap<>();
            data.put("staffInfo", loginStaff);
            String token = JwtUtils.generateToken(data);
            return ResponseDto.success(loginStaff.getNickname()+"登陆成功", token);
        }else {
            return ResponseDto.fail("账号或密码错误");
        }
    }
}
