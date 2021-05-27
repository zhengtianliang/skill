package com.zheng.service;

import com.zheng.dto.SkillGoodsDTO;
import com.zheng.exception.OrderException;
import com.zheng.vo.R;

/**
 * @author: ZhengTianLiang
 * @date: 2021/5/17  10:34
 * @desc: 订单service
 */

public interface OrderService {

    /**
     * @author: ZhengTianLiang
     * @date: 2021/5/17  10:34
     * @desc: 订单的保存接口   (秒杀1.0)
     */
    R saveV1(SkillGoodsDTO skillGoodsDTO) throws OrderException;

    /**
     * @author: ZhengTianLiang
     * @date: 2021/5/25  10:03
     * @desc: 订单的保存接口   (秒杀2.0)
     */
    R saveV2(SkillGoodsDTO skillGoodsDTO) throws OrderException;
}
