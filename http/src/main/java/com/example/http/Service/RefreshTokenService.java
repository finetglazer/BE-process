package com.example.http.Service;

import com.example.http.Payload.response.BaseResponse;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

public interface RefreshTokenService {
//    BaseResponse<?> refreshToken(RefreshTokenRequest refreshTokenRequest);
    BaseResponse<?> autoRefreshToken(HttpServletRequest request);
    @Transactional
    BaseResponse<?> deleteToken(Long userId);
}
