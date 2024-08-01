package com.example.http.Controller;

import com.google.api.client.http.HttpResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/callback")
public class CallBackController {
    @GetMapping
    public String callback(@RequestParam("code") String code) {
        // The authorization code can be used to request an access token
        return "Authorization code received: " + code;
    }
}
