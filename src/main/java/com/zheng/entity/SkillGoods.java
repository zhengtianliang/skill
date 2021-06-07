package com.zheng.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author: ZhengTianLiang
 * @date: 2021/5/17  9:26
 * @desc: 秒杀商品实体类
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "秒杀商品实体类")
public class SkillGoods {

    /**
     * 秒杀商品id
     */
    @ApiModelProperty(value = "秒杀商品id")
    private Integer seckillId;

    /**
     * 秒杀开始时间
     */
    @ApiModelProperty(value = "秒杀开始时间")
    private Date startTime;

    /**
     * 秒杀结束时间
     */
    @ApiModelProperty(value = "秒杀结束时间")
    private Date endTime;

    /**
     * 秒杀商品的库存量
     */
    @ApiModelProperty(value = "秒杀商品的库存量")
    private Integer seckillStock;

    /**
     * 价格
     */
    private Double price;

}
