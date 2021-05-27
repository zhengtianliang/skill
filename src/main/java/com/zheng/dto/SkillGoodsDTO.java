package com.zheng.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: ZhengTianLiang
 * @date: 2021/5/17  10:30
 * @desc: 秒杀商品的请求dto(创建订单的请求dto)
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "秒杀商品的请求dto(创建订单的请求dto)")
public class SkillGoodsDTO {

    /**
     * token，内部含有用户信息
     */
    @ApiModelProperty(value = "token，内部含有用户信息")
    private String token;

    /**
     * 商品id
     */
    @ApiModelProperty(value = "")
    private Integer gid;

    /**
     * 商品数量(比如说限购2件，限购3件，这个就是数量)
     */
    @ApiModelProperty(value = "")
    private Integer count;

    /**
     * 来源的渠道类型(pc端、app端、email、small等)
     */
    @ApiModelProperty(value = "")
    private String type;
}
