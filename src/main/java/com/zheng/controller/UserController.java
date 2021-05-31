package com.zheng.controller;

import com.zheng.dto.LoginDTO;
import com.zheng.service.UserService;
import com.zheng.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: ZhengTianLiang
 * @date: 2021/05/31  21:45
 * @desc: 用户controller
 */

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * @author: ZhengTianLiang
     * @date: 2021/05/31  21:45
     * @desc: 登录
     */
    @PostMapping(value = "/api/user/login.do")
    public R login(@RequestBody LoginDTO loginDTO){
        return userService.login(loginDTO);
    }

    /**
     * @author: ZhengTianLiang
     * @date: 2021/05/31  21:47
     * @desc: 校验token的有效性
     */
    @GetMapping(value = "/api/user/checktoken.do")
    public R checkToken(String token){
        return userService.checkToken(token);
    }

    /**
     * @author: ZhengTianLiang
     * @date: 2021/05/31  21:48
     * @desc: 用户注册
     */
    @PostMapping(value = "/api/user/register.do")
    public R register(@RequestBody LoginDTO loginDTO){
//        return userService.register(loginDTO);
        return null;
    }

    /**
     * @author: ZhengTianLiang
     * @date: 2021/05/31  21:50
     * @desc: 校验手机号是否注册
     */
    @PostMapping(value = "/api/user/checkphone.do")
    public R checkphone(String phone){
//        return userService.checkphone(phone);
        return null;
    }

}
