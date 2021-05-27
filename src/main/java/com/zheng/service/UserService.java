package com.zheng.service;

import com.zheng.dto.LoginDTO;
import com.zheng.vo.R;

/**
 * @author: ZhengTianLiang
 * @date: 2021/5/19  17:42
 * @desc: 用户的service
 */

public interface UserService {

    /**
     * @author: ZhengTianLiang
     * @date: 2021/5/19  17:42
     * @desc: 用户的登陆接口
     */
    R login(LoginDTO loginDTO);

    /**
     * @author: ZhengTianLiang
     * @date: 2021/5/24  21:54
     * @desc: 校验令牌的有效性
     */
    R checkToken(String token);

}
