package com.zheng.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: ZhengTianLiang
 * @date: 2021/5/19  17:44
 * @desc: 用户登陆的请求dto
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "用户登陆的请求dto")
public class LoginDTO {

    /**
     * 用户名/手机号
     */
    @ApiModelProperty(value = "用户名/手机号")
    private String phone;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码")
    private String pwd;
}
