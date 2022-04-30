package com.ysy.homework.kill.mapper;

import com.ysy.homework.kill.pojo.Order;
import org.apache.ibatis.annotations.Mapper;

/**
 * @anthor silenceYin
 * @date 2022/4/30 - 17:34
 */
@Mapper
public interface OrderMapper {
    /**
     * 创建订单
     */
    void createOrder(Order order);
}
