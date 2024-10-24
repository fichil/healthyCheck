package com.cykj.mapper;

import com.cykj.pojo.Staff;
import org.apache.ibatis.annotations.Param;

/**
 * Created by zhangzhenhua on 2024/8/8.
 * 员工Mapper
 */
public interface StaffMapper {

    //员工登陆
    Staff getStaffByLogin(@Param("account") String account, @Param("password") String password);
}
