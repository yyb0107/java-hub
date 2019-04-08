package com.bingo.java.pattern.strategypattern;

/**
 * @author Bingo
 * @title: PayResult
 * @projectName java-hub
 */
public class PayResult {
    String code;
    String msg;
    String errorMsg;

    public PayResult(String code, String msg, String errorMsg) {
        this.code = code;
        this.msg = msg;
        this.errorMsg = errorMsg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public String toString() {
        return "PayResult{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", errorMsg='" + errorMsg + '\'' +
                '}';
    }
}
