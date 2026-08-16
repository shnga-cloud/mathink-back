package com.mathink.controller;

import com.mathink.common.Result;
import com.mathink.dto.FeedbackDTO;
import com.mathink.dto.RecognizeUploadDTO;
import com.mathink.entity.UserStats;
import com.mathink.interceptor.JwtInterceptor;
import com.mathink.service.RecognizeService;
import com.mathink.vo.RecognizeVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 识别 Controller
 */
@RestController
@RequestMapping("/recognize")
@RequiredArgsConstructor
public class RecognizeController {

    private final RecognizeService recognizeService;

    /**
     * 上传图片识别 LaTeX 公式
     */
    @PostMapping("/upload")
    public Result<RecognizeVO> recognizeUpload(
            HttpServletRequest request,
            @Valid RecognizeUploadDTO dto
    ) {
        Long userId = (Long) request.getAttribute(JwtInterceptor.CURRENT_USER_ID);
        RecognizeVO result = recognizeService.recognizeUpload(userId, dto);
        return Result.success(result);
    }

    /**
     * 提交反馈 (标记正确/错误)
     */
    @PostMapping("/feedback")
    public Result<Void> submitFeedback(
            HttpServletRequest request,
            @Valid @RequestBody FeedbackDTO dto
    ) {
        Long userId = (Long) request.getAttribute(JwtInterceptor.CURRENT_USER_ID);
        recognizeService.submitFeedback(userId, dto);
        return Result.success();
    }

    /**
     * 获取用户统计
     */
    @GetMapping("/stats")
    public Result<UserStats> getUserStats(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute(JwtInterceptor.CURRENT_USER_ID);
        UserStats stats = recognizeService.getUserStats(userId);
        return Result.success(stats);
    }
}
