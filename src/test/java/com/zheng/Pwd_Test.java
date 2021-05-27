package com.zheng;

import com.zheng.util.EncryptUtil;
import org.junit.jupiter.api.Test;

/**
 * @author: ZhengTianLiang
 * @date: 2021/5/19  18:11
 * @desc: 密码的测试类
 */

public class Pwd_Test {

    /**
     * @author: ZhengTianLiang
     * @date: 2021/5/19  18:11
     * @desc: 测试加密工具类能否正常的使用
     */
    @Test
    public void test01(){
        String key = EncryptUtil.createAESKey();
        System.out.println(key);
        String pass = "lianggeniubi";
        System.out.println(pass);
        String aesenc = EncryptUtil.aesenc(key, pass);
        System.out.println("密文是："+aesenc);
        System.out.println("明文是："+EncryptUtil.aesdec(key,aesenc));
    }

}
