package com.example.http.Service;

import com.example.http.payload.request.UserChangeRequest;
import com.example.http.payload.request.UserUpdateRequest;
import com.example.http.payload.response.BaseMessageResponse;
import com.example.http.payload.response.BaseResponse;
import org.springframework.data.domain.Pageable;


public interface UserService {
    BaseMessageResponse<?> deleteUser(Long id);
    BaseResponse<?> getAllUser(Pageable pageable, String Search, Long RoleId);
    BaseResponse<?> getAllLeaders(Pageable pageable);
    BaseMessageResponse<?> updateRole(UserUpdateRequest userUpdateRequest);
    BaseResponse<?> changeUser(UserChangeRequest userChangeRequest);
}
