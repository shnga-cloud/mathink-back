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
     * 识别类型: math 或 chem
     */
    private String type;

    /**
     * 数学模式: LaTeX 公式
     */
    private String latex;

    /**
     * 化学模式: chemfig LaTeX 字符串, 如 \chemfig {...}
     */
    private String ssml;

    /**
     * 化学模式: LaTeX 编译渲染的 SVG 字符串 (透明背景), 直接透传给前端
     */
    private String svg;

    /**
     * 识别置信度
     */
    private BigDecimal confidence;

    /**
     * 原始图片URL
     */
    private String imageUrl;
}
