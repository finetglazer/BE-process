package com.example.http.Service;

import com.example.http.payload.request.RefreshTokenRequest;
import com.example.http.payload.response.BaseResponse;
import com.fasterxml.jackson.databind.ser.Serializers;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

public interface RefreshTokenService {
//    BaseResponse<?> refreshToken(RefreshTokenRequest refreshTokenRequest);
    BaseResponse<?> autoRefreshToken(HttpServletRequest request);
    @Transactional
    BaseResponse<?> deleteToken(Long userId);
}
