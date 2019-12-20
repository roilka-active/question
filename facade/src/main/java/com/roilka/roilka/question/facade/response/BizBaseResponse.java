package com.roilka.roilka.question.facade.response;

import com.roilka.roilka.question.common.enums.BizResponseCodeEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName BizBaseResponse
 * @Description TODO
 * @Author changyou
 * @Date 2019/11/26 19:53
 **/
@Data
public class BizBaseResponse<T> implements Serializable {

    private Integer code;

    private String message;

    private T result;

    public BizBaseResponse() {
        this(BizResponseCodeEnum.SUCCESS.getCode(), BizResponseCodeEnum.SUCCESS.getName(), null);
    }

    public BizBaseResponse(Integer code, String message) {
        this(code, message, null);
    }

    public BizBaseResponse(T data) {
        this(BizResponseCodeEnum.SUCCESS.getCode(), BizResponseCodeEnum.SUCCESS.getName(), data);
    }

    public BizBaseResponse(Integer code, String message, T data) {
        this.code = BizResponseCodeEnum.SUCCESS.getCode();
        this.message = BizResponseCodeEnum.SUCCESS.getName();
        this.result = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
