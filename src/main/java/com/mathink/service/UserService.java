package com.mathink.service;

import com.mathink.dto.BindEmailDTO;
import com.mathink.dto.LoginDTO;
import com.mathink.dto.RegisterDTO;
import com.mathink.vo.LoginVO;

/**
 * 用户 Service 接口
 */
public interface UserService {

    LoginVO login(LoginDTO loginDTO);

    void register(RegisterDTO registerDTO);

//    绑定邮箱
    void bindEmail(BindEmailDTO dto);
}
