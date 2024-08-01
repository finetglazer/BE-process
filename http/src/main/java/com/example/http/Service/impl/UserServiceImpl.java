package com.example.http.Service.impl;

import com.example.http.Model.Account;
import com.example.http.Model.User;
import com.example.http.Service.UserService;
import com.example.http.Payload.request.UserChangeRequest;
import com.example.http.Payload.request.UserUpdateRequest;
import com.example.http.Payload.response.BaseMessageResponse;
import com.example.http.Payload.response.BaseResponse;
import com.example.http.Payload.response.UserResponse;
import com.example.http.Repository.AccountRepository;
import com.example.http.Repository.UserRepository;
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

        User check = userRepository.findUserByUsername(userChangeRequest.getUsername()).orElse(null);
        if (check != null) {
            if (!userChangeRequest.getUsername().equals(user.getUsername()))
                return new BaseResponse<>(400, "ERROR.USERNAME.EXIST");

        }
        account.setUsername(userChangeRequest.getUsername());
        user.setUsername(userChangeRequest.getUsername());

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
