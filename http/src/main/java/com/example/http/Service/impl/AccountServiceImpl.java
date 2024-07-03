package com.example.http.Service.impl;

import com.example.http.Model.Account;
import com.example.http.Service.AccountService;
import com.example.http.payload.request.ChangePasswordRequest;
import com.example.http.payload.response.BaseResponse;
import com.example.http.repository.AccountRepository;
import com.example.http.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public BaseResponse<?> changePassword(ChangePasswordRequest changePasswordRequest, HttpServletRequest request) {
        Account account = jwtUtils.checkJwt(request);
        if (account == null) {
            return new BaseResponse<>(401, "ERROR.NOT_AUTHORIZED");
        }
//        System.out.println(account);
        if (encoder.matches(changePasswordRequest.getOldPassword(), account.getPassword())) {
            if (changePasswordRequest.getOldPassword().equals(changePasswordRequest.getNewPassword())) {
                return new BaseResponse<>(400, "ERROR.PASSWORD_SAME");
            }
            if (changePasswordRequest.getNewPassword().equals(changePasswordRequest.getConfirmPassword())) {
                account.setPassword(encoder.encode(changePasswordRequest.getNewPassword()));
                accountRepository.save(account);
                return new BaseResponse<>(200, "SUCCESS.CHANGE_PASSWORD");
            } else {
                return new BaseResponse<>(400, "ERROR.PASSWORD_NOT_MATCH");
            }
        } else {
            return new BaseResponse<>(400, "ERROR.WRONG_CURRENT_PASSWORD");
        }

    }
}
