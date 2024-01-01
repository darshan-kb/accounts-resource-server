package com.user.account.controller;

import com.user.account.model.ApiResponse;
import com.user.account.payload.AddTicketPayload;
import com.user.account.service.TicketService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class TicketController {
    @Autowired
    TicketService ticketService;
    @PostMapping("/ticket/add")
    public ResponseEntity<Double> addticket(@RequestBody AddTicketPayload addTicketPayload, Principal p){
        double balance = ticketService.addTicket(p.getName(), addTicketPayload.getAmount(), addTicketPayload.getTicketId());
        return new ResponseEntity<Double>(balance,HttpStatus.OK);
    }

    @PostMapping("/ticket/error")
    public ResponseEntity<Double> errorticket(@RequestBody AddTicketPayload addTicketPayload, Principal p){
        double balance = ticketService.errorTicket(p.getName(), addTicketPayload.getAmount());
        return new ResponseEntity<Double>(balance,HttpStatus.OK);
    }
}
