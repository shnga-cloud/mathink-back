package com.mathink.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 用户实体类
 * 对应数据库表: user
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    /**
     * 用户ID (主键, 自增)
     */
    private Long id;

    /**
     * 用户名 (唯一, 不为空)
     */
    private String username;

    /**
     * 密码 (加密存储)
     */
    private String password;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;
}
