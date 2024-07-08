package com.user.account.service;

import com.user.account.entities.AmountVerification;
import com.user.account.entities.OtpConfirmation;
import com.user.account.entities.User;
import com.user.account.exception.UserNotFoundException;
import com.user.account.repository.AmountVerificationRepository;
import com.user.account.repository.OtpConfirmationRepository;
import com.user.account.repository.UserRepository;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private OtpService otpService;
    @Mock
    private EmailSenderService emailSenderService;
    @Mock
    private AmountVerificationRepository amountVerificationRepository;
    @Mock
    private OtpConfirmationRepository otpConfirmationRepository;

    @Test
    void getbalance_UserFound() {
        String email = "darshanbehere@gmail.com";
        User user = User.builder()
                .id(1)
                .username(email)
                .role("ROLE_USER")
                .balance(5500.0)
                .build();
        when(userRepository.findByEmail(any())).thenReturn(Optional.ofNullable(user));
        Double balance = userService.getbalance("arshanbehere@gmail.com","xyz");

        assertThat(balance).isEqualTo(5500.0);
    }

    @Test
    void getbalance_UserNotFound() {
        String email = "darshanbehere@gmail.com";
        User user = User.builder()
                .id(1)
                .username(email)
                .role("ROLE_USER")
                .balance(0.0)
                .build();
        when(userRepository.findByEmail(any())).thenReturn(Optional.ofNullable(null));
        when(userRepository.save(any())).thenReturn(user);
        Double balance = userService.getbalance("arshanbehere@gmail.com","xyz");

        assertThat(balance).isEqualTo(0.0);
    }

    @Test
    void recharge(){
        String email = "darshanbehere@gmail.com";
        String userEmail = "a@g.com";
        double amount = 10.0;
        User user = User.builder()
                .id(1)
                .username(userEmail)
                .role("ROLE_USER")
                .balance(0.0)
                .build();
        String otp = "145821";
        AmountVerification amountVerification = AmountVerification.builder()
                .amount(10)
                .id(1).build();
        OtpConfirmation otpConfirmation = OtpConfirmation.builder()
                .otpId(1)
                .confirmAt(LocalDateTime.of(LocalDate.of(2024,2,17), LocalTime.of(12,0,0)))
                .otp("123")
                .timestamp(LocalDateTime.of(LocalDate.of(2024,2,17), LocalTime.of(12,0,0)))
                .expireAt(LocalDateTime.of(LocalDate.of(2024,2,17), LocalTime.of(12,30,0)))
                .build();
        when(userRepository.findByEmail(any())).thenReturn(Optional.ofNullable(user));
        when(otpService.generateOtp()).thenReturn(otp);
        when(amountVerificationRepository.save(any())).thenReturn(amountVerification);
        when(otpConfirmationRepository.save(any())).thenReturn(otpConfirmation);

        Long otpId = userService.recharge(10.0,userEmail,email);

        assertThat(otpId).isNotNull();
        assertThat(otpId).isEqualTo(1);

        verify(emailSenderService).rechargeEmail(eq(email), eq(userEmail), eq(otp), eq(amount));
        verify(amountVerificationRepository).save(any(AmountVerification.class));
        verify(otpConfirmationRepository).save(any(OtpConfirmation.class));
    }

    @Test
    void recharge_UserNotFound() {
        when(userRepository.findByEmail("a@g.com")).thenReturn(Optional.ofNullable(null));
        assertThatThrownBy(()->userService.recharge(0.0,"a@g.com",any())).isInstanceOf(UserNotFoundException.class);
    }

    @Test
    void recharge_IsLessThanZero() {
        assertThatThrownBy(()->userService.recharge(-1,"d@g.com","k@g.com"))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Amount is not valid");
    }

    @Test
    void confirmRecharge() {
    }

    @Test
    void getAllUserEmail() {
        User u1 = User.builder()
                .balance(100.0)
                .role("ROLE_USER")
                .email("a@gmail.com")
                .username("a@gmail.com")
                .build();
        User u2 = User.builder()
                .balance(500.0)
                .role("ROLE_USER")
                .email("b@gmail.com")
                .username("b@gmail.com")
                .build();
        User u3 = User.builder()
                .balance(200.0)
                .role("ROLE_USER")
                .email("c@gmail.com")
                .username("c@gmail.com")
                .build();

        List<User> userList = List.of(u1,u2,u3);

        when(userRepository.findAll()).thenReturn(userList);

        List<String> emailList = userService.getAllUserEmail();

        assertThat(emailList).isNotNull();
        assertThat(emailList).hasSize(3);
        assertThat(emailList.get(1)).isEqualTo("b@gmail.com");
    }
}