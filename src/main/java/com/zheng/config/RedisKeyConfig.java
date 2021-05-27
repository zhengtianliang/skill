package com.zheng.config;

/**
 * @author: ZhengTianLiang
 * @date: 2021/5/20  17:45
 * @desc: redis的key的配置类
 */

public class RedisKeyConfig {

    /**
     * 记录登陆的令牌，     校验令牌用的
     */
    public static final String TOKEN_USER = "skill_token_";

    /**
     * 记录登陆的账号      实现唯一登陆用的
     */
    public static final String USER_TOKEN = "skill_user_";

    /**
     * 令牌有效期  1小时
     */
    public static final int TOKEN_HOURS = 1;

    /**
     * 记录被挤掉的令牌     后面记录令牌，set
     */
    public static final String TOKEN_SWAP = "skill_token_swap";

    /**
     * 记录错误的次数，5分钟内的错误次数.   String   手机号：时间戳   值    有效期5分钟
     */
    public static final String USER_PASSFAIL = "skill_userfail_";

    /**
     * 错误的有效期   5分钟
     */
    public static final int TOKEN_FILE = 5;

    /**
     * 记录冻结账号   String   后面跟手机号    有效期  30分钟
     */
    public static final String USER_FREEZE = "skill_userfreeze";

    /**
     * 记录冻结账号的过期时间   30分钟
     */
    public static final int USER_FREEZE_TIME = 30;

    /**
     * 记录秒杀商品的库存    记录秒杀商品的信息和库存，有效期：永久有效
     *      Hash    k：秒杀商品的id       v：库存量
     */
    public static final String SKILL_GOODS="skill_goodskc_";

    /**
     * 记录秒杀订单   存redis，用hash类型
     */
    public static final String SKILL_ORDER = "skill_order";

}
