package com.mathink.service.impl;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.mathink.common.PageResult;
import com.mathink.dto.FeedbackDTO;
import com.mathink.dto.HistoryQueryDTO;
import com.mathink.dto.RecognizeUploadDTO;
import com.mathink.entity.History;
import com.mathink.entity.UserStats;
import com.mathink.exception.BusinessException;
import com.mathink.mapper.HistoryMapper;
import com.mathink.mapper.UserStatsMapper;
import com.mathink.service.RecognizeService;
import com.mathink.vo.HistoryVO;
import com.mathink.vo.RecognizeVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * 识别 Service 实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RecognizeServiceImpl implements RecognizeService {

    private final HistoryMapper historyMapper;
    private final UserStatsMapper userStatsMapper;

    @Value("${app.upload.dir:uploads}")
    private String uploadDir;

    @Value("${recognize.python-service.url:http://localhost:8000}")
    private String pythonServiceUrl;

    @Override
    @Transactional
    public RecognizeVO recognizeUpload(Long userId, RecognizeUploadDTO dto) {
        // 1. 解析 Base64
        String base64Image = dto.getImage();
        byte[] imageBytes = parseBase64(base64Image);

        log.info("收到图片大小: {} KB", imageBytes.length / 1024);  // 新增这行

        // 校验大小 (5MB 限制)
        if (imageBytes.length > 5 * 1024 * 1024) {
            throw new BusinessException("图片大小不能超过5MB");
        }

        // 2. 保存图片到本地
        String imageUrl = saveImage(imageBytes, base64Image);

        // 3. 调用 Python 识别服务
        RecognizeResult recognizeResult = callPythonService(base64Image);
        String latex = recognizeResult.getLatex();
        BigDecimal confidence = recognizeResult.getConfidence();

        // 4. 保存历史记录
        History history = new History();
        history.setUserId(userId);
        history.setImageUrl(imageUrl);
        history.setLatexResult(latex);
        history.setConfidence(confidence);
        historyMapper.insert(history);

        // 5. 更新用户统计 (累加识别次数)
        incrementUserStats(userId);

        log.info("图片识别成功, userId: {}, historyId: {}, latex: {}", userId, history.getId(), latex);

        // 6. 返回结果
        return RecognizeVO.builder()
                .id(history.getId())
                .latex(latex)
                .confidence(confidence)
                .imageUrl(imageUrl)
                .build();
    }

    @Override
    @Transactional
    public void submitFeedback(Long userId, FeedbackDTO dto) {
        // 1. 查询历史记录
        History history = historyMapper.findById(dto.getHistoryId());
        if (history == null) {
            throw new BusinessException("历史记录不存在");
        }

        // 2. 校验归属
        if (!history.getUserId().equals(userId)) {
            throw new BusinessException("无权操作此记录");
        }

        // 3. 校验反馈值
        Integer feedback = dto.getFeedback();
        if (feedback != 1 && feedback != 2) {
            throw new BusinessException("反馈值无效, 只能为 1(正确) 或 2(错误)");
        }

        // 4. 更新反馈
        historyMapper.updateFeedback(dto.getHistoryId(), feedback);

        // 5. 更新用户统计
        if (feedback == 1) {
            userStatsMapper.incrementCorrect(userId);
        } else {
            userStatsMapper.incrementWrong(userId);
        }

        log.info("反馈提交成功, userId: {}, historyId: {}, feedback: {}", userId, dto.getHistoryId(), feedback);
    }

    @Override
    public UserStats getUserStats(Long userId) {
        UserStats stats = userStatsMapper.findByUserId(userId);
        if (stats == null) {
            stats = createDefaultStats(userId);
        }
        return stats;
    }

    @Override
    public PageResult<HistoryVO> getHistoryList(Long userId, HistoryQueryDTO dto) {
        int page = dto.getPage();
        int size = dto.getSize();
        int offset = (page - 1) * size;

        Long total = historyMapper.countByUserId(userId);
        if (total == null || total == 0) {
            return new PageResult<>(List.of(), 0L, page, size);
        }

        List<History> histories = historyMapper.findByUserId(userId, offset, size);
        List<HistoryVO> list = histories.stream()
                .map(this::convertToHistoryVO)
                .collect(Collectors.toList());

        return new PageResult<>(list, total, page, size);
    }

    @Override
    @Transactional
    public void deleteHistory(Long userId, Long historyId) {
        int rows = historyMapper.deleteById(historyId, userId);
        if (rows == 0) {
            History history = historyMapper.findById(historyId);
            if (history == null) {
                throw new BusinessException("历史记录不存在");
            }
            throw new BusinessException("无权操作此记录");
        }
        log.info("删除历史记录成功, userId: {}, historyId: {}", userId, historyId);
    }

    /**
     * Entity 转 VO
     */
    private HistoryVO convertToHistoryVO(History h) {
        return HistoryVO.builder()
                .id(h.getId())
                .imageUrl(h.getImageUrl())
                .latexResult(h.getLatexResult())
                .confidence(h.getConfidence())
                .feedback(h.getFeedback())
                .createdAt(h.getCreatedAt())
                .build();
    }

    /**
     * 累加识别次数
     */
    private void incrementUserStats(Long userId) {
        UserStats stats = userStatsMapper.findByUserId(userId);
        if (stats == null) {
            stats = createDefaultStats(userId);
        }
        userStatsMapper.incrementTotal(userId);
    }

    /**
     * 创建默认统计记录
     */
    private UserStats createDefaultStats(Long userId) {
        UserStats stats = new UserStats();
        stats.setUserId(userId);
        stats.setTotalCount(0);
        stats.setCorrectCount(0);
        stats.setWrongCount(0);
        stats.setLastRecognizeAt(LocalDateTime.now());
        userStatsMapper.insert(stats);
        return stats;
    }

    /**
     * 保存图片到本地
     */
    private String saveImage(byte[] imageBytes, String base64Image) {
        try {
            Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String extension = getExtensionFromBase64(base64Image);
            String newFilename = UUID.randomUUID().toString() + extension;

            Path filePath = uploadPath.resolve(newFilename);
            Files.write(filePath, imageBytes);

            log.debug("图片已保存到: {}", filePath);
            return "/uploads/" + newFilename;

        } catch (IOException e) {
            log.error("保存图片失败", e);
            throw new BusinessException("保存图片失败");
        }
    }

    /**
     * 解析 Base64，支持 data:image/png;base64,xxx 格式
     */
    private byte[] parseBase64(String base64) {
        try {
            if (base64.contains(",")) {
                base64 = base64.substring(base64.indexOf(",") + 1);
            }
            return Base64.getDecoder().decode(base64);
        } catch (Exception e) {
            throw new BusinessException("图片格式错误");
        }
    }

    /**
     * 从 Base64 头部获取扩展名
     */
    private String getExtensionFromBase64(String base64) {
        if (base64.contains("image/png")) return ".png";
        if (base64.contains("image/jpeg") || base64.contains("image/jpg")) return ".jpg";
        if (base64.contains("image/gif")) return ".gif";
        if (base64.contains("image/webp")) return ".webp";
        return ".png";
    }

    /**
     * 调用 Python 微服务进行公式识别 (Base64 JSON 方式)
     */
    private RecognizeResult callPythonService(String base64Image) {
        String url = pythonServiceUrl + "/predict";

        try {
            JSONObject requestBody = new JSONObject();
            requestBody.set("image", base64Image);

            HttpResponse response = HttpRequest.post(url)
                    .header("Content-Type", "application/json")
                    .body(requestBody.toString())
                    .execute();

            JSONObject json = JSONUtil.parseObj(response.body());
            if (json.getInt("code") == 200) {
                JSONObject data = json.getJSONObject("data");
                String latex = data.getStr("latex", "");
                BigDecimal confidence = data.getBigDecimal("confidence");
                return new RecognizeResult(latex, confidence);
            }

            throw new BusinessException(json.getStr("message", "识别失败"));
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("调用 Python 识别服务失败", e);
            throw new BusinessException("识别服务暂不可用，请稍后重试");
        }
    }

    /**
     * 识别结果内部类
     */
    @lombok.Data
    private static class RecognizeResult {
        private final String latex;
        private final BigDecimal confidence;
    }
}
