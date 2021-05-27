package com.zheng.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: ZhengTianLiang
 * @date: 2021/5/17  9:26
 * @desc: 用户表
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "用户表")
public class User {

    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id")
    private Integer uid;

    /**
     * 用户手机号/账户
     */
    @ApiModelProperty(value = "用户手机号/账户")
    private String phone;

    /**
     * 用户密码，加密后的密文
     */
    @ApiModelProperty(value = "用户密码，加密后的密文")
    private String password;
}
