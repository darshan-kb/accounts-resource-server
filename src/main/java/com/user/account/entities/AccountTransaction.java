package com.user.account.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
@Builder
@Entity
public class AccountTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long transactionId;
    private String remark;
    private LocalDateTime timeStamp;
    private double withdrawalAmount;
    private double depositAmount;
    private double balance;
    @ManyToOne
    private User user;

}
