package com.loyalove.baseboot.common.model;

import java.io.Serializable;

/**
 * Title: Result.java
 * Description: Result
 * Company: ysh
 *
 * @author: sailuo@yiji.com
 * @date: 2016-11-29 18:16
 */

public class Result implements Serializable {

    private static final long serialVersionUID = 1L;

    //成功
    private static final String STATUS_OK = "OK";
    //失败
    private static final String STATUS_FAIL = "FAIL";
    //错误
    private static final String STATUS_ERROR = "ERROR";

    //状态
    private String status;

    //消息（失败原因或错误原因）
    private String message;

    //返回结果
    private Object result;

    //分页对象
    private Pager pager;

    public Result() {
        this.setStatus(STATUS_OK);
    }

    public Result(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public Result(Object result, Pager pager) {
        this.result = result;
        this.pager = pager;
        this.setStatus(STATUS_OK);
    }

    public static Result getResultSuccess(String message) {
        Result result = new Result(STATUS_OK, message);
        return result;
    }

    public static Result getResultSuccess(String message, Object obj) {
        Result result = getResultSuccess(message);
        result.setResult(obj);
        return result;
    }

    public static Result getResultSuccess(String message, Object obj, Pager pager) {
        Result result = getResultSuccess(message, obj);
        result.setResult(pager);
        return result;
    }

    public static Result getResultFail(String message) {
        Result result = new Result(STATUS_FAIL, message);
        return result;
    }

    public static Result getResultError(String message) {
        Result result = new Result(STATUS_ERROR, message);
        return result;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public Pager getPager() {
        return pager;
    }

    public void setPager(Pager pager) {
        this.pager = pager;
    }
}
