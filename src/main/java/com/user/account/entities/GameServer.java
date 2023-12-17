package com.user.account.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GameServer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long gameServerId;
    private String gameServerName;
    private String gameServerCreateAccountUrl;

    public GameServer(String gameServerName, String gameServerCreateAccountUrl) {
        this.gameServerName = gameServerName;
        this.gameServerCreateAccountUrl = gameServerCreateAccountUrl;
    }
}
