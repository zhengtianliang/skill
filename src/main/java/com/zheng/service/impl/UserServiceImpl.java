package com.zheng.service.impl;

import com.alibaba.fastjson.JSON;
import com.zheng.config.RedisKeyConfig;
import com.zheng.dao.UserDao;
import com.zheng.dto.LoginDTO;
import com.zheng.dto.TokenDTO;
import com.zheng.entity.User;
import com.zheng.service.UserService;
import com.zheng.util.EncryptUtil;
import com.zheng.util.JwtUtil;
import com.zheng.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author: ZhengTianLiang
 * @date: 2021/5/19  17:46
 * @desc: 用户的serviceImpl
 */

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RedisTemplate redisTemplate;

    @Value("${skill.aesKey}")
    private String aesKey;

    /**
     * @author: ZhengTianLiang
     * @date: 2021/5/19  17:46
     * @desc: 用户登陆(返回jwt)
     */
    @Override
    public R login(LoginDTO loginDTO) {
        // 1、校验账户是否被冻结
        if (redisTemplate.hasKey(RedisKeyConfig.USER_FREEZE + loginDTO.getPhone())) {
            // 该账户已经被冻结
            return R.fail("亲，账号已经被冻结，剩余解封时间：" +
                    redisTemplate.getExpire(RedisKeyConfig.USER_FREEZE + loginDTO.getPhone()) + "秒");
        } else {
            // 2、检查账户密码
            User user = userDao.selectByPhone(loginDTO.getPhone());
            if (user != null) {
                // 3、校验密码，注意密文
                if (user.getPassword().equals(EncryptUtil.aesenc(aesKey, loginDTO.getPwd()))) { // 账户密码正确
                    // 4、登陆成功后，校验是否登陆过，若是登陆过，那么本次就将上次登陆(原先的令牌)干掉
                    if (redisTemplate.hasKey(RedisKeyConfig.USER_TOKEN + loginDTO.getPhone())) {
                        // 能进入，则说明该账户之前登陆过
                        Object o = redisTemplate.opsForValue().get(RedisKeyConfig.USER_TOKEN + loginDTO.getPhone());
                        // 记录一下被挤掉的信息，到redis中，此次是用set来记录的
                        redisTemplate.opsForSet().add(RedisKeyConfig.TOKEN_SWAP, o);
                        // 同事删除掉原先的令牌
                        redisTemplate.delete(RedisKeyConfig.USER_TOKEN + loginDTO.getPhone());
                    }
                    // 5、生成token令牌
                    TokenDTO tokenDTO = new TokenDTO(loginDTO.getPhone(), new Date());
                    String token = JwtUtil.createJwtToken(JSON.toJSONString(tokenDTO));
                    // 6、记录令牌,对应的账户，做唯一登陆
                    redisTemplate.opsForValue().set(RedisKeyConfig.TOKEN_USER + token,
                            JSON.toJSONString(user), RedisKeyConfig.TOKEN_HOURS, TimeUnit.HOURS);
                    // 记录账户对应的令牌
                    redisTemplate.opsForValue().set(RedisKeyConfig.USER_TOKEN + loginDTO.getPhone(), token,
                            RedisKeyConfig.TOKEN_HOURS, TimeUnit.HOURS);
                    // 7、令牌返回
                    return R.ok(token);
                } else {
                    int r = 0;
                    // 8、密码错误  5分钟内，密码错误3次以上，冻结30分钟
                    Set keys = redisTemplate.keys(RedisKeyConfig.USER_PASSFAIL + loginDTO.getPhone());
                    if (keys != null && keys.size() > 1) {
                        r = keys.size();
                        // 需要冻结，之前错误至少两次(size>1)，加上这次，错误三次了，
                        redisTemplate.opsForValue().set(RedisKeyConfig.USER_FREEZE + loginDTO.getPhone(),
                                System.currentTimeMillis() / 1000 + "",
                                RedisKeyConfig.USER_FREEZE_TIME, TimeUnit.MINUTES);
                    }
                    // 9、记录本次的失败
                    redisTemplate.opsForValue().set(RedisKeyConfig.USER_FREEZE + loginDTO.getPhone()
                            + ":" + System.currentTimeMillis(), "", RedisKeyConfig.TOKEN_FILE, TimeUnit.MINUTES);
                    r += 1;
                    return R.fail("亲，您已经失败了" + r + "次，小心封号哦");
                }

            } else {
                // 上面的else是密码对不上，这个是账号查不到。
                return R.fail("账号或密码不正确");
            }
        }
    }

    /**
     * @author: ZhengTianLiang
     * @date: 2021/5/24  21:56
     * @desc: 校验令牌的有效性
     */
    @Override
    public R checkToken(String token) {
        if (redisTemplate.hasKey(RedisKeyConfig.TOKEN_SWAP+token)){
            return R.fail("亲，你的账号已经在其他设备上登陆了");
        }else {
            if (redisTemplate.hasKey(RedisKeyConfig.TOKEN_USER+token)){
                return R.ok();
            }else {
                return R.fail("亲，登陆信息已失效，请重新登陆");
            }
        }

    }

}
