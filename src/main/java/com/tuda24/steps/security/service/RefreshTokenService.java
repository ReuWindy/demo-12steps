package com.tuda24.steps.security.service;
import com.tuda24.steps.entity.RefreshToken;
import com.tuda24.steps.entity.User;

public interface RefreshTokenService {

    RefreshToken create(User user);

    RefreshToken verify(String refreshToken);

    RefreshToken rotate(RefreshToken oldToken);

    void revoke(String refreshToken);
}
