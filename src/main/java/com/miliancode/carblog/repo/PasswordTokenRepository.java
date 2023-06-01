package com.miliancode.carblog.repo;

import com.miliancode.carblog.services.token.ConfirmationToken;
import com.miliancode.carblog.services.token.PasswordRecoveryToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface PasswordTokenRepository extends JpaRepository<ConfirmationToken, Long> {

    Optional<PasswordRecoveryToken> findByToken(String token);

    @Transactional
    @Modifying
    @Query("UPDATE PasswordRecoveryToken c " +
            "SET c.confirmedAt = ?2 " +
            "WHERE c.token = ?1")
    int updateConfirmedAt(String token, LocalDateTime confirmedAt);

}