package com.cykj.mapper;

import com.cykj.pojo.Menu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by zhangzhenhua on 2024/8/10.
 */
public interface MenuMapper {
    //根据角色id查询菜单
    List<Menu> getMenusByRoleId(@Param("roleId") int roleId);

}
