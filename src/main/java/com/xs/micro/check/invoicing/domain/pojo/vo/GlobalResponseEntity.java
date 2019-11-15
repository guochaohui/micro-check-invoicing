package com.xs.micro.check.invoicing.domain.pojo.vo;

import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 * 统一的异常处理响应对象
 * @author typ
 */
public class GlobalResponseEntity<T> implements Serializable {

    private static final long serialVersionUID = -7138912839325217911L;

    private int code;

    private String msg;

    private T data;

    public GlobalResponseEntity() {
    }

    public GlobalResponseEntity(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 响应成功
     * @return
     */
    public static GlobalResponseEntity success(){
        return new GlobalResponseEntity(HttpStatus.OK.value(),"ok",null);
    }

    /**
     * 响应成功，并返回说明文字以及数据
     * @param msg
     * @param data
     * @return
     */
    public static GlobalResponseEntity success(String msg,Object data){
        return new GlobalResponseEntity(HttpStatus.OK.value(),msg,data);
    }

    /**
     * 响应失败
     * @return
     */
    public static GlobalResponseEntity failure(String msg) {
        return failure(HttpStatus.INTERNAL_SERVER_ERROR.value(), msg);
    }

    /**
     * 响应失败，并返回说明文字以及数据
     * @param msg
     * @param data
     * @return
     */
    public static GlobalResponseEntity failure(String msg,Object data) {
        return new GlobalResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR.value(), msg,data);
    }

    public static GlobalResponseEntity failure(Integer code, String msg) {
        return new GlobalResponseEntity(code, msg, null);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean ok() {
        return this.code == HttpStatus.OK.value();
    }

    public boolean processing() {
        return this.code == HttpStatus.PROCESSING.value();
    }

    public boolean failed() {
        return !ok() && !processing();
    }

}
