package com.user.account.controller;

import com.user.account.dto.UserDTO;
import com.user.account.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

@RestController
public class HelloController {
//    @Autowired
//    private UserService userService;
//    @GetMapping("/token")
//    public String hello(HttpServletRequest request){
//        CsrfToken csrfToken = (CsrfToken) request.getAttribute("_csrf");
//        System.out.println(csrfToken.getToken());
//        return "Hello from accounts server";
//    }

//    @PostMapping("/account")
//    public ResponseEntity<String> createUser(@RequestBody UserDTO user){
//        String u = userService.createUser(user);
//        return ResponseEntity.ok(u);
//    }
}
