package com.mathink.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.Data;

/**
 * 历史记录查询 DTO
 */
@Data
public class HistoryQueryDTO {

    @Positive(message = "页码必须大于0")
    private Integer page = 1;

    @Min(value = 1, message = "每页大小至少为1")
    @Max(value = 50, message = "每页大小最多为50")
    private Integer size = 10;
}
