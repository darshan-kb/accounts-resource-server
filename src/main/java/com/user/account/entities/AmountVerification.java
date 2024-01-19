package com.user.account.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class AmountVerification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private double amount;
    @OneToOne(mappedBy = "amountVerification")
    private OtpConfirmation otpConfirmation;

    public AmountVerification(double amount, OtpConfirmation otpConfirmation) {
        this.amount = amount;
        this.otpConfirmation = otpConfirmation;
    }
}
