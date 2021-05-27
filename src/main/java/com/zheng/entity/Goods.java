package com.zheng.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: ZhengTianLiang
 * @date: 2021/5/17  9:25
 * @desc: 商品类，实体
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "商品类，实体")
public class Goods {

    /**
     * 商品id
     */
    @ApiModelProperty(value = "商品id")
    private String goodsId;

}
