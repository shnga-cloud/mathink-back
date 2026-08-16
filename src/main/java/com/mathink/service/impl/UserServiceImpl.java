package com.mathink.service.impl;

import com.mathink.dto.LoginDTO;
import com.mathink.entity.User;
import com.mathink.exception.BusinessException;
import com.mathink.mapper.UserMapper;
import com.mathink.service.UserService;
import com.mathink.util.JwtUtil;
import com.mathink.util.PasswordUtil;
import com.mathink.vo.LoginVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 用户 Service 实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final JwtUtil jwtUtil;

    @Override
    public LoginVO login(LoginDTO loginDTO) {
        // 1. 根据用户名查询用户
        User user = userMapper.findByUsername(loginDTO.getUsername());
        if (user == null) {
            throw new BusinessException("用户名或密码错误");
        }

        // 2. 校验密码 (BCrypt)
        if (!PasswordUtil.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new BusinessException("用户名或密码错误");
        }

        // 3. 生成 JWT Token
        String token = jwtUtil.generateToken(user.getId(), user.getUsername());

        // 4. 封装返回数据
        return LoginVO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .token(token)
                .build();
    }

}
