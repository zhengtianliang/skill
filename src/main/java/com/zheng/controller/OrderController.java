package com.zheng.controller;

import com.zheng.dto.SkillGoodsDTO;
import com.zheng.exception.OrderException;
import com.zheng.service.OrderService;
import com.zheng.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: ZhengTianLiang
 * @date: 2021/05/30  22:40
 * @desc: 订单接口控制器
 */

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * @author: ZhengTianLiang
     * @date: 2021/05/30  22:40
     * @desc: 生成订单接口第一版本
     */
    @PostMapping(value = "/api/order/skillsave.do")
    public R saveV1(@RequestBody SkillGoodsDTO skillGoodsDTO) throws OrderException {
        return orderService.saveV1(skillGoodsDTO);
    }

    /**
     * @author: ZhengTianLiang
     * @date: 2021/05/31  21:52
     * @desc: 生成订单接口第二版本
     */
    @PostMapping(value = "/api/order/v2/skillsave.do")
    public R saveV2(@RequestBody SkillGoodsDTO skillGoodsDTO) throws OrderException {
        return orderService.saveV2(skillGoodsDTO);
    }


}
