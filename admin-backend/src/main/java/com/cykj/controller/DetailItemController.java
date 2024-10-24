package com.cykj.controller;

import com.cykj.dto.ResponseDto;
import com.cykj.pojo.DetailItem;
import com.cykj.service.DetailItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by zhangzhenhua on 2024/8/12.
 * 细项控制类
 */
@Controller
@RequestMapping("detailItem")
public class DetailItemController {

    @Autowired
    private DetailItemService detailItemService;

    /**
     * 查询所有细项
     * @param pageCurrent
     * @param pageSize
     * @return
     */
    @ResponseBody
    @RequestMapping("getAllDetailItem")
    public ResponseDto getAllDetail(int pageCurrent, int pageSize) {
        return detailItemService.getAllDetail(pageCurrent,pageSize);
    }

    /**
     * 修改细项信息
     * @param detailItem
     * @return
     */
    @ResponseBody
    @RequestMapping("updateDetailItemById")
    public ResponseDto updateDetailItem(@RequestBody DetailItem detailItem) {
        return detailItemService.updateDetailItemById(detailItem);
    }

    /**
     * 根据细项名称模糊搜索
     * @param detailItemName
     * @param pageCurrent
     * @param pageSize
     */
    @ResponseBody
    @RequestMapping("getDetailItemByNameLike")
    public ResponseDto getDetailByNameLike(String detailItemName, int pageCurrent, int pageSize) {
        return detailItemService.getDetailItemByNameLike(detailItemName, pageCurrent, pageSize);
    }


    /**
     * 添加细项
     * @param detailItem
     * @return
     */
    @ResponseBody
    @RequestMapping("addDetailItem")
    public ResponseDto addDetailItem(@RequestBody DetailItem detailItem) {
        return detailItemService.addDetail(detailItem);
    }

}
