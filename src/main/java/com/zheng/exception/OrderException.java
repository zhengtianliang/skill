package com.zheng.exception;

/**
 * @author: ZhengTianLiang
 * @date: 2021/5/17  15:11
 * @desc: 自定义订单异常
 */

public class OrderException extends Exception {

    /**
     * @author: ZhengTianLiang
     * @date: 2021/5/17  15:11
     * @desc: 创建带参数的异常
     */
    public OrderException(String msg) {
        super(msg);
    }

    /**
     * @author: ZhengTianLiang
     * @date: 2021/5/17  15:12
     * @desc: 创建不带参数的异常
     */
    public OrderException() {
        super();
    }

}
