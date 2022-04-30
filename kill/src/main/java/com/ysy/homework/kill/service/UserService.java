package com.ysy.homework.kill.service;

/**
 * @anthor silenceYin
 * @date 2022/4/30 - 17:27
 */
public interface UserService {

    /**
     * 向reids中写入访问次数
     */
    Integer addUserReadCount(Integer uid);

    /**
     * 判断单位时间调用次数
     */
    Boolean getUserCount(Integer uid);

}
