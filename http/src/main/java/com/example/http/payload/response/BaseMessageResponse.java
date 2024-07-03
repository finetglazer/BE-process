package com.example.http.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseMessageResponse<T> {
    private Integer status;
    private String message;
    private T data;

    public BaseMessageResponse(Integer status, String message) {
        this.status = status;
        this.message = message;
    }

    public BaseMessageResponse(T data) {
        this.status = 1;
        this.data = data;
    }
}
