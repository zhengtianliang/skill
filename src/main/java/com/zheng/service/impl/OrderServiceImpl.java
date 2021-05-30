package com.zheng.service.impl;

import com.alibaba.fastjson.JSON;
import com.zheng.config.RedisKeyConfig;
import com.zheng.dao.OrderDao;
import com.zheng.dao.SkillGoodsDao;
import com.zheng.dto.SkillGoodsDTO;
import com.zheng.entity.Order;
import com.zheng.entity.SkillGoods;
import com.zheng.exception.OrderException;
import com.zheng.service.OrderService;
import com.zheng.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: ZhengTianLiang
 * @date: 2021/5/17  10:35
 * @desc: 订单接口的serviceImpl
 */

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private SkillGoodsDao skillGoodsDao;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Value("${skill.maxcount}")
    private Integer skillMaxCount;

    /**
     * 辅助redis实现预减库存，记录已售罄的商品       泛型是 商品id，是否售罄
     */
    private ConcurrentHashMap<Integer, Boolean> map = new ConcurrentHashMap<>();

    /**
     * @author: ZhengTianLiang
     * @date: 2021/5/17  10:36
     * @desc: 订单接口的新增       (秒杀1.0)
     */
    @Override
    public R saveV1(SkillGoodsDTO skillGoodsDTO) throws OrderException {
        // 策略1：每个用户只能成功秒杀一次(不成功的话，可以继续参与秒杀)
        // 策略2：每个用户只能参与一次(不管成不成功，都不能参与第二次)

        // 1、判断秒杀的数量，有没有超过秒杀的限购上限
        if (skillGoodsDTO.getCount() > skillMaxCount) {
            // 要注意，此处应该有错误信息的记录
            return R.fail("秒杀的商品数量，超过了限购数量");
        } else {
            // 2、查询数据库的 秒杀商品的信息，校验秒杀商品是否存在，防止恶意攻击
            SkillGoods skillGoods = skillGoodsDao.selectById(skillGoodsDTO.getGid());
            if (skillGoods != null) {
                // 3、秒杀活动时间的比较
                Date current = new Date();
                if (current.getTime() < skillGoods.getStartTime().getTime()) {
                    return R.fail("秒杀还未开始");
                } else if (current.getTime() < skillGoods.getEndTime().getTime()) {
                    return R.fail("秒杀已经结束了");
                } else { // 秒杀活动正在进行中
                    // 4、判断用户是否秒杀过，若秒杀过，则不允许第二次秒杀
                    Order order = orderDao.selectByUIdAndGId(0, skillGoodsDTO.getGid());
                    if (order == null) {
                        // 5、校验库存是否足够，剩余的库存量大于要购买的量
                        if (skillGoods.getSeckillStock() >= skillGoodsDTO.getCount()) {
                            // 6、生成订单
                            Order addOrder = new Order();
                            if (orderDao.insertOrder(addOrder) > 0) {
                                // 7、减库存。a、下单立减库存 b、付款立减库存 c、预减库存
                                Integer modifyCount = skillGoodsDao.updateSkillGoods(skillGoods.getSeckillId(),
                                        skillGoods.getSeckillStock() - skillGoodsDTO.getCount());
                                if (modifyCount > 0) { // 修改库存成功
                                    // 此时真正的完成了秒杀的步骤
                                    return R.ok(addOrder);
                                } else {
                                    throw new OrderException("扣减库存失败，请联系管理员");
                                }
                            } else {
                                return R.fail("亲，网络不行，请刷新试试");
                            }
                        } else {
                            return R.fail("亲，秒杀商品已经告罄");
                        }
                    } else {
                        return R.fail("亲，你已经秒杀过了，每人只能秒杀一次哦");
                    }

                }
            } else {
                // 像这种恶意的攻击的，可以加黑名单或者限流
                return R.fail("要秒杀的商品不存在！");
            }

        }

    }

    /**
     * @author: ZhengTianLiang
     * @date: 2021/5/25  10:05
     * @desc: 订单的保存接口   (秒杀2.0，从数据库，改进成  redis)
     */
    @Override
    public R saveV2(SkillGoodsDTO skillGoodsDTO) throws OrderException {
        // 策略1：每个用户只能成功秒杀一次(不成功的话，可以继续参与秒杀)
        // 策略2：每个用户只能参与一次(不管成不成功，都不能参与第二次)

        // 这个用户id，是从token中解析出来的
        int uid = 1;

        // 1、判断秒杀的数量，有没有超过秒杀的限购上限
        if (skillGoodsDTO.getCount() > skillMaxCount) {
            // 要注意，此处应该有错误信息的记录
            return R.fail("秒杀的商品数量，超过了限购数量");
        } else {
            // 2、查询数据库的 秒杀商品的信息，校验秒杀商品是否存在，防止恶意攻击
            if (stringRedisTemplate.opsForHash().hasKey(RedisKeyConfig.SKILL_GOODS, skillGoodsDTO.getGid())) {
                // 3、校验库存是否足够，剩余的库存量大于要购买的量     内存标记 (这个商品是否已售罄)
                if (map.contains(skillGoodsDTO.getGid())) { // map中有这个商品的key，则说明这个商品已经售罄了。
                    return R.fail("商品已售罄!");
                } else {
                    int redisCount = (int) stringRedisTemplate.opsForHash().get(RedisKeyConfig.SKILL_GOODS, skillGoodsDTO.getGid());
                    // 校验库存是否充足(因为可以选择购买的数量)
                    if (redisCount >= skillGoodsDTO.getCount()) {
                        // 4、校验用户是否购买过此秒杀商品
                        if (!stringRedisTemplate.opsForHash().hasKey(RedisKeyConfig.SKILL_ORDER,
                                skillGoodsDTO.getGid() + "_" + uid)) {
                            // 5、生成订单
                            Order addOrder = new Order();
                            addOrder.setOid((int) System.currentTimeMillis() / 1000);
                            addOrder.setStatus(1);
                            addOrder.setSgid(skillGoodsDTO.getGid());
                            addOrder.setUid(uid);
                            // 将生成的订单放到redis中
                            stringRedisTemplate.opsForHash().put(RedisKeyConfig.SKILL_ORDER,
                                    skillGoodsDTO.getGid() + "_" + uid, JSON.toJSONString(addOrder));

                            // 新增mysql中的订单数据，消息机制，异步操作 mysql中订单生效(用到了rabbitmq，上面只是创建了订单对象，但是新增到了redis中，没存入mysql中)

                            // 在redis中进行商品库存的预减
                            stringRedisTemplate.opsForHash().put(RedisKeyConfig.SKILL_GOODS, skillGoodsDTO.getGid(),
                                    redisCount - skillGoodsDTO.getCount());
                            // 往map中插入数据，代表这个商品已经卖完了
                            if (redisCount - skillGoodsDTO.getCount() == 0){
                                map.put(skillGoodsDTO.getGid(),true);
                            }
                            return R.ok(addOrder);
                        } else {
                            return R.fail("亲，秒杀商品库存不足(您要买3件，只剩下2件的那种情况)");
                        }
                    } else {
                        return R.fail("亲，你已经秒杀过了，每人只能秒杀一次哦");
                    }
                }

            } else {
                // 像这种恶意的攻击的，可以加黑名单或者限流
                return R.fail("要秒杀的商品不存在！");
            }

        }

    }


}
