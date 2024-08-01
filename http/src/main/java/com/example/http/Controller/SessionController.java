package com.example.http.Controller;

import javax.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/session")
public class SessionController {

    @GetMapping("/get")
    public String getAllInformationOfSession(HttpSession session) {
        String result = "";
        result += "Session ID: " + session.getId() + "<br>";
        result += "Creation Time: " + session.getCreationTime() + "<br>";
        result += "Last Accessed Time: " + session.getLastAccessedTime() + "<br>";
        result += "Max Inactive Interval: " + session.getMaxInactiveInterval() + "<br>";
        return result;
    }
}