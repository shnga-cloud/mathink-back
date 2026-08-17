package com.mathink.mapper;

import com.mathink.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 用户 Mapper 接口
 */
@Mapper
public interface UserMapper {

    User findByUsername(@Param("username") String username);

    /**
     * 根据邮箱查询用户
     *
     * @param email 邮箱
     * @return 用户信息
     */
    User findByEmail(@Param("email") String email);
    /**

    /**
     * 根据ID查询用户
     */
    User findById(@Param("id") Long id);

    int insert(User user);

    /**
     * 更新邮箱
     */
    int updateEmail(@Param("id") Long id, @Param("email") String email);
}
