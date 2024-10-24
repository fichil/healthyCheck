package com.cykj.service.impl;

import com.cykj.dto.ResponseDto;
import com.cykj.mapper.DepartmentMapper;
import com.cykj.pojo.Department;
import com.cykj.service.DepartmentService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhangzhenhua on 2024/8/11.
 * 科室模块服务层实现类
 */
@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;

    /**
     * 查询所有科室
     * @param pageCurrent
     * @param pageSize
     * @return
     */
    @Override
    public ResponseDto getAllDepartments(int pageCurrent, int pageSize) {

        //启动分页拦截器
        Page<Object> pages = PageHelper.startPage(pageCurrent, pageSize);
        List<Department> departments = departmentMapper.getAllDepartment();
        PageInfo<Object> pageInfo = new PageInfo<>(pages);
        return ResponseDto.success("查询所有科室成功",pageInfo);
    }

    /**
     * 修改科室信息
     * @param department
     * @return
     */
    @Override
    public ResponseDto updateDepartmentById(Department department) {
        int i = departmentMapper.updateDepartmentById(department);
        if (i > 0) {
            return ResponseDto.success("修改科室信息成功",null);
        } else {
            return ResponseDto.fail("修改科室信息失败");
        }
    }

    /**
     * 根据科室名称模糊搜索
     * @param departmentName
     * @return
     */
    @Override
    public ResponseDto getDepartmentByNameLike(String departmentName,int pageCurrent, int pageSize) {

        //启动分页拦截器
        Page<Object> pages = PageHelper.startPage(pageCurrent, pageSize);
        List<Department> departments = departmentMapper.getDepartmentByNameLike(departmentName);
        PageInfo<Object> pageInfo = new PageInfo<>(pages);

        return ResponseDto.success("根据科室名称模糊搜索成功", pageInfo);
    }

    /**
     * 添加科室
     * @param department
     * @return
     */
    @Override
    public ResponseDto addDepartment(Department department) {
        int i = departmentMapper.addDepartment(department);
        if (i > 0) {
            return ResponseDto.success("添加科室成功",null);
        } else {
            return ResponseDto.fail("添加科室失败");
        }
    }
}
