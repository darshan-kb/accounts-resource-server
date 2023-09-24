package com.user.account.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Client {
    private int id;
    private String clientname;
    private String password;
    private String authority;
}
