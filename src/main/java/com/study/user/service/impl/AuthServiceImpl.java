package com.study.user.service.impl;

import com.study.user.common.exception.BusinessException;
import com.study.user.controller.model.request.AuthRequest;
import com.study.user.controller.model.response.AuthResponse;
import com.study.user.enums.ErrorCode;
import com.study.user.model.view.UserLoginView;
import com.study.user.repository.UserRepository;
import com.study.user.service.AuthService;
import com.study.user.service.SecurityService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final SecurityService securityService;

    @Override
    public AuthResponse login(final AuthRequest authRequest) {
        final UserLoginView userLogin = userRepository.fetchUserByUsername(authRequest.username())
            .orElseThrow(() -> new BusinessException(ErrorCode.UNAUTHENTICATED));

        if (!securityService.matches(authRequest.password(), userLogin.getPassword())) {
            throw new BusinessException(ErrorCode.UNAUTHENTICATED);
        }

        final String accessToken = securityService.generateToken(userLogin.getUsername());

        return new AuthResponse(accessToken);
    }
}
