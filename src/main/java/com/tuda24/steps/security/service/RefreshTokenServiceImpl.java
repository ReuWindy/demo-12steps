package com.tuda24.steps.security.service;

import com.tuda24.steps.entity.RefreshToken;
import com.tuda24.steps.entity.User;
import com.tuda24.steps.exception.NotFoundException;
import com.tuda24.steps.repository.RefreshToken.RefreshTokenRepository;
import com.tuda24.steps.security.utils.TokenHashUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    private final long REFRESH_TOKEN_DAYS = 7;

    @Override
    public RefreshToken create(User user) {

        String rawToken = UUID.randomUUID().toString();
        String tokenHash = TokenHashUtil.hashToken(rawToken);

        RefreshToken refreshToken = RefreshToken.builder()
                .tokenHash(tokenHash)
                .user(user)
                .revoked(false)
                .expiryDate(LocalDateTime.now().plusDays(REFRESH_TOKEN_DAYS))
                .build();

        RefreshToken saved = refreshTokenRepository.save(refreshToken);

        saved.setTokenHash(rawToken);

        return saved;
    }

    @Override
    public RefreshToken verify(String refreshToken) {

        String hash = TokenHashUtil.hashToken(refreshToken);

        RefreshToken token = refreshTokenRepository.findByTokenHash(hash)
                .orElseThrow(() -> new NotFoundException("Refresh token not found"));

        if (token.isRevoked()) {
            throw new RuntimeException("Refresh token revoked");
        }

        if (token.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Refresh token expired");
        }

        return token;
    }

    @Override
    public RefreshToken rotate(RefreshToken oldToken) {

        oldToken.setRevoked(true);
        refreshTokenRepository.save(oldToken);

        return create(oldToken.getUser());
    }

    @Override
    public void revoke(String refreshToken) {

        String hash = TokenHashUtil.hashToken(refreshToken);

        RefreshToken token = refreshTokenRepository.findByTokenHash(hash)
                .orElseThrow(() -> new NotFoundException("Refresh token not found"));

        token.setRevoked(true);
        refreshTokenRepository.save(token);
    }
}