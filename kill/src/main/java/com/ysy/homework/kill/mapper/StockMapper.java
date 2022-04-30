package com.ysy.homework.kill.mapper;

import com.ysy.homework.kill.pojo.Stock;
import org.apache.ibatis.annotations.Mapper;

/**
 * @anthor silenceYin
 * @date 2022/4/30 - 17:34
 */
@Mapper
public interface StockMapper {

    /**
     * 根据商品id查询库存数量
     */
    Stock checkStock(Integer id);

    /**
     * 根据商品id扣除库存
     */
    Integer updateSale(Stock stock);
}
