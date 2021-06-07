package com.zheng.listener;

import com.zheng.dao.OrderDao;
import com.zheng.dao.SkillGoodsDao;
import com.zheng.entity.Order;
import com.zheng.entity.SkillGoods;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author: ZhengTianLiang
 * @date: 2021/06/06  20:00
 * @desc: 订单消息监听器
 */

@Component
@RabbitListener(queues = "skill.queue.order")
public class OrderListener {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private SkillGoodsDao skillGoodsDao;

    /**
     * @author: ZhengTianLiang
     * @date: 2021/06/06  20:01
     * @desc: 由于消息发送方发送的是map，所以接收方也得是map。也可以用string的json来传
     */
    @RabbitHandler
    public void handler(Map<String, Object> map) {
        Order order = (Order) map.get("order");
        int count = (int) map.get("count");
        if (order != null) {
            // 查询商品的秒杀价格
            SkillGoods goods = skillGoodsDao.selectById(order.getSgid());
            order.setTprice(goods.getPrice() * count);
            orderDao.insertOrder(order);
        }
    }

}
