package com.cykj.controller;

import com.cykj.dto.ResponseDto;
import com.cykj.pojo.Department;
import com.cykj.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by zhangzhenhua on 2024/8/11.
 * 科室模块控制层
 */
@Controller
@RequestMapping("department")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    /**
     * 查询所有科室
     * @param pageCurrent
     * @param pageSize
     * @return
     */
    @RequestMapping("/getAllDepartment")
    @ResponseBody
    public ResponseDto getAllDepartment(int pageCurrent, int pageSize) {
        return departmentService.getAllDepartments(pageCurrent, pageSize);
    }

    /**
     * 修改科室信息
     * @param department
     * @return
     */
    @RequestMapping("/updateDepartmentById")
    @ResponseBody
    public ResponseDto updateDepartmentById(@RequestBody Department department) {
        return departmentService.updateDepartmentById(department);
    }

    /**
     * 根据科室名称模糊搜索
     * @param departmentName
     * @return
     */
    @RequestMapping("/getDepartmentByNameLike")
    @ResponseBody
    public ResponseDto getDepartmentByNameLike(String departmentName,int pageCurrent, int pageSize) {
        return departmentService.getDepartmentByNameLike(departmentName,pageCurrent,pageSize);
    }

    /**
     * 添加科室
     * @param department
     * @return
     */
    @RequestMapping("/addDepartment")
    @ResponseBody
    public ResponseDto addDepartment(@RequestBody Department department) {
        return departmentService.addDepartment(department);
    }

}
