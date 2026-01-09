package com.study.user.service;

import com.study.user.controller.model.request.AuthRequest;
import com.study.user.controller.model.response.AuthResponse;

public interface AuthService {
    AuthResponse login(final AuthRequest authRequest);
}
