package com.mathink.service;

import com.mathink.dto.LoginDTO;
import com.mathink.vo.LoginVO;

/**
 * 用户 Service 接口
 */
public interface UserService {

    /**
     * 用户登录
     *
     * @param loginDTO 登录请求参数
     * @return 登录响应
     */
    LoginVO login(LoginDTO loginDTO);
}
