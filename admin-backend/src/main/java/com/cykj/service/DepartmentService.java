package com.cykj.service;

import com.cykj.dto.ResponseDto;
import com.cykj.pojo.Department;

/**
 * Created by zhangzhenhua on 2024/8/11.
 * 科室Service
 */
public interface DepartmentService {
    //获取所有科室
    ResponseDto getAllDepartments(int pageCurrent, int pageSize);
    //修改科室信息
    ResponseDto updateDepartmentById(Department department);
    //根据科室名称模糊搜索
    ResponseDto getDepartmentByNameLike(String departmentName,int pageCurrent, int pageSize);
    //添加科室
    ResponseDto addDepartment(Department department);
}
