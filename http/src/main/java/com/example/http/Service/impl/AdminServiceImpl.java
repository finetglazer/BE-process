package com.example.http.Service.impl;

import com.example.http.Payload.response.AccountResponse;
import com.example.http.Payload.response.BaseResponse;
import com.example.http.Payload.response.UserResponse;
import com.example.http.Repository.AccountRepository;
import com.example.http.Repository.UserRepository;
import com.example.http.Service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;


@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private UserRepository userRepository;

    /*There are 2 two function here, each will return a page, we need to add
    * a UserServiceImpl and AccountServiceImple*/
    @Override
    public BaseResponse<?> getAllAccountsUsersByThread(Pageable pageable) {
        long start = System.nanoTime();
        List<Object> list = new LinkedList<>();

        Runnable runnable1 = () -> {
            list.add(accountRepository.findAllAccounts(pageable).toList());
        };

        Runnable runnable2 = () -> {
            list.add(userRepository.findAllUsers(pageable).toList());
        };

        Thread thread1 = new Thread(runnable1);
        Thread thread2 = new Thread(runnable2);
        thread1.setPriority(6);

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long end = System.nanoTime();
        System.out.println("Using thread time: " + (end - start) / 1e6 + "ms");
        return new BaseResponse<>(1, list);

//        return new BaseResponse<>(1, accountResponses, userResponses);
    }

    @Override
    public BaseResponse<?> getAllAccountsUsers(Pageable pageable) {
        long start = System.nanoTime();
        List<Object> list = new LinkedList<>();
        list.add(accountRepository.findAllAccounts(pageable).toList());
        list.add(userRepository.findAllUsers(pageable).toList());

        long end = System.nanoTime();
        System.out.println("No thread Time: " + (end - start) / 1e6 + "ms");
        return new BaseResponse<>(1, list);
//        return new BaseResponse<>(1, accountResponses, userResponses);
    }

}
