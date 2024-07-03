package com.example.http.Controller;

import com.example.http.Model.AccessLog;
import com.example.http.Model.Account;
import com.example.http.Model.RefreshToken;
import com.example.http.Model.User;
import com.example.http.Service.RefreshTokenService;
import com.example.http.Service.impl.UserDetailsImpl;
import com.example.http.Service.impl.UserDetailsServiceImpl;
import com.example.http.common.Const;
import com.example.http.payload.request.RefreshTokenRequest;
import com.example.http.payload.request.SignUpRequest;
import com.example.http.payload.response.BaseMessageResponse;
import com.example.http.payload.response.BaseResponse;
import com.example.http.payload.request.LoginRequest;
import com.example.http.payload.response.JwtResponse;
//import com.example.http.payload.response.MessageResponse;
import com.example.http.repository.AccessLogRepository;
import com.example.http.repository.AccountRepository;
import com.example.http.repository.RefreshTokenRepository;
import com.example.http.repository.UserRepository;
import com.example.http.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private AccessLogRepository accessLogRepository;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private RefreshTokenRepository RefreshTokenRepository;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            Account account = userDetailsService.findAccountByUsername(loginRequest.getUsername());
            if (ObjectUtils.isEmpty(account)) {
                return ResponseEntity.ok(new BaseResponse<>("ACCOUNT.ERROR.USERNAME"));
            } else if (!encoder.matches(loginRequest.getPassword(), account.getPassword())){
                return ResponseEntity.ok(new BaseResponse<>("ACCOUNT.ERROR.PASSWORD"));
            }
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

            userDetailsService.saveLastLoginByUsername(account);

            String jwt = jwtUtils.generateJwtToken(userDetails);
            //save token
            try {
                User user = userRepository.findUserByUsername(account.getUsername()).orElseThrow(null);
                RefreshToken refreshToken = new RefreshToken(user.getId(), jwt, jwtUtils.getExpirationDateFromJwtToken(jwt));
                RefreshTokenRepository.save(refreshToken);
                accessLogRepository.save(AccessLog.builder().accountId(account.getId())
                        .accessLogin(LocalDateTime.now())
                        .build());
            } catch (Exception e) {
                return ResponseEntity.ok(new BaseResponse<>("ERROR.ACCOUNT.IS.LOGINING.AT.ANOTHER.PLACE"));
            }



            return ResponseEntity.ok(new JwtResponse(Const.STATUS_RESPONSE.SUCCESS, jwt));
        } catch (Exception e) {
            return ResponseEntity.ok(new BaseResponse<>("ERROR.AUTHENTICATION"));
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new BaseResponse<>("ERROR: Username is already taken!"));
        }

        Account account = new Account(signUpRequest.getUsername(),
                encoder.encode(signUpRequest.getPassword()));
        accountRepository.save(account);

        User user = new User();
        user.setUsername(signUpRequest.getUsername());
        user.setName(signUpRequest.getUsername());// default name
        user.setRoleId(signUpRequest.getRoleId());
        userRepository.save(user);

        return ResponseEntity.ok(new BaseResponse<>("SUCCESS: User registered successfully!"));
    }

//    @PostMapping("/refresh_token")
//    public ResponseEntity<?> refreshToken(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
//
//        return ResponseEntity.ok(refreshTokenService.refreshToken(refreshTokenRequest));
//    }

    @PostMapping("/autorefresh_token")
    public ResponseEntity<?> autoRefreshToken(HttpServletRequest request) {
        return ResponseEntity.ok(refreshTokenService.autoRefreshToken(request));
    }

//    @PostMapping("/logout")
//    public ResponseEntity<?> logout(HttpServletRequest request) {
//        return ResponseEntity.ok(refreshTokenService.logout(request));
//    }

    // sign out means we delete token of that user in database
    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findUserByUsername(userDetails.getUsername()).orElse(null);
        if (user == null) {
            return ResponseEntity.ok(new BaseResponse<>("USER.ERROR.NOT_FOUND"));
        }

        return ResponseEntity.ok(new BaseResponse<>(1, refreshTokenService.deleteToken(user.getId())));
    }
    //sign out by Http
    @PostMapping("/signout")
    public ResponseEntity<?> signOut(HttpServletRequest request) {
        Account account = jwtUtils.checkJwt(request);
        if (account == null) {
            return ResponseEntity.ok(new BaseResponse<>("TOKEN.ERROR.INVALID"));
        }
        User user = userRepository.findUserByUsername(account.getUsername()).orElse(null);
        if (user == null) {
            return ResponseEntity.ok(new BaseResponse<>("USER.ERROR.NOT_FOUND"));
        }
        return ResponseEntity.ok(new BaseResponse<>(1, refreshTokenService.deleteToken(user.getId())));
    }
}
