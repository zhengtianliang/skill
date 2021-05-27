package com.zheng.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author: ZhengTianLiang
 * @date: 2021/5/20  17:39
 * @desc: token的dto
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "token的dto")
public class TokenDTO {

    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号")
    private String phone;

    /**
     * token的时间
     */
    @ApiModelProperty(value = "token的时间")
    private Date sdate;
}
