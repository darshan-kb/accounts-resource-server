package com.user.account.service;

import com.user.account.entities.AccountTransaction;
import com.user.account.entities.User;
import com.user.account.repository.AccountTransactionRepository;
import com.user.account.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TicketService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AccountTransactionRepository accountTransactionRepository;

    @Transactional
    public double addTicket(String email, String authorities, double amount, String remark){
        if(amount<0){
            throw new IllegalStateException("Amount is not valid");
        }
        User user = userRepository.findByEmail(email).orElseGet(()->{
            return userRepository.save(new User(email,email,authorities,0.0));
        });

        double balance = user.getBalance();
        if(balance >= amount){
            user.setBalance(balance-amount);
            AccountTransaction transaction = AccountTransaction.builder()
                            .balance(user.getBalance())
                                    .remark(remark)
                                            .depositAmount(0.0)
                                                    .withdrawalAmount(amount)
                                                            .timeStamp(LocalDateTime.now())
                                                                    .user(user)
                                                                            .build();
            accountTransactionRepository.save(transaction);
            userRepository.save(user);
        }
        else{
            throw new IllegalStateException("Insufficient balance");
        }

        return user.getBalance();
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
