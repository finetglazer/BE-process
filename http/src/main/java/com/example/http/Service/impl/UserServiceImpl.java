package com.example.http.Service.impl;

import com.example.http.Model.Account;
import com.example.http.Model.User;
import com.example.http.Service.UserService;
import com.example.http.payload.request.UserChangeRequest;
import com.example.http.payload.request.UserUpdateRequest;
import com.example.http.payload.response.BaseMessageResponse;
import com.example.http.payload.response.BaseResponse;
import com.example.http.payload.response.UserResponse;
import com.example.http.repository.AccountRepository;
import com.example.http.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AccountRepository accountRepository;

    @Transactional
    @Override
    public BaseMessageResponse<?> deleteUser(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return new BaseMessageResponse<>(404, "User not found");
        }

        userRepository.delete(user);
        return new BaseMessageResponse<>(200, "User deleted successfully");
    }

    @Override
    public BaseResponse<?> getAllUser(Pageable pageable, String Search, Long RoleId) {
        Page<UserResponse> userResponsePage;
        if (RoleId != 0L) {
            userResponsePage = userRepository.findAllByKeywordAndNumber(pageable, Search, RoleId);
        } else {
            userResponsePage = userRepository.findAllByKeyword(pageable, Search, RoleId);
        }
        return new BaseResponse<>(userResponsePage);
    }

    @Override
    public BaseResponse<?> getAllLeaders(Pageable pageable) {
        Page<UserResponse> userResponsePage = userRepository.findAllLeaders(pageable);
        return new BaseResponse<>(userResponsePage);
    }

    @Override
    public BaseMessageResponse<?> updateRole(UserUpdateRequest userUpdateRequest) {
        User user = userRepository.findById(userUpdateRequest.getId()).orElse(null);
        if (user == null) {
            return new BaseMessageResponse<>(404, "ERROR.USER.NOT.FOUND");
        }
        if (user.getRoleId() == 1) {
            return new BaseMessageResponse<>(400, "ERROR.CANNOT.UPDATE.ADMIN");
        }
        user.setRoleId(userUpdateRequest.getUpdateid());
        userRepository.save(user);
        return new BaseMessageResponse<>(200, "SUCCESSFULLY.UPDATED.ROLE");
    }

    @Override
    public BaseResponse<?> changeUser(UserChangeRequest userChangeRequest) {
        User user = userRepository.findById(userChangeRequest.getId()).orElse(null);
        if (user == null) {
            return new BaseResponse<>(404, "ERROR.USER.NOT.FOUND");
        }
        Account account = accountRepository.findAccountByUsername(user.getUsername()).orElse(null);
        if (account == null) {
            return new BaseResponse<>(404, "ERROR.ACCOUNT.NOT.FOUND");
        }
        if (userChangeRequest.getUsername() != null) {
            account.setUsername(userChangeRequest.getUsername());
            user.setUsername(userChangeRequest.getUsername());
        }
        if (userChangeRequest.getRoleId() != null) {
            user.setRoleId(userChangeRequest.getRoleId());
        }
        if (userChangeRequest.getName() != null) {
            user.setName(userChangeRequest.getName());
        }

        accountRepository.save(account);
        userRepository.save(user);

        return new BaseResponse<>(200, user);

    }
}
