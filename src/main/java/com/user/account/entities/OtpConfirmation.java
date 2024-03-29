package com.user.account.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class OtpConfirmation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long otpId;
    @ManyToOne
    private User user;
    private String otp;
    private LocalDateTime timestamp;
    private LocalDateTime confirmAt;
    private LocalDateTime expireAt;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "amount_verification_id", referencedColumnName = "id")
    private AmountVerification amountVerification;

    public OtpConfirmation(User user, String otp, LocalDateTime timestamp, LocalDateTime confirmAt, LocalDateTime expireAt) {
        this.user = user;
        this.otp = otp;
        this.timestamp = timestamp;
        this.confirmAt = confirmAt;
        this.expireAt = expireAt;
    }

    public OtpConfirmation(User user, String otp, LocalDateTime timestamp, LocalDateTime expireAt) {
        this.user = user;
        this.otp = otp;
        this.timestamp = timestamp;
        this.expireAt = expireAt;
    }
}
