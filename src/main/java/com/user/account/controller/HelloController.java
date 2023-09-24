package com.user.account.controller;

import com.user.account.dto.UserDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello(HttpServletRequest request){
        CsrfToken csrfToken = (CsrfToken) request.getAttribute("_csrf");
        System.out.println(csrfToken.getToken());
        return "Hello from accounts server";
    }

    @PostMapping("/account/create")
    public ResponseEntity<String> createUser(@RequestBody UserDTO user){
        return ResponseEntity.ok("ok");
    }
}
