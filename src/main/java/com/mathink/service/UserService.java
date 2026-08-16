package com.mathink.service;

import com.mathink.dto.LoginDTO;
import com.mathink.dto.RegisterDTO;
import com.mathink.vo.LoginVO;
import com.mathink.vo.RegisterVO;

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

    /**
     * 用户注册
     *
     * @param registerDTO 注册请求参数
     * @return 注册响应
     */
    RegisterVO register(RegisterDTO registerDTO);
}
