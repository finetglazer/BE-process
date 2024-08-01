package com.example.http.Service.impl;

import com.example.http.Model.Account;
import com.example.http.Model.RefreshToken;
import com.example.http.Model.User;
import com.example.http.Service.RefreshTokenService;
import com.example.http.Payload.response.BaseResponse;
import com.example.http.Repository.RefreshTokenRepository;
import com.example.http.Repository.UserRepository;
import com.example.http.Security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

@Service
@Scope("prototype")
public class RefreshTokenServiceImpl implements RefreshTokenService {
    @Autowired
    private UserRepository UserRepository;
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;


    @Override
    public BaseResponse<?> autoRefreshToken(HttpServletRequest request) {
        Account account = jwtUtils.checkJwt(request);
        if (account == null) {
            return new BaseResponse<>("TOKEN.ERROR.INVALID");
        }
        String username = account.getUsername();
        User user = UserRepository.findUserByUsername(username).orElse(null);
        if (user == null) {
            return new BaseResponse<>("USER.ERROR.NOT_FOUND");
        }
        String newToken = jwtUtils.generateTokenFromUsername(username);
        /*
        Illustration: If the user has a refresh token in the database, update the token and expiry date.
        but the problem here is if it is necessary to check exist token in the database
        Answer: no because the user are in the server and login successfully, so the token exactly in database until they sign out.
         */
//        if (refreshTokenRepository.existsByUserId(user.getId())) {
//            RefreshToken refreshToken = refreshTokenRepository.findByUserId(user.getId());
//            refreshToken.setToken(newToken);
//            refreshToken.setExpiryDate(jwtUtils.getExpirationDateFromJwtToken(newToken));
//            System.out.println(refreshToken.toString());
//            refreshTokenRepository.save(refreshToken);
//        } else {
//            System.out.println("new user");
//            refreshTokenRepository.save(new RefreshToken(user.getId(), newToken, jwtUtils.getExpirationDateFromJwtToken(newToken)));
//        }
        //optimize
        RefreshToken refreshToken = refreshTokenRepository.findByUserId(user.getId());
        refreshToken.setToken(newToken);
        refreshToken.setExpiryDate(jwtUtils.getExpirationDateFromJwtToken(newToken));
//        System.out.println(refreshToken.toString());
        refreshTokenRepository.save(refreshToken);

        return new BaseResponse<>(1, newToken);

    }

    @Transactional
    @Override
    public BaseResponse<?> deleteToken(Long userId) {

        refreshTokenRepository.deleteByUserId(userId);
        return new BaseResponse<>(1, "LOGOUT.SUCCESS");
    }
}
