package com.user.account.entities;

import com.user.account.dto.UserDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String username;
    private String email;
    private String role;
    private double balance;
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<AccountTransaction> accountTransactions;
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<OtpConfirmation> otpConfirmations;

    public User(String username, String email, String role, double balance){
        this.username=username;
        this.email=email;
        this.role=role;
        this.balance=balance;
    }

    public User(UserDTO user){
        this.username=user.getUsername();
        this.email=user.getEmail();
        this.role=user.getRole();
    }
}
