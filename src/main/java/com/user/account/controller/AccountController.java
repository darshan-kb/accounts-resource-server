package com.user.account.controller;

import com.user.account.dto.UserDTO;
import com.user.account.entities.User;
import com.user.account.payload.OtpPayload;
import com.user.account.payload.RechargeConfirmationPayload;
import com.user.account.payload.RechargePayload;
import com.user.account.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthenticatedAuthorizationManager;
import org.springframework.security.authorization.AuthoritiesAuthorizationManager;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
public class AccountController {

    @Autowired
    UserService userService;
    Logger logger = LoggerFactory.getLogger(AccountController.class);

    @GetMapping("account/balance")
    public ResponseEntity<Double> getbalance(Authentication a){
        String authorities = a.getAuthorities().toString();
        logger.info(authorities);
        return ResponseEntity.ok(userService.getbalance(a.getName(), authorities.substring(1,authorities.length()-1)));
    }

    @PostMapping("account/recharge")
    public ResponseEntity<Long> recharge(@RequestBody RechargePayload rechargePayload, Principal a){
        return ResponseEntity.ok(userService.recharge(rechargePayload.getAmount(),rechargePayload.getEmail(),a.getName()));
    }

    @PostMapping("account/recharge/confirm")
    public ResponseEntity<RechargeConfirmationPayload> recharge(@RequestBody OtpPayload otp){
        return ResponseEntity.ok(userService.confirmRecharge(otp.getOtpId(), otp.getOtp()));
    }

    @GetMapping("account/users")
    public ResponseEntity<List<String>> getAllUserEmail(){
        return ResponseEntity.ok(userService.getAllUserEmail());
    }
}
