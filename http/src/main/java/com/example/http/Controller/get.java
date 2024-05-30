package com.example.http.Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/")
public class get {

    @Autowired
    private HttpServletRequest httpServletRequest;


    @RequestMapping("/get")
    public String get() {
        HttpSession httpSession = httpServletRequest.getSession();
        httpSession.setMaxInactiveInterval(30 * 60);
        return httpSession.getId() + "\n" +
                httpServletRequest.getRequestURI()
                + "\n" + httpServletRequest.getQueryString()
                + "\n" + httpServletRequest.getRemoteAddr()
                + "\n" + httpServletRequest.getRemoteHost()
                + "\n" + httpServletRequest.getRemotePort()
                + "\n" + httpServletRequest.getRemoteUser();

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
