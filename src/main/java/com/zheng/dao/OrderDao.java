package com.zheng.dao;

import com.zheng.entity.Order;
import org.apache.ibatis.annotations.Param;

/**
 * @author: ZhengTianLiang
 * @date: 2021/5/17  9:38
 * @desc: 订单表dao
 */

public interface OrderDao {

    /**
     * @author: ZhengTianLiang
     * @date: 2021/5/17  9:38
     * @desc: 根据用户id、商品id，查询是否有订单数据(是为了判断用户是否秒杀过，若秒杀过则不允许继续秒杀)
     */
    Order selectByUIdAndGId(@Param("uid") Integer userId, @Param("gid") Integer goodsId);

    /**
     * @author: ZhengTianLiang
     * @date: 2021/5/17  14:46
     * @desc: 下单(生成订单)
     */
    Integer insertOrder(Order order);

}
