package com.user.account.service;

import com.user.account.dto.UserDTO;
import com.user.account.entities.User;
import com.user.account.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    public String createUser(UserDTO user){
        return userRepository.save(new User(user)).toString();
    }

    public double getbalance(String email){
        return userRepository.findByEmail(email).get().getBalance();
    }
}
