package com.zheng;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author: ZhengTianLiang
 * @date: 2021/5/17  9:25
 * @desc: 秒杀项目启动类
 */

@SpringBootApplication
@MapperScan(value = "com.zheng.dao")
public class SkillApplication {

    public static void main(String[] args) {
        SpringApplication.run(SkillApplication.class, args);
    }

}
