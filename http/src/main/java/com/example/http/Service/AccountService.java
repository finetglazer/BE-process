package com.example.http.Service;

import com.example.http.Payload.request.ChangePasswordRequest;
import com.example.http.Payload.response.BaseResponse;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;

public interface AccountService {
    BaseResponse<?> changePassword(ChangePasswordRequest changePasswordRequest, HttpServletRequest request);
    BaseResponse<?> getAllAccounts(Pageable pageable, String search);
}
