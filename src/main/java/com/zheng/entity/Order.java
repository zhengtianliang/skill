package com.zheng.entity;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author: ZhengTianLiang
 * @date: 2021/5/17  9:25
 * @desc: 订单表实体类
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "订单表实体类")
public class Order {

    /**
     * 订单id
     */
    private Integer oid;

    /**
     * 秒杀商品id
     */
    private int sgid;

    /**
     * 商品价格
     */
    private Double tprice;

    /**
     * 创建时间
     */
    private Date ctime;

    /**
     * 订单状态:1 未支付 2支付成功,未发货 3.已发货，待收货
     *          4.收货完成，待确认 5 确认收货，未评价 6 订单完成,已评价
     *          7.订单超时 8.取消订单(未付款) 9.退款 10.退货
     */
    private int status;

    /**
     * 用户id
     */
    private int uid;

    /**
     * 订单类型 1：普通 2：秒杀
     */
    private int type;
}
