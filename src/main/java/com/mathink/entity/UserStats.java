package com.mathink.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 用户统计实体类
 * 对应数据库表: user_stats
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserStats {

    /**
     * 统计ID (主键, 自增)
     */
    private Long id;

    /**
     * 用户ID (逻辑外键, 唯一)
     */
    private Long userId;

    /**
     * 累计识别次数
     */
    private Integer totalCount;

    /**
     * 用户反馈正确次数
     */
    private Integer correctCount;

    /**
     * 用户反馈错误次数
     */
    private Integer wrongCount;

    /**
     * 最后识别时间
     */
    private LocalDateTime lastRecognizeAt;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;
}
