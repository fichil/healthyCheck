package com.cykj.service.impl;

import com.cykj.dto.ResponseDto;
import com.cykj.mapper.ItemMapper;
import com.cykj.pojo.DetailItem;
import com.cykj.pojo.Item;
import com.cykj.service.ItemService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhangzhenhua on 2024/8/12.
 * 项目模块服务层实现类
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemMapper itemMapper;

    /**
     * 修改项目信息
     * @param item
     * @return
     */
    @Override
    public ResponseDto updateItemById(Item item) {
        int i = itemMapper.updateItemById(item);
        if (i > 0) {
            return ResponseDto.success("修改项目信息成功",null);
        } else {
            return ResponseDto.fail("修改项目信息失败");
        }
    }

    /**
     * 根据项目名称模糊搜索
     * @param itemName
     * @param pageCurrent
     * @param pageSize
     * @return
     */
    @Override
    public ResponseDto getItemByNameLike(String itemName, int pageCurrent, int pageSize) {
        //启动分页拦截器
        Page<Object> pages = PageHelper.startPage(pageCurrent, pageSize);
        List<Item> items = itemMapper.getItemByNameLike(itemName);
        PageInfo<Object> pageInfo = new PageInfo<>(pages);

        return ResponseDto.success("根据项目名称模糊搜索成功", pageInfo);
    }

    /**
     * 添加项目
     * @param item
     * @return
     */
    @Override
    public ResponseDto addItem(Item item) {
        int i = itemMapper.addItem(item);
        if (i > 0) {
            return ResponseDto.success("添加项目成功",null);
        } else {
            return ResponseDto.fail("添加项目失败");
        }
    }
}
