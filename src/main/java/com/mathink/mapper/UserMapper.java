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

    int insert(User user);

    User findById(@Param("id") Long id);

    User findByEmail(@Param("email") String email);

    int updateEmail(@Param("id") Long id, @Param("email") String email);
}
