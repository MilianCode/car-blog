package com.miliancode.carblog.services.token;

import com.miliancode.carblog.repo.ConfirmationTokenRepository;
import com.miliancode.carblog.repo.PasswordTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PasswordRecoveryTokenService {
    private final PasswordTokenRepository passwordTokenRepository;

    public void saveConfirmationToken(ConfirmationToken token) {
        passwordTokenRepository.save(token);
    }

    public Optional<PasswordRecoveryToken> getToken(String token) {
        return passwordTokenRepository.findByToken(token);
    }

    public int setConfirmedAt(String token) {
        return passwordTokenRepository.updateConfirmedAt(
                token, LocalDateTime.now());
    }
}
