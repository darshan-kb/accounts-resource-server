package com.user.account.service;

import com.bastiaanjansen.otp.HOTPGenerator;
import com.bastiaanjansen.otp.SecretGenerator;
import com.user.account.entities.OtpConfirmation;
import com.user.account.repository.OtpConfirmationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class OtpService {
    @Autowired
    OtpConfirmationRepository otpConfirmationRepository;
    public String generateOtp(){
        return OTP(6);
    }
    public String OTP(int len) {
//        System.out.println("Generating OTP using random() : ");
//        System.out.print("You OTP is : ");
        String numbers = "0123456789";
        Random rndm_method = new Random();
        char[] otp = new char[len];
        for (int i = 0; i < len; i++) {
            otp[i] = numbers.charAt(rndm_method.nextInt(numbers.length()));
        }
        return new String(otp);
    }

    public OtpConfirmation confirmOtp(Long otpId, String otp){
        var otpConfirmation = otpConfirmationRepository.findById(otpId);
        if(!otpConfirmation.isPresent()||otpConfirmation.get().getOtp()!=otp)
            throw new IllegalStateException("Invalid OTP");

        return otpConfirmation.get();
    }
}
