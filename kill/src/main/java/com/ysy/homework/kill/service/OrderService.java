package com.ysy.homework.kill.service;

/**
 * @anthor silenceYin
 * @date 2022/4/30 - 17:27
 */
public interface OrderService {

    /**
     * 根据商品ID与用户ID生成MD5
     * @param id
     * @param uid
     * @return
     */
    String getMD5(Integer id, Integer uid);

    /**
     * 用来处理秒杀下单方法 返回订单id 加入md5签名 （接口隐藏）
     * @param id
     * @param uid
     * @param md5
     * @return
     */
    Integer killMD5(Integer id, Integer uid, String md5);
}
