package io.mkth.registration.registration.repository;

import io.mkth.registration.registration.model.ConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {

    ConfirmationToken findByTokenId(String tokenId);
}
