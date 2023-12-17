package com.user.account.repository;

import com.user.account.entities.GameServer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameServerRepository extends JpaRepository<GameServer,Long> {
}
