package com.mathink.controller;

import com.mathink.common.PageResult;
import com.mathink.common.Result;
import com.mathink.dto.FeedbackDTO;
import com.mathink.dto.HistoryQueryDTO;
import com.mathink.dto.RecognizeUploadDTO;
import com.mathink.entity.UserStats;
import com.mathink.service.RecognizeService;
import com.mathink.vo.HistoryVO;
import com.mathink.vo.RecognizeVO;
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
    public Result<RecognizeVO> recognizeUpload(@Valid @RequestBody RecognizeUploadDTO dto) {
        RecognizeVO result = recognizeService.recognizeUpload(dto);
        return Result.success(result);
    }

    /**
     * 提交反馈 (标记正确/错误)
     */
    @PostMapping("/feedback")
    public Result<Void> submitFeedback(@Valid @RequestBody FeedbackDTO dto) {
        recognizeService.submitFeedback(dto);
        return Result.success();
    }

    /**
     * 获取用户统计
     */
    @GetMapping("/stats")
    public Result<UserStats> getUserStats() {
        UserStats stats = recognizeService.getUserStats();
        return Result.success(stats);
    }

    /**
     * 分页查询当前用户的历史记录
     */
    @GetMapping("/history")
    public Result<PageResult<HistoryVO>> getHistoryList(@Valid HistoryQueryDTO dto) {
        PageResult<HistoryVO> pageResult = recognizeService.getHistoryList(dto);
        return Result.success(pageResult);
    }

    /**
     * 删除历史记录
     */
    @DeleteMapping("/history/{id}")
    public Result<Void> deleteHistory(@PathVariable Long id) {
        recognizeService.deleteHistory(id);
        return Result.success();
    }
}
