package com.zheng.task;

import com.zheng.config.RedisKeyConfig;
import com.zheng.util.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author: ZhengTianLiang
 * @date: 2021/05/31  21:13
 * @desc: 定时任务的task
 */

@Component
public class LimitTokenTask {

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 雪花算法的id生成器
     */
    private IdGenerator idGenerator = new IdGenerator();

    /**
     * 默认桶的容量
     */
    private Integer maxCount = 1000;

    /**
     * 生成令牌的频率，秒级
     */
    private int secoundCount = 100;

    /**
     * 定时任务，每隔多久执行一次这个任务
     */
    @Scheduled(cron = "* * * * * ?")
    public void createLimitToken() {
        for (int i = 0; i < secoundCount; i++) {
            // 验证令牌桶是否已经达到了上限
            if (redisTemplate.opsForList().size(RedisKeyConfig.LIMIT_BUCKET) < maxCount) {
                redisTemplate.opsForList().leftPush(RedisKeyConfig.LIMIT_BUCKET, idGenerator.nextId() + "");
            }

        }
    }

}
