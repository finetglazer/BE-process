package com.example.http.payload.response;

import com.example.http.Model.Account;
import lombok.Data;
import org.springframework.http.HttpStatus;

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
