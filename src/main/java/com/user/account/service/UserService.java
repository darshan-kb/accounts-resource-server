package com.user.account.service;

import com.user.account.dto.UserDTO;
import com.user.account.entities.User;
import com.user.account.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenService tokenService;
    public String createUser(UserDTO user){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers2 = new HttpHeaders();
        headers2.set("Authorization","Bearer "+tokenService.getToken());
        HttpEntity<String> entity = new HttpEntity(user ,headers2);

        ResponseEntity<UserDTO> response2 = restTemplate.exchange("http://localhost:9090/account", HttpMethod.POST, entity, UserDTO.class);
        System.out.println(response2.getBody());
        return userRepository.save(new User(user)).toString();
    }

    public double getbalance(String email){
        return userRepository.findByEmail(email).get().getBalance();
    }
}
