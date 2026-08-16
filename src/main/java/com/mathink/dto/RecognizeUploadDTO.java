package com.mathink.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * 上传图片识别请求 DTO
 */
@Data
public class RecognizeUploadDTO {

    /**
     * 图片文件
     */
    @NotNull(message = "请上传图片")
    private MultipartFile file;
}
