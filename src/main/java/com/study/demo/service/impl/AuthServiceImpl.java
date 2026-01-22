package com.study.demo.service.impl;

import com.study.demo.common.exception.BusinessException;
import com.study.demo.dto.auth.AuthRequest;
import com.study.demo.dto.auth.AuthResponse;
import com.study.demo.dto.user.UserLoginDto;
import com.study.demo.enums.ErrorCode;
import com.study.demo.repository.UserRepository;
import com.study.demo.service.AuthService;
import com.study.demo.service.SecurityService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final SecurityService securityService;

    @Override
    public AuthResponse login(final AuthRequest authRequest) {
        final UserLoginDto userLogin = userRepository.fetchUserByUsername(authRequest.username())
            .orElseThrow(() -> new BusinessException(ErrorCode.UNAUTHENTICATED));

        if (!securityService.matches(authRequest.password(), userLogin.getPassword())) {
            throw new BusinessException(ErrorCode.UNAUTHENTICATED);
        }

        final String accessToken = securityService.generateToken(userLogin.getUsername());

        return new AuthResponse(accessToken);
    }

}
