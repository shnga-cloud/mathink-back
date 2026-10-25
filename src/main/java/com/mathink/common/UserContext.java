package com.mathink.common;

import lombok.Data;

/**
 * 用户上下文
 * 基于ThreadLocal存储当前请求线程的用户信息
 */
public class UserContext {

    private static final ThreadLocal<UserInfo> USER_THREAD_LOCAL = new ThreadLocal<>();

    private UserContext() {
    }

    /**
     * 设置当前用户信息
     *
     * @param userId   用户ID
     * @param username 用户名
     */
    public static void set(Long userId, String username) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(userId);
        userInfo.setUsername(username);
        USER_THREAD_LOCAL.set(userInfo);
    }

    /**
     * 获取当前用户ID
     *
     * @return 用户ID
     */
    public static Long getUserId() {
        UserInfo userInfo = USER_THREAD_LOCAL.get();
        return userInfo.getUserId();
    }

    /**
     * 获取当前用户名
     *
     * @return 用户名
     */
    public static String getUsername() {
        UserInfo userInfo = USER_THREAD_LOCAL.get();
        return userInfo.getUsername();
    }

    /**
     * 清除当前线程的用户信息
     * 必须在请求结束后调用,防止内存泄漏
     */
    public static void clear() {
        USER_THREAD_LOCAL.remove();
    }

    /**
     * 用户信息内部类
     */
    @Data
    private static class UserInfo {
        private Long userId;
        private String username;
    }
}
