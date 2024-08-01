package com.example.http.Controller;

import com.example.http.Service.Oauth2Service;
import org.bouncycastle.pqc.math.linearalgebra.PolynomialRingGF2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/oauth2")
public class Oauth2Controller {
    @Autowired
    private Oauth2Service oauth2Service;

    @GetMapping("/getUrl")
    public ResponseEntity<?> getUrl(@RequestParam String authServer) {
        return ResponseEntity.ok(oauth2Service.getUrl(authServer));
    }

    @GetMapping("/getAccessToken")
    public ResponseEntity<?> getAccessToken(@RequestParam String code, @RequestParam String app) {
        return ResponseEntity.ok(oauth2Service.getAccessToken(code, app));
    }

    @GetMapping("/getInfo")
    public ResponseEntity<?> getInfo(@RequestHeader("Authorization") String accessToken, @RequestParam String app) {
        return ResponseEntity.ok(oauth2Service.getInfo(accessToken, app));
    }

//    @GetMapping("/getAccessToken/google")
//    public ResponseEntity<?> getAccessTokenGoogle(@RequestParam String code) {
//        return ResponseEntity.ok(oauth2Service.getAccessTokenGoogle(code));
//    }



}
