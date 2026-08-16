package com.mathink.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 反馈请求 DTO
 */
@Data
public class FeedbackDTO {

    /**
     * 历史记录ID
     */
    @NotNull(message = "历史记录ID不能为空")
    private Long historyId;

    /**
     * 反馈值: 1=正确, 2=错误
     */
    @NotNull(message = "反馈值不能为空")
    private Integer feedback;
}
