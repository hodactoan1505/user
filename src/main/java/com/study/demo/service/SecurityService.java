package com.study.demo.service;

public interface SecurityService {

    String encode(final String password);

    boolean matches(final String password, final String hashedPassword);

    String generateToken(final String username);

    boolean isValidToken(final String token);

    String extractUsername(final String token);
}
