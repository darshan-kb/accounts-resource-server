package com.user.account.controller;

import com.user.account.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthenticatedAuthorizationManager;
import org.springframework.security.authorization.AuthoritiesAuthorizationManager;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class AccountController {

    @Autowired
    UserService userService;

    @GetMapping("account/balance/{email}")
    public ResponseEntity<Double> getbalance(@PathVariable String email, Principal principal){
        System.out.println(principal);
        return ResponseEntity.ok(userService.getbalance(email));
    }
}
