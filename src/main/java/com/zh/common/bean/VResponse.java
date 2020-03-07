package com.zh.common.bean;

public class VResponse<T> {
    private int code = 1;
    private Integer amount;
    private String msg;
    private T result;


    //通用错误码
    public static final int SUCCESS = 1;

    public VResponse(int errCode, String errMsg) {
        this.code = errCode;
        this.msg = errMsg;
    }

    public VResponse(int code, Integer amount, T result) {
        this.code = code;
        this.amount = amount;
        this.result = result;
    }

    public VResponse(int code, String msg, T result) {
        this.code = code;
        this.msg = msg;
        this.result = result;
    }

    public VResponse() {

    }

    public static <T> VResponse<T> success(T result){
        VResponse<T> response = new VResponse<T>(SUCCESS, null);
        response.result = result;
        return response;
    }

    public static <T> VResponse<T> success(String msg){
        return new VResponse<T>(SUCCESS, msg);
    }

    public static <T> VResponse<T> success(){
        return new VResponse<T>(SUCCESS, null);
    }

    public static <T> VResponse<T> error(int code,String msg){
        VResponse<T> response = new VResponse<T>();
        response.setCode(code);
        response.setMsg(msg);
        return response;
    }

    public static <T> VResponse<T> errorException(int code, T result){
        VResponse<T> response = new VResponse<T>();
        response.setCode(code);
        response.setResult(result);
        return response;
    }

    public static <T> VResponse<T> success(Integer amount,T result){
        VResponse<T> response = new VResponse<T>(SUCCESS,amount,result);
        response.setAmount(amount);
        response.result = result;
        return response;
    }

    public static <T> VResponse<T> result(Integer amount,T result){
        VResponse<T> response = new VResponse<T>(SUCCESS,amount,result);
        response.setAmount(amount);
        response.result = result;
        return response;
    }

    public static <T> VResponse<T> success(String msg,T result){
        VResponse<T> response = new VResponse<T>(SUCCESS, msg, result);
//        response.setAmount(amount);
//        response.result = result;
        return response;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

}

