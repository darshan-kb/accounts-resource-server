package com.user.account.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RechargeConfirmationPayload {
    private String user;
    private double amount;
    private double recharge;
}
