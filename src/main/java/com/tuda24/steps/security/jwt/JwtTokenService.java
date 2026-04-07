package com.tuda24.steps.security.jwt;

import com.tuda24.steps.entity.RefreshToken;
import com.tuda24.steps.entity.User;
import com.tuda24.steps.exception.NotFoundException;
import com.tuda24.steps.repository.User.UserRepository;
import com.tuda24.steps.security.dto.LoginRequest;
import com.tuda24.steps.security.dto.LoginResponse;
import com.tuda24.steps.security.dto.LogoutResponse;
import com.tuda24.steps.security.dto.RefreshTokenRequest;
import com.tuda24.steps.security.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtTokenService {

    private final JwtTokenManager jwtTokenManager;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RefreshTokenService refreshTokenService;

    public LoginResponse login(LoginRequest loginRequest) {

        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
        } catch (AuthenticationException e) {
            throw new RuntimeException("Wrong username or password!");
        }

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User not found"));

        if (!user.isActive()) {
            throw new RuntimeException("User is inactive");
        }

        String accessToken = jwtTokenManager.generateAccessToken(user);

        RefreshToken refreshToken = refreshTokenService.create(user);

        return new LoginResponse(
                accessToken,
                refreshToken.getTokenHash(),
                user.getUsername(),
                user.getId()
        );
    }

    public LoginResponse refreshToken(RefreshTokenRequest request) {

        RefreshToken oldToken = refreshTokenService.verify(request.getRefreshToken());

        String newAccessToken = jwtTokenManager.generateAccessToken(oldToken.getUser());

        RefreshToken newRefreshToken = refreshTokenService.rotate(oldToken);

        return new LoginResponse(
                newAccessToken,
                newRefreshToken.getTokenHash(),
                oldToken.getUser().getUsername(),
                oldToken.getUser().getId()
        );
    }

    public LogoutResponse logout(RefreshTokenRequest request) {

        refreshTokenService.revoke(request.getRefreshToken());

        return new LogoutResponse("Logout successfully");
    }
}