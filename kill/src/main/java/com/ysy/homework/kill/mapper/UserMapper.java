package com.ysy.homework.kill.mapper;

import com.ysy.homework.kill.pojo.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @anthor silenceYin
 * @date 2022/4/30 - 17:35
 */
@Mapper
public interface UserMapper {

    /**
     * 根据用户id查询用户
     */
    User findUserById(Integer uid);
}
