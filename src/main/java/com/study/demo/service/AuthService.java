package com.study.demo.service;

import com.study.demo.dto.auth.AuthRequest;
import com.study.demo.dto.auth.AuthResponse;

public interface AuthService {
    AuthResponse login(final AuthRequest authRequest);
}
