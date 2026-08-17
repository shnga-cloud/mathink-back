package com.mathink.service;

import com.mathink.common.PageResult;
import com.mathink.dto.FeedbackDTO;
import com.mathink.dto.HistoryQueryDTO;
import com.mathink.dto.RecognizeUploadDTO;
import com.mathink.entity.UserStats;
import com.mathink.vo.HistoryVO;
import com.mathink.vo.RecognizeVO;

/**
 * 识别 Service 接口
 */
public interface RecognizeService {

    RecognizeVO recognizeUpload(Long userId, RecognizeUploadDTO dto);

    void submitFeedback(Long userId, FeedbackDTO dto);

    UserStats getUserStats(Long userId);

    /**
     * 分页查询当前用户的历史记录
     */
    PageResult<HistoryVO> getHistoryList(Long userId, HistoryQueryDTO dto);

    /**
     * 删除历史记录 (只能删除自己的)
     */
    void deleteHistory(Long userId, Long historyId);
}
