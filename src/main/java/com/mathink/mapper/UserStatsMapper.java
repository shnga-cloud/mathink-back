package com.mathink.mapper;

import com.mathink.entity.UserStats;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 用户统计 Mapper 接口
 */
@Mapper
public interface UserStatsMapper {

    /**
     * 根据用户ID查询统计
     *
     * @param userId 用户ID
     * @return 用户统计
     */
    UserStats findByUserId(@Param("userId") Long userId);

    /**
     * 新增用户统计
     *
     * @param stats 用户统计
     * @return 影响行数
     */
    int insert(UserStats stats);

    /**
     * 更新用户统计
     *
     * @param stats 用户统计
     * @return 影响行数
     */
    int update(UserStats stats);

    /**
     * 累加识别次数
     *
     * @param userId 用户ID
     * @return 影响行数
     */
    int incrementTotal(@Param("userId") Long userId);

    /**
     * 累加正确次数
     *
     * @param userId 用户ID
     * @return 影响行数
     */
    int incrementCorrect(@Param("userId") Long userId);

    /**
     * 累加错误次数
     *
     * @param userId 用户ID
     * @return 影响行数
     */
    int incrementWrong(@Param("userId") Long userId);
}
