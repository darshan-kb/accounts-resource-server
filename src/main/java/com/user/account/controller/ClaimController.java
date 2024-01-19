package com.user.account.controller;

import com.user.account.payload.TransactionPayload;
import com.user.account.service.ClaimService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@AllArgsConstructor
public class ClaimController {
    private ClaimService claimService;
    @PostMapping("/claims")
    public ResponseEntity<Double> claim(@RequestBody TransactionPayload transactionPayload, Principal p){
        return new ResponseEntity<Double>(claimService.redeemClaim(p.getName(), transactionPayload.getClaimAmount(), transactionPayload.getId()), HttpStatus.OK);
    }
}
