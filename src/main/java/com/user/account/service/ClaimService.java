package com.user.account.service;

import com.user.account.entities.AccountTransaction;
import com.user.account.entities.User;
import com.user.account.repository.AccountTransactionRepository;
import com.user.account.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class ClaimService {
    private UserRepository userRepository;
    private AccountTransactionRepository accountTransactionRepository;
    @Transactional
    public double redeemClaim(String email, double amount, String remark){
        User user = userRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("User not found"));
        user.setBalance(user.getBalance()+amount);
        AccountTransaction transaction = AccountTransaction.builder()
                .timeStamp(LocalDateTime.now())
                        .user(user)
                                .depositAmount(amount)
                                        .balance(user.getBalance())
                                                .withdrawalAmount(0.0)
                                                        .remark(remark)
                                                                .build();
        accountTransactionRepository.save(transaction);
        userRepository.save(user);
        return user.getBalance();
    }
}
