package com.ysy.homework.kill.service.impl;

import com.ysy.homework.kill.mapper.OrderMapper;
import com.ysy.homework.kill.mapper.StockMapper;
import com.ysy.homework.kill.mapper.UserMapper;
import com.ysy.homework.kill.pojo.Order;
import com.ysy.homework.kill.pojo.Stock;
import com.ysy.homework.kill.pojo.User;
import com.ysy.homework.kill.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @anthor silenceYin
 * @date 2022/4/30 - 17:28
 */
@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final StockMapper stockMapper;

    private final OrderMapper orderMapper;

    private final UserMapper userMapper;

    private final StringRedisTemplate stringRedisTemplate;

    @Value("${md5.salt}")
    private String salt;

    /**
     * 根据商品ID与用户ID生成MD5
     *
     * @param id
     * @param uid
     * @return
     */
    @Override
    public String getMD5(Integer id, Integer uid) {
        // 验证uid 用户是否存在
        User user = userMapper.findUserById(uid);
        if (null == user) throw new RuntimeException("用户信息不存在!");
        // 验证id 商品是否存在
        Stock stock = stockMapper.checkStock(id);
        if (null == stock) throw new RuntimeException("商品信息不存在!");
        // 生成MD5存入Redis
        String hashKey = "KEY_" + uid + "_" + id;
        // 生成MD5 盐
        String key = DigestUtils.md5DigestAsHex((uid + id + salt).getBytes());
        // 存入redis key value 时间
        stringRedisTemplate.opsForValue().set(hashKey, key, 120, TimeUnit.SECONDS);
        return key;
    }

    /**
     * 用来处理秒杀下单方法 返回订单id 加入md5签名 （接口隐藏）
     *
     * @param id
     * @param uid
     * @param md5
     * @return
     */
    @Override
    public Integer killMD5(Integer id, Integer uid, String md5) {
        // 验证redis中的秒杀商品是否超时
        if (Boolean.FALSE.equals(stringRedisTemplate.hasKey("kill" + id))) { //当redis不存在秒杀的商品    当我们设置的限时字段过期了就不存在了，不存在就代表秒杀结束了！
            throw new RuntimeException("当前商品的抢购活动已经结束了！");
        }
        // 验证签名
        String hashKey = "KEY_" + uid + "_" + id;
        // 通过redis
        String md5DB = stringRedisTemplate.opsForValue().get(hashKey);
        if (null == md5DB) {
            throw new RuntimeException("没有携带签名,请求不合法!");
        }
        if (!md5DB.equals(md5)) { //请求的md5与数据库中的md5作比较
            throw new RuntimeException("当前请求数据不合法,请稍后再试!");
        }

        // 校验库存
        Stock stock = checkStock(id);
        // 存在扣住库存 未抛出异常则满足！
        updateSale(stock);
        // 创建订单
        return createOrder(stock);
    }

    /**
     * 校验库存
     *
     * @param id
     * @return
     */
    public Stock checkStock(Integer id) {
        //根据商品id校验库存是否还存在
        Stock stock = stockMapper.checkStock(id);
        //当已售和库存相等就库存不足了
        if (stock.getSale().equals(stock.getCount())) {
            throw new RuntimeException("库存不足!");
        }
        return stock;  //满足情况下返回商品信息
    }

    /**
     * 扣除库存
     *
     * @param stock
     */
    public void updateSale(Stock stock) {
        //在sql层面完成销量+1 和 版本号 +1 并且根据商品id和版本号同时查询更新的商品
        Integer updRows = stockMapper.updateSale(stock);   //更新信息
        if (updRows == 0) {   //代表没有拿到版本号
            throw new RuntimeException("抢购失败,请重试！");
        }
    }

    /**
     * 创建订单
     *
     * @param stock
     * @return
     */
    public Integer createOrder(Stock stock) {
        //创建订单
        Order order = new Order();
        order.setSid(stock.getId()).setName(stock.getName()).setCreateDate(new Date());
        orderMapper.createOrder(order); //创建订单
        return order.getId();   //mybatis主键生成策略 直接返回创建的id
    }
}
