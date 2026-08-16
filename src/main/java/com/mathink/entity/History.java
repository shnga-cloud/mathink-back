package com.mathink.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 识别历史实体类
 * 对应数据库表: recognition_history
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class History {

    /**
     * 历史记录ID (主键, 自增)
     */
    private Long id;

    /**
     * 用户ID (逻辑外键)
     */
    private Long userId;

    /**
     * 原始图片路径
     */
    private String imageUrl;

    /**
     * 识别出的LaTeX公式
     */
    private String latexResult;

    /**
     * 识别置信度
     */
    private BigDecimal confidence;

    /**
     * 用户反馈: 0=未反馈, 1=识别正确, 2=识别错误
     */
    private Integer feedback;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;
}
