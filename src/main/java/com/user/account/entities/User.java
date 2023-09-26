package com.user.account.entities;

import com.user.account.dto.UserDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
