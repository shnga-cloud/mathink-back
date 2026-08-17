package com.mathink.controller;

import com.mathink.common.Result;
import com.mathink.dto.BindEmailDTO;
import com.mathink.dto.LoginDTO;
import com.mathink.dto.RegisterDTO;
import com.mathink.interceptor.JwtInterceptor;
import com.mathink.service.UserService;
import com.mathink.vo.LoginVO;
import com.mathink.vo.RegisterVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public Result<LoginVO> login(@Valid @RequestBody LoginDTO loginDTO) {
        LoginVO loginVO = userService.login(loginDTO);
        return Result.success(loginVO);
    }

    @PostMapping("/register")
    public Result<RegisterVO> register(@Valid @RequestBody RegisterDTO registerDTO) {
        RegisterVO registerVO = userService.register(registerDTO);
        return Result.success(registerVO);
    }

    /**
     * 绑定邮箱
     */
    @PostMapping("/bind-email")
    public Result<Void> bindEmail(
            HttpServletRequest request,
            @Valid @RequestBody BindEmailDTO dto
    ) {
        Long userId = (Long) request.getAttribute(JwtInterceptor.CURRENT_USER_ID);
        userService.bindEmail(userId, dto);
        return Result.success();
    }
}
