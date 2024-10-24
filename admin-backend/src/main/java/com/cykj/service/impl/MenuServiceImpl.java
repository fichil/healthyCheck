package com.cykj.service.impl;

import com.cykj.dto.ResponseDto;
import com.cykj.mapper.MenuMapper;
import com.cykj.pojo.Menu;
import com.cykj.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangzhenhua on 2024/8/10.
 * 菜单模块service实现类
 */
@Service
public class MenuServiceImpl implements MenuService {

    // Spring Boot中使用@Autowired来实现Bean的注入
    @Autowired
    private MenuMapper menuMapper;


    /**
     * 根据角色id查询权限菜单
     * @param roleId
     * @return
     */
    @Override
    public ResponseDto getMenusByRoleId(int roleId) {
        ArrayList<Object> menuData = new ArrayList<>();
        List<Menu> allMenu = menuMapper.getMenusByRoleId(roleId);
        //转换成前端所需要的数据格式
        for (int i = 0; i < allMenu.size(); i++) {
            if (allMenu.get(i).getParentMenuId() == 0) {
                menuData.add(getChildMenu(allMenu.get(i), allMenu));
            }
        }
        return ResponseDto.success("根据角色id"+roleId+"查询权限菜单成功",menuData);
    }

    /**
     * 获取子菜单
     * @param nowMenu
     * @param allMenu
     * @return
     */
    public Menu getChildMenu(Menu nowMenu,List<Menu> allMenu) {
        for (Menu menu : allMenu) {
            if (nowMenu.getMenuId() == menu.getParentMenuId()) {
                nowMenu.getChildren().add(getChildMenu(menu, allMenu));
            }
        }
        return nowMenu;
    }
}
