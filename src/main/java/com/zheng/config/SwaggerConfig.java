package com.zheng.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author: ZhengTianLiang
 * @date: 2021/5/17  9:41
 * @desc: swagger的配置类
 */

@Configuration
public class SwaggerConfig {

    /**
     * @author: ZhengTianLiang
     * @date: 2021/5/17  9:42
     * @desc: 创建文档说明
     */
    public ApiInfo createApiInfo(){
        ApiInfo apiInfo = new ApiInfoBuilder().title("基于SpringBoot实现的秒杀项目数据接口")
                .description("基于SpringBoot实现的秒杀一套后端服务")
                .contact(new Contact("zheng","http://localhost:chounimei","123456789@123.com"))
                .build();
        return apiInfo;
    }


    /**
     * @author: ZhengTianLiang
     * @date: 2021/5/17  9:42
     * @desc: 创建swagger扫描信息
     */
    @Bean
    public Docket createDocket(){
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(createApiInfo())
                .select().apis(RequestHandlerSelectors.basePackage("com.zheng.controller")).build();
    }

}
