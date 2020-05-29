package com.roilka.roilka.question.common.base;

import com.roilka.roilka.question.common.enums.BizResponseCodeEnum;

import java.io.Serializable;

/**
 * @ClassName BizRestException
 * @Description TODO
 * @Author 75six
 * @Date 2019/12/23 19:05
 **/
public class BizRestException extends RuntimeException implements Serializable {

    private BizResponseCodeEnum           errorCode;

    private String            errorMessage;

    public BizRestException() {
        super(BizResponseCodeEnum.OPERATION_FAILED.getDesc());
        this.errorCode = BizResponseCodeEnum.OPERATION_FAILED;
        this.errorMessage = errorCode.getDesc();

    }

    public BizRestException(BizResponseCodeEnum errorCode) {
        super(errorCode.getDesc());
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getDesc();
    }

    public BizRestException(BizResponseCodeEnum errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public BizRestException(BizResponseCodeEnum errorCode, String errorMessage, Throwable exception) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        super.initCause(exception);
    }

    public BizResponseCodeEnum getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorCode(BizResponseCodeEnum errorCode) {
        this.errorCode = errorCode;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public static boolean isBizException(Throwable exception) {

        return exception instanceof BizRestException;
    }

    public static boolean isErrorException(BizResponseCodeEnum errorCode) {

        boolean error = BizResponseCodeEnum.SYSTEM_ERROR.equals(errorCode)
                || BizResponseCodeEnum.CALLSERVICCE_ERROR.equals(errorCode)
                || BizResponseCodeEnum.URL_REQUEST_ERROR.equals(errorCode)
                || BizResponseCodeEnum.PROCESS_FAIL.equals(errorCode);

        return error;
    }
}
