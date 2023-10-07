package com.user.account.service;

import com.user.account.entities.User;
import com.user.account.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ClaimService {
    private UserRepository userRepository;
    public double redeemClaim(String email, double amount){
        User user = userRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("User not found"));
        user.setBalance(user.getBalance()+amount);
        userRepository.save(user);
        return user.getBalance();
    }
}
