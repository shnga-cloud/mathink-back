package com.mathink.controller;

import com.mathink.common.Result;
import com.mathink.dto.BindEmailDTO;
import com.mathink.dto.LoginDTO;
import com.mathink.dto.RegisterDTO;
import com.mathink.service.UserService;
import com.mathink.vo.LoginVO;
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
    public Result<Void> register(@Valid @RequestBody RegisterDTO registerDTO) {
        userService.register(registerDTO);
        return Result.success();
    }

    @PostMapping("/bind-email")
    public Result<Void> bindEmail(@Valid @RequestBody BindEmailDTO dto) {
        userService.bindEmail(dto);
        return Result.success();
    }
}
