package com.ysy.homework.kill.service.impl;

import com.ysy.homework.kill.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @anthor silenceYin
 * @date 2022/4/30 - 17:28
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Integer addUserReadCount(Integer uid) {
        // 根据不同uid生成调用次数的key
        String readKey = "READ_" + uid;
        // 获取redis中key的调用次数
        String readCount = stringRedisTemplate.opsForValue().get(readKey);
        int read = -1;
        if (readCount == null) { // 第一次调用
            // 存入redis中设置0
            log.info("=======================>第一次调用");
            stringRedisTemplate.opsForValue().set(readKey, "0", 3600, TimeUnit.SECONDS);
        } else {  // 不是第一次
            // 每次调用+1
            log.info("=======================>不是第一次调用");
            read = Integer.parseInt(readCount) + 1;
            stringRedisTemplate.opsForValue().set(readKey, String.valueOf(read), 3600, TimeUnit.SECONDS);
        }
        return read;    //返回调用次数   (如果返回-1就代表没有调用过)
    }

    @Override
    public Boolean getUserCount(Integer uid) {
        // 根据用户id生成key
        String readKey = "READ_" + uid;
        // 根据当前key获取用户的调用次数
        String readCount = stringRedisTemplate.opsForValue().get(readKey);
        if (readCount == null) {    // 基本不会出现
            //为空直接抛弃说明key出现异常
            log.error("该用户没有访问申请验证值记录,疑似异常!");
            return true;
        }
        return Integer.parseInt(readCount) > 10;  //大于10代表超过：true超过 false 没超过
    }
}
