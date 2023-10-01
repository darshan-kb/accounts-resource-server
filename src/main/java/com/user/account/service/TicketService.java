package com.user.account.service;

import com.user.account.entities.User;
import com.user.account.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TicketService {
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public double addTicket(String email, double amount){
        if(amount<0){
            throw new IllegalStateException("Amount is not valid");
        }
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        double balance = user.getBalance();
        if(balance > amount){
            user.setBalance(balance-amount);
            userRepository.save(user);
        }
        else{
            throw new IllegalStateException("Insufficient balance");
        }

        return balance-amount;
    }

    public double errorTicket(String email, double amount) {
        if(amount<0){
            throw new IllegalStateException("Amount is not valid");
        }
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        double balance = user.getBalance();
        user.setBalance(balance+amount);
        userRepository.save(user);
        return balance+amount;
    }
}
