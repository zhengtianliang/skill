package com.zheng;

import com.zheng.util.JwtUtil;
import org.junit.jupiter.api.Test;

/**
 * @author: ZhengTianLiang
 * @date: 2021/5/19  17:34
 * @desc: 测试jwt的测试类
 */

public class Jwt_Test {

    /**
     * @author: ZhengTianLiang
     * @date: 2021/5/19  17:34
     * @desc: 测试jwt的生成和解析
     */
    @Test
    public void testJwt(){
        String token = JwtUtil.createJwtToken("Hello JWT,Hello ZhengTianLiang");
        System.out.println(token);
        System.out.println(JwtUtil.parseJwtToken(token));
    }
}
