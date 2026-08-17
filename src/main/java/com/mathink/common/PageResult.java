package com.mathink.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 分页结果封装类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResult<T> {

    /**
     * 数据列表
     */
    private List<T> list;

    /**
     * 总数量
     */
    private Long total;

    /**
     * 当前页
     */
    private Integer page;

    /**
     * 每页数量
     */
    private Integer size;
}
