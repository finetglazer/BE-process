package com.example.http.Service;

import com.example.http.Payload.request.UserChangeRequest;
import com.example.http.Payload.request.UserUpdateRequest;
import com.example.http.Payload.response.BaseMessageResponse;
import com.example.http.Payload.response.BaseResponse;
import org.springframework.data.domain.Pageable;


public interface UserService {
    BaseMessageResponse<?> deleteUser(Long id);
    BaseResponse<?> getAllUser(Pageable pageable, String Search, Long RoleId);
    BaseResponse<?> getAllLeaders(Pageable pageable);
    BaseMessageResponse<?> updateRole(UserUpdateRequest userUpdateRequest);
    BaseResponse<?> changeUser(UserChangeRequest userChangeRequest);
}
