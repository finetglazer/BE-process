package com.example.http.Controller;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.example.http.Configuration.GoogleDriverConfig;
import com.example.http.Model.AccessLog;
import com.example.http.Model.Account;
import com.example.http.Service.impl.UserDetailsServiceImpl;
import com.example.http.payload.request.SignUpRequest;
import com.example.http.payload.response.BaseResponse;
import com.example.http.repository.AccessLogRepository;
import com.example.http.repository.AccountRepository;
import com.google.api.services.drive.model.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/get")
public class get {

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccessLogRepository accessLogRepository;

    @Autowired
    private GoogleDriverConfig googleDriveService;

    @GetMapping("/list-files")
    public List<File> listFiles() throws IOException{
        return googleDriveService.getDriveService().files().list().execute().getFiles();
    }

//    @PreAuthorize("hasAuthority('system_user_view')")
    @GetMapping("/getAllUser")
    public ResponseEntity<?> getAllUser(@PageableDefault Pageable pageable, String Search,String RoleId) {
        // get all user
//        System.out.println("Hello from controller!");
//        System.out.println(Search + RoleId);
        return ResponseEntity.ok(Search + RoleId);
    }


    @GetMapping("/getAccount/{username}")
    public BaseResponse<?> getAccount(@PathVariable(name = "username") String Username) {
        Account account = accountRepository.nghichNgu(Username);
        return new BaseResponse<>(HttpStatus.OK.value(), account);
    }



    @PostMapping("/testUserDetailsService")
    public ResponseEntity<?> testUserDetailsService(@RequestBody @Valid SignUpRequest signUpRequest) {
        Account account = userDetailsService.findAccountByUsername(signUpRequest.getUsername());
        userDetailsService.saveLastLoginByUsername(account);
        accessLogRepository.save(AccessLog.builder().accountId(account.getId())
                .accessLogin(LocalDateTime.now())
                .build());

        if (ObjectUtils.isEmpty(account)) {
            return ResponseEntity.ok(new BaseResponse<>("ACCOUNT.ERROR.USERNAME"));
        } else if (!signUpRequest.getPassword().equals(account.getPassword())) {
            return ResponseEntity.ok(new BaseResponse<>("ACCOUNT.ERROR.PASSWORD"));
        }

        return ResponseEntity.ok(account);
    }

    @PutMapping("/putAccount")
    public BaseResponse<?> putAccount(@RequestBody @Valid SignUpRequest signUpRequest) {
        Account account = new Account(signUpRequest.getUsername(), signUpRequest.getPassword());
        Account savedAccount = accountRepository.save(account);
        return new BaseResponse<>(HttpStatus.OK.value(), savedAccount);
    }

    @RequestMapping("/gett")
    public String get() {
        HttpSession httpSession = httpServletRequest.getSession();
        httpSession.setMaxInactiveInterval(30 * 60);
        return httpSession.getId() + "\n" +
                httpServletRequest.getRequestURI()
                + "\n" + httpServletRequest.getQueryString()
                + "\n" + httpServletRequest.getRemoteAddr()
                + "\n" + httpServletRequest.getRemoteHost()
                + "\n" + httpServletRequest.getRemotePort()
                + "\n" + httpServletRequest.getRemoteUser()
                + "\n" + httpServletRequest.getHeaders("authority");

//        return "Hello World";
    }

    @PutMapping("/put")
    public ResponseEntity<String> put() {


        return new ResponseEntity<>("Hello World", HttpStatus.OK);
    }

    @WebServlet("/MyWebApplication/personal/info/top")
    public class MyServlet extends HttpServlet {
        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
            // Get the request URI
            String requestURI = request.getRequestURI();
            System.out.println("Request URI: " + requestURI);

            // Get the context path
            String contextPath = request.getContextPath();
            System.out.println("Context Path: " + contextPath);

            // Get the servlet path
            String servletPath = request.getServletPath();
            System.out.println("Servlet Path: " + servletPath);

            // Get the path info
            String pathInfo = request.getPathInfo();
            System.out.println("Path Info: " + pathInfo);

            // Get the query string
            String queryString = request.getQueryString();
            System.out.println("Query String: " + queryString);

            // Your custom logic goes here (e.g., fetching data based on the request)

            // Send a response (you can write HTML content or perform other actions)
            response.setContentType("text/html");
            response.getWriter().println("<html><body>");
            response.getWriter().println("<h1>Hello from MyServlet!</h1>");
            response.getWriter().println("</body></html>");
        }
    }


}
