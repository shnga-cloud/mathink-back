package com.mathink.service;

import com.mathink.dto.FeedbackDTO;
import com.mathink.dto.RecognizeUploadDTO;
import com.mathink.entity.UserStats;
import com.mathink.vo.RecognizeVO;

/**
 * 识别 Service 接口
 */
public interface RecognizeService {

    /**
     * 上传图片识别 LaTeX 公式
     *
     * @param userId 用户ID
     * @param dto    上传请求参数
     * @return 识别结果
     */
    RecognizeVO recognizeUpload(Long userId, RecognizeUploadDTO dto);

    /**
     * 提交反馈
     *
     * @param userId 用户ID
     * @param dto    反馈参数
     */
    void submitFeedback(Long userId, FeedbackDTO dto);

    /**
     * 获取用户统计
     *
     * @param userId 用户ID
     * @return 用户统计
     */
    UserStats getUserStats(Long userId);
}
