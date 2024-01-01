package com.user.account.controller;

import com.user.account.payload.RechargePayload;
import com.user.account.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthenticatedAuthorizationManager;
import org.springframework.security.authorization.AuthoritiesAuthorizationManager;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
public class AccountController {

    @Autowired
    UserService userService;

    @GetMapping("account/balance")
    public ResponseEntity<Double> getbalance(Principal principal){
        return ResponseEntity.ok(userService.getbalance(principal.getName()));
    }

    @PostMapping("account/recharge")
    public ResponseEntity<Double> recharge(@RequestBody RechargePayload rechargePayload){
        return ResponseEntity.ok(userService.recharge(rechargePayload.getAmount(),rechargePayload.getEmail()));
    }
}
