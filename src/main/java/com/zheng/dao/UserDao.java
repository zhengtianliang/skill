package com.zheng.dao;

import com.zheng.entity.User;

/**
 * @author: ZhengTianLiang
 * @date: 2021/5/17  9:39
 * @desc: 用户dao
 */

public interface UserDao {

    /**
     * @author: ZhengTianLiang
     * @date: 2021/5/17  9:39
     * @desc: 用户dao
     */
    User selectByPhone(String phone);
}
