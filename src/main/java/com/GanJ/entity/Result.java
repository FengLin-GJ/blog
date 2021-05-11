package com.GanJ.entity;

import lombok.Data;

/**
 * @author: GanJ
 * @Date: 2021/2/18 17:15
 * Describe: 返回统一的响应格式
 */
@Data
public class Result<T> {

    /**
     * 错误码
     */
    private Integer code;

    /**
     * 提示信息
     */
    private String msg;

    /**
     * 具体内容
     */
    private  T data;

}
