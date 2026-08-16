package com.mathink.mapper;

import com.mathink.entity.History;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 识别历史 Mapper 接口
 */
@Mapper
public interface HistoryMapper {

    /**
     * 新增历史记录
     *
     * @param history 历史记录
     * @return 影响行数
     */
    int insert(History history);

    /**
     * 根据ID查询历史记录
     *
     * @param id 历史记录ID
     * @return 历史记录
     */
    History findById(@Param("id") Long id);

    /**
     * 更新反馈
     *
     * @param id 历史记录ID
     * @param feedback 反馈值: 0=未反馈, 1=正确, 2=错误
     * @return 影响行数
     */
    int updateFeedback(@Param("id") Long id, @Param("feedback") Integer feedback);
}
