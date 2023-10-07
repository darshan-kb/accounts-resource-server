package com.user.account.entities;

import com.user.account.dto.UserDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String username;
    private String email;
    private String role;
    private double balance;
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<AccountTransaction> accountTransaction;

    public User(String username, String email, String role){
        this.username=username;
        this.email=email;
        this.role=role;
    }

    public User(UserDTO user){
        this.username=user.getUsername();
        this.email=user.getEmail();
        this.role=user.getRole();
    }
}
