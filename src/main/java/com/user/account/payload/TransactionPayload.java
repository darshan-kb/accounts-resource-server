package com.user.account.payload;

import lombok.Data;

@Data
public class TransactionPayload {

    private String email;
    private double claimAmount;
    private String id;
}
