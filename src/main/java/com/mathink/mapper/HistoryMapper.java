package com.mathink.mapper;

import com.mathink.entity.History;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 识别历史 Mapper 接口
 */
@Mapper
public interface HistoryMapper {

    int insert(History history);

    History findById(@Param("id") Long id);

    int updateFeedback(@Param("id") Long id, @Param("feedback") Integer feedback);

    /**
     * 分页查询指定用户的历史记录
     */
    List<History> findByUserId(@Param("userId") Long userId,
                               @Param("offset") int offset,
                               @Param("size") int size);

    /**
     * 统计指定用户的历史记录数量
     */
    Long countByUserId(@Param("userId") Long userId);

    /**
     * 删除历史记录 (只能删除自己的)
     */
    int deleteById(@Param("id") Long id, @Param("userId") Long userId);
}
