package com.zheng.vo;

import lombok.Data;

/**
 * @author: ZhengTianLiang
 * @date: 2021/5/17  9:54
 * @desc: 统一的返回结果
 */

@Data
public class R {

    /**
     * 返回code码,0成功1失败
     */
    private Integer code;

    /**
     * 返回信息
     */
    private String msg;

    /**
     * 返回数据
     */
    private Object data;

    public R(Integer code,String msg,Object data){
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static R ok(){return new R(0,"OK",null);}
    public static R ok(Object object){return new R(0,"OK",object);}
    public static R fail(){return new R(0,"ERROR",null);}
    public static R fail(String msg){return new R(0,msg,null);}
}
