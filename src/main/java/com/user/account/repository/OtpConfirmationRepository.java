package com.user.account.repository;

import com.user.account.entities.OtpConfirmation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OtpConfirmationRepository extends JpaRepository<OtpConfirmation, Long> {
}
