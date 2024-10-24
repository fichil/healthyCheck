package com.cykj.service;

import com.cykj.dto.ResponseDto;

/**
 * Created by zhangzhenhua on 2024/8/10.
 * 菜单模块的service
 */
public interface MenuService {

    //根据角色id查询菜单
    ResponseDto getMenusByRoleId(int roleId);
}
