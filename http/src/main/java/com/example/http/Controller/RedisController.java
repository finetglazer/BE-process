package com.example.http.Controller;

import com.example.http.Model.RedisDTO;
import com.example.http.Service.BaseRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/redis")
public class RedisController {

    @Autowired
    private BaseRedisService baseRedisService;

    @PostMapping("/setKey")
    public void set(@RequestBody RedisDTO redisDTO) {
        if (redisDTO != null) {
            System.out.println(redisDTO);
            System.out.println(redisDTO.getKey() + " " + redisDTO.getValue());
            baseRedisService.set(redisDTO.getKey(), redisDTO.getValue());
            System.out.println("Key set successfully");
        } else {
            // Handle the case where key is null (e.g., throw an exception, return an error message)
            System.out.println("Error: Key cannot be null");
        }
    }


    @PostMapping("/setKey2")
    public void set() {
        baseRedisService.set("missing", "you");

    }

    @PutMapping("/put")
    public void put(@RequestBody RedisDTO redisDTO) {
        baseRedisService.set(redisDTO.getKey(), redisDTO.getValue());
//        baseRedisService.put("put", "put");
    }

}
