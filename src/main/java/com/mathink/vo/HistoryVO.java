package com.mathink.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 历史记录 VO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HistoryVO {

    private Long id;
    private String imageUrl;
    private String resultType;
    private String latexResult;
    private String smilesResult;
    private String svgResult;
    private BigDecimal confidence;
    private Integer feedback;
    private LocalDateTime createdAt;
}
