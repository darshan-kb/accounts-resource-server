package com.user.account.controller;

import com.user.account.model.ApiResponse;
import com.user.account.service.TicketService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TicketController {
    @Autowired
    TicketService ticketService;
    @PostMapping("/ticket/add")
    public ResponseEntity<Double> addticket(@RequestBody String email, @RequestBody double amount){
        double balance = ticketService.addTicket(email,amount);
        return new ResponseEntity<Double>(balance,HttpStatus.OK);
    }
}
