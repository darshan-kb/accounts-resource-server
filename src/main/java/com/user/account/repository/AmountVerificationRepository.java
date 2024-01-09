package com.user.account.repository;

import com.user.account.entities.AmountVerification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AmountVerificationRepository extends JpaRepository<AmountVerification, Long> {
}
