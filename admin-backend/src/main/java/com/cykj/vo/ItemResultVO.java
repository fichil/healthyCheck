package com.cykj.vo;

import com.cykj.pojo.DetailResult;
import com.cykj.pojo.ItemSummary;
import lombok.Data;

import java.util.List;

/**
 * Created by zhangzhenhua on 2024/8/22.
 * 接收体检项目结果VO
 */
@Data
public class ItemResultVO {
    private List<ItemSummary> results;
    private String nowOrderNumber;
}
