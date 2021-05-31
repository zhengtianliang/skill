package com.zheng.config;

import com.zheng.filter.LimitBucketFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: ZhengTianLiang
 * @date: 2021/05/31  21:35
 * @desc: 注册过滤器
 */

@Configuration
public class FilterConfig {

    /**
     * @author: ZhengTianLiang
     * @date: 2021/05/31  21:35
     * @desc: 注册过滤器,可以使用@WebFilter、也可以用下面这种方式
     */
    @Bean
    public FilterRegistrationBean addFilter(LimitBucketFilter filter){
        FilterRegistrationBean bean = new FilterRegistrationBean();
        // 要拦截的路径
        bean.addUrlPatterns("/*");
        bean.setFilter(filter);
        return bean;
    }

}
