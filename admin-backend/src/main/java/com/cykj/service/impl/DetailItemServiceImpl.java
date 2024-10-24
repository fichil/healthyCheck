package com.cykj.service.impl;

import com.cykj.dto.ResponseDto;
import com.cykj.mapper.DetailItemMapper;
import com.cykj.pojo.Department;
import com.cykj.pojo.DetailItem;
import com.cykj.service.DetailItemService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhangzhenhua on 2024/8/12.
 * DetailItemServiceImpl 实现 DetailItemService 接口
 */
@Service
public class DetailItemServiceImpl implements DetailItemService {

    @Autowired
    private DetailItemMapper detailItemMapper;

    /**
     * 获取所有细项
     * @return
     */
    @Override
    public ResponseDto getAllDetail(int pageCurrent, int pageSize) {

        //启动分页拦截器
        Page<Object> pages = PageHelper.startPage(pageCurrent, pageSize);
        List<DetailItem> allDetail = detailItemMapper.getAllDetail();
        PageInfo<Object> pageInfo = new PageInfo<>(pages);
        return ResponseDto.success("查询所有细项成功",pageInfo);
    }

    /**
     * 修改细项信息
     * @param detailItem
     * @return
     */
    @Override
    public ResponseDto updateDetailItemById(DetailItem detailItem) {
        int i = detailItemMapper.updateDetailItemById(detailItem);
        if (i > 0) {
            return ResponseDto.success("修改细项信息成功",null);
        } else {
            return ResponseDto.fail("修改细项信息失败");
        }
    }

    /**
     * 根据细项名称模糊搜索
     * @param detailItemName
     * @param pageCurrent
     * @param pageSize
     * @return
     */
    @Override
    public ResponseDto getDetailItemByNameLike(String detailItemName, int pageCurrent, int pageSize) {
        //启动分页拦截器
        Page<Object> pages = PageHelper.startPage(pageCurrent, pageSize);
        List<DetailItem> detailItems = detailItemMapper.getDetailItemByNameLike(detailItemName);
        PageInfo<Object> pageInfo = new PageInfo<>(pages);

        return ResponseDto.success("根据细项名称模糊搜索成功", pageInfo);
    }

    /**
     * 添加细项
     * @param detailItem
     * @return
     */
    @Override
    public ResponseDto addDetail(DetailItem detailItem) {
        int i = detailItemMapper.addDetailItem(detailItem);
        if (i > 0) {
            return ResponseDto.success("添加细项成功",null);
        } else {
            return ResponseDto.fail("添加细项失败");
        }
    }

}
