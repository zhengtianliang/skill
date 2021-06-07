package com.zheng.listener;

import com.zheng.config.RedisKeyConfig;
import com.zheng.dao.OrderDao;
import com.zheng.entity.Order;
import com.zheng.service.impl.OrderServiceImpl;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author: ZhengTianLiang
 * @date: 2021/06/07  22:11
 * @desc: 死信队列的消息监听者
 */

@Component
@RabbitListener(queues = "skill.queue.dlxorder")
public class DlxOrderListener {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 注入这个，是为了获取到内存标记的对象，用来修改内存标记用的
     */
    @Autowired
    private OrderServiceImpl orderService;

    @RabbitHandler
    public void handler(Map<String, Object> map) {
        // 获取订单
        Order order = (Order) map.get("order");
        int count = (int) map.get("count");
        if (order != null) {
            // 查询数据库对应的订单的状态，是已支付还是未支付
            Order order1 = orderDao.selectById(order.getOid());
            // 1、则是代表还未支付，已经超时了(能进入到这个监听器，一定是过了15分钟，进了死信了，
            // 死信消息根据路由键将消息发到这个队列中，才被监听到的)
            if ("1".equals(order1.getStatus())) {
                // 修改订单状态
                int update = orderDao.update(order1.getOid(), 7);
                // 将redis中已经预减过的库存，还原回去，先查出来现在的数量
                int c = (int) redisTemplate.opsForHash().get(RedisKeyConfig.SKILL_GOODS, order.getSgid());
                // 用现在的数量，加上之间减去的数量
                redisTemplate.opsForHash().put(RedisKeyConfig.SKILL_GOODS, order.getSgid(), c + count);
                // 若是内存标记是“售罄”，则更改内存标记，(因为刚刚将预减的又加上的，所以不在是售罄的状态了)
                if (orderService.getMap().containsKey(order.getSgid())){ // 因为是售罄的商品，商品id才会在这个map中，然后现在有库存了，所以需要取掉这个商品
                    orderService.getMap().remove(order.getSgid());
                }

            }
        }

    }
}
