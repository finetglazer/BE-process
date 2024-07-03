package com.example.http.Service;

import com.example.http.payload.request.ChangePasswordRequest;
import com.example.http.payload.response.BaseResponse;
import com.fasterxml.jackson.databind.ser.Serializers;

import javax.servlet.http.HttpServletRequest;

public interface AccountService {
    BaseResponse<?> changePassword(ChangePasswordRequest changePasswordRequest, HttpServletRequest request);
}
