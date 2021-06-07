package io.mkth.registration.registration.service;

import io.mkth.registration.registration.model.ConfirmationToken;
import io.mkth.registration.registration.repository.ConfirmationTokenRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ConfirmationTokenService {

    private final ConfirmationTokenRepository confirmationTokenRepository;

    public ConfirmationTokenService(ConfirmationTokenRepository confirmationTokenRepository) {
        this.confirmationTokenRepository = confirmationTokenRepository;
    }

    public ConfirmationToken getConfirmationToken(String token) {
        //TODO: treatment exceptions not found and expired token
        return confirmationTokenRepository.findByTokenId(token);
    }

    public ConfirmationToken save(ConfirmationToken confirmationToken) {
        return confirmationTokenRepository.save(confirmationToken);
    }

    public ConfirmationToken setConfirmDate(ConfirmationToken confirmationToken) {
        confirmationToken.setConfirmedAt(LocalDateTime.now());
        return save(confirmationToken);
    }
}
