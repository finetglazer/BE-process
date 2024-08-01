package com.example.http.Service;

import com.example.http.Payload.response.BaseResponse;
import org.springframework.data.domain.Pageable;

public interface AdminService {
    BaseResponse<?> getAllAccountsUsers(Pageable pageable);
    BaseResponse<?> getAllAccountsUsersByThread(Pageable pageable);
}
