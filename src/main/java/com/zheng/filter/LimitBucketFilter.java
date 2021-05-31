package com.zheng.filter;

import com.alibaba.fastjson.JSON;
import com.zheng.config.RedisKeyConfig;
import com.zheng.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: ZhengTianLiang
 * @date: 2021/05/30  21:55
 * @desc: 限流算法拦截器,实现javax.servlet.Filter (若是微服务项目的话，则应继承ZuulFilter)
 *      令牌桶算法：安装一定的频率去生成令牌，请求去令牌桶(容量池)中获取令牌，令牌桶有上限(qps最大并发容量)
 */
@Component
public class LimitBucketFilter implements Filter {

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 默认桶的容量(到达这个容量，就不在生产令牌了)
     */
    private Integer maxCount = 1000;

    /**
     * 生产令牌的频率   秒级
     */
    private int secountCount = 100;


    /**
     * @author: ZhengTianLiang
     * @date: 2021/05/30  21:55
     * @desc: 限流拦截操作(校验能否拿到token，假设秒杀有100件，10万人来抢，一共就生成1000个token，让这一千人抢1百)
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        // 只对一部分接口进行限流操作(只对其中一部分并发量比较高的接口进行限流操作)
        if (request.getQueryString().endsWith("/api/order/skillsave.do")){
            // 进行限流
            // 能取到令牌，则说明有，可以继续执行，令牌一旦取出，就要删除
            Long i = Long.valueOf(redisTemplate.opsForList().leftPop(RedisKeyConfig.LIMIT_BUCKET));
            if (i != null){ // 能取到token，放行
                filterChain.doFilter(request,servletResponse);
            }else { // 取不到，就拦截住
                response.setContentType("application/json;charset=UTF-8");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().println(JSON.toJSONString(R.fail("亲，网络阻塞，请等待!")));
            }

        }
    }
}
