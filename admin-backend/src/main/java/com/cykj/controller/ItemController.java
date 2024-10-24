package com.cykj.controller;

import com.cykj.dto.ResponseDto;
import com.cykj.pojo.Item;
import com.cykj.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by zhangzhenhua on 2024/8/12.
 * 项目模块控制层
 */
@Controller
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    /**
     * 添加项目
     * @param item
     * @return
     */
    @ResponseBody
    @RequestMapping("addItem")
    public ResponseDto addItem(@RequestBody Item item){
        return itemService.addItem(item);
    }

    /**
     * 根据项名名称模糊搜索
     * @param itemName
     * @param pageCurrent
     * @param pageSize
     * @return
     */
    @ResponseBody
    @RequestMapping("getItemByNameLike")
    public ResponseDto getItemByNameLike(String itemName,int pageCurrent, int pageSize){
        return itemService.getItemByNameLike(itemName, pageCurrent, pageSize);
    }

    /**
     * 修改项目信息
     * @param item
     * @return
     */
    @ResponseBody
    @RequestMapping("updateItemById")
    public ResponseDto updateItemById(@RequestBody Item item){
        return itemService.updateItemById(item);
    }
}
