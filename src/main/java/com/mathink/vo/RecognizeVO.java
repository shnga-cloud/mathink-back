package com.mathink.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 识别结果响应 VO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecognizeVO {

    /**
     * 历史记录ID
     */
    private Long id;

    /**
     * 识别出的LaTeX公式
     */
    private String latex;

    /**
     * 识别置信度
     */
    private BigDecimal confidence;

    /**
     * 原始图片URL
     */
    private String imageUrl;
}
