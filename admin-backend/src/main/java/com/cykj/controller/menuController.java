package com.cykj.controller;

import com.cykj.dto.ResponseDto;
import com.cykj.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by zhangzhenhua on 2024/8/10.
 * 菜单模块控制类
 */
@Controller
@RequestMapping("/menu")
public class menuController {

    // 引入MenuService 并注入
    @Autowired
    private MenuService menuService;

    /**
     * 根据角色id查询菜单
     * @param roleId
     * @return
     */
    @RequestMapping("/getMenusByRoleId")
    @ResponseBody
    public ResponseDto getMenusByRoleId(@RequestParam("roleId") int roleId) {
        return menuService.getMenusByRoleId(roleId);
    }


}
