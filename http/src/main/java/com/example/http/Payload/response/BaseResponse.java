package com.example.http.Payload.response;

import lombok.Data;

@Data
public class BaseResponse<T> {

    private Integer status;
    private T data;


    public BaseResponse(Integer status, T data) {
        this.status = status;
        this.data = data;
    }

    public BaseResponse(T data) {
        this.status = 200;
        this.data = data;
    }
}
