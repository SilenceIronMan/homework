package com.ysy.homework.kill.controller;

import com.google.common.util.concurrent.RateLimiter;
import com.ysy.homework.kill.service.OrderService;
import com.ysy.homework.kill.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @anthor silenceYin
 * @date 2022/4/30 - 17:25
 */
@RestController
@RequestMapping("/stock")
@Slf4j
@RequiredArgsConstructor
public class StockController {

    private final OrderService orderService;

    private final UserService userService;

    /**
     * 创建令牌桶实例 qps 20
     */
    private RateLimiter rateLimiter = RateLimiter.create(20);

    /**
     * 乐观锁 + 令牌桶算法限流 + MD5签名 + 单用户访问频率限制
     * @param id
     * @param uid
     * @param md5
     * @return
     */
    @GetMapping("/killtokenMD5limit/{id}/{uid}/{md5}")
    public String killtokenMD5limit(@PathVariable("id") Integer id,
                                    @PathVariable("uid")Integer uid,
                                    @PathVariable("md5")String md5){
        // 加入令牌桶的限流措施
        if(!rateLimiter.tryAcquire(1, TimeUnit.MICROSECONDS)){
            log.info("抢购失败,当前秒杀活动过于火爆,请重试!");
            return "抢购失败,当前秒杀活动过于火爆,请重试!";
        }
        try {   //2秒内能拿到产品才能进入
            //单用户调用接口频率限制
            Integer readCount = userService.addUserReadCount(uid);
            log.info("===>当前该用户"+uid+"访问次数："+readCount);
            Boolean isBannd = userService.getUserCount(uid);
            if (isBannd){
                log.info("购买失败,您当前超过了频率限制!");
                return "购买失败,您当前超过了频率限制!";
            }
            //根据秒杀商品id调用秒杀业务
            Integer orderId = orderService.killMD5(id,uid,md5);
            return "秒杀成功,订单ID为："+ orderId;
        }catch (Exception e){
            e.printStackTrace();
            return e.getMessage();
        }
    }
    /**
     * 生成MD5
     * @param id
     * @param uid
     * @return
     */
    @GetMapping("/md5/{id}/{uid}")
    public String getMD5(@PathVariable("id")Integer id,@PathVariable("uid") Integer uid){
        String md5;
        try {
            md5 = orderService.getMD5(id,uid);
        }catch (Exception e){
            e.printStackTrace();
            return "获取MD5失败："+e.getMessage();
        }
        return "获取到的MD5信息为："+md5;
    }

}
