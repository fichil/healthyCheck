package com.cykj.mapper;

import com.cykj.pojo.Department;

import java.util.List;

/**
 * Created by zhangzhenhua on 2024/8/11.
 * 科室Mapper
 */
public interface DepartmentMapper {
    // 获取所有科室
    List<Department> getAllDepartment();

    //修改科室信息
    int updateDepartmentById(Department department);

    //根据科室名称模糊搜索
    List<Department> getDepartmentByNameLike(String departmentName);

    //添加科室
    int addDepartment(Department department);

}
