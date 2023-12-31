package com.user.account.service;

import com.user.account.dto.UserDTO;
import com.user.account.entities.GameServer;
import com.user.account.entities.User;
import com.user.account.repository.GameServerRepository;
import com.user.account.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private GameServerRepository gameServerRepository;
    Logger logger = LoggerFactory.getLogger(UserService.class);
    public String createUser(UserDTO user){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization","Bearer "+tokenService.getToken());
        HttpEntity<String> entity = new HttpEntity(user ,headers);
        List<GameServer> gameServerList = gameServerRepository.findAll();
        for(GameServer serverList : gameServerList){
            ResponseEntity<UserDTO> response = restTemplate.exchange(serverList.getGameServerCreateAccountUrl(), HttpMethod.POST, entity, UserDTO.class);
            logger.info("Account created on "+serverList.getGameServerName()+" "+response.getBody().toString());
        }

        return userRepository.save(new User(user)).toString();
    }

    public double getbalance(String email){
        return userRepository.findByEmail(email).get().getBalance();
    }

    @Transactional
    public double recharge(double amount, String email){
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
