package com.user.account.service;

import com.netflix.discovery.converters.Auto;
import com.user.account.dto.UserDTO;
import com.user.account.entities.AmountVerification;
import com.user.account.entities.GameServer;
import com.user.account.entities.OtpConfirmation;
import com.user.account.entities.User;
import com.user.account.payload.RechargeConfirmationPayload;
import com.user.account.repository.AmountVerificationRepository;
import com.user.account.repository.GameServerRepository;
import com.user.account.repository.OtpConfirmationRepository;
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

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private GameServerRepository gameServerRepository;
    @Autowired
    private OtpConfirmationRepository otpConfirmationRepository;
    @Autowired
    private AmountVerificationRepository amountVerificationRepository;
    @Autowired
    private OtpService otpService;
    @Autowired
    private EmailSender emailSender;
    Logger logger = LoggerFactory.getLogger(UserService.class);
//    public String createUser(UserDTO user){
//        RestTemplate restTemplate = new RestTemplate();
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Authorization","Bearer "+tokenService.getToken());
//        HttpEntity<String> entity = new HttpEntity(user ,headers);
//        List<GameServer> gameServerList = gameServerRepository.findAll();
//        for(GameServer serverList : gameServerList){
//            ResponseEntity<UserDTO> response = restTemplate.exchange(serverList.getGameServerCreateAccountUrl(), HttpMethod.POST, entity, UserDTO.class);
//            logger.info("Account created on "+serverList.getGameServerName()+" "+response.getBody().toString());
//        }
//
//        return userRepository.save(new User(user)).toString();
//    }

    public double getbalance(String email, String authorities){

        return userRepository.findByEmail(email).orElseGet(()->{
            return userRepository.save(new User(email,email,authorities,0.0));
        }).getBalance();
    }

    @Transactional
    public Long recharge(double amount, String userEmail, String email){
        if(amount<0){
            throw new IllegalStateException("Amount is not valid");
        }
        User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new UsernameNotFoundException("User not found"));
//        OtpConfirmation otpConfirmation = new OtpConfirmation(email)
        String otp = otpService.generateOtp();
        emailSender.rechargeEmail(email,userEmail,otp,amount);
        LocalDateTime currentTime = LocalDateTime.now();
        OtpConfirmation otpConfirmation = new OtpConfirmation(user,otp,currentTime,currentTime.plusMinutes(15));
        AmountVerification amountVerification = new AmountVerification(amount, otpConfirmation);
        otpConfirmation.setAmountVerification(amountVerification);
        amountVerificationRepository.save(amountVerification);
        OtpConfirmation savedOtpConfirmation = otpConfirmationRepository.save(otpConfirmation);

//        double balance = user.getBalance();
//        user.setBalance(balance+amount);
//        userRepository.save(user);
        return savedOtpConfirmation.getOtpId();
    }

    @Transactional
    public RechargeConfirmationPayload confirmRecharge(Long otpId, String otp){
        var otpObj = otpService.confirmOtp(otpId,otp);
        User user = otpObj.getUser();
        double balance = user.getBalance();
        double rechargeAmount = otpObj.getAmountVerification().getAmount();
        user.setBalance(balance+rechargeAmount);
        return new RechargeConfirmationPayload(user.getEmail(),user.getBalance(),rechargeAmount);
    }

    public List<String> getAllUserEmail() {
        return userRepository.findAll().stream().map(User::getEmail).collect(Collectors.toList());
    }
}
