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

    /**
     * @author: ZhengTianLiang
     * @date: 2021/6/7  21:55
     * @desc: 根据订单id查询订单详情(主要是看订单的状态)
     */
    Order selectById(long oid);

    /**
     * @author: ZhengTianLiang
     * @date: 2021/6/7  21:56
     * @desc: 更新订单状态
     */
    int update(@Param("oid")long id,@Param("status")int status);

}
