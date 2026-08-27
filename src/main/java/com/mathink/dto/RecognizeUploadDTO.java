package com.mathink.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 上传图片识别请求 DTO
 */
@Data
public class RecognizeUploadDTO {

    /**
     * 图片 Base64 字符串
     */
    @NotBlank(message = "请上传图片")
    private String image;

    /**
     * 识别模式: math(数学) 或 chem(化学), 默认 math
     */
    private String mode = "math";
}
