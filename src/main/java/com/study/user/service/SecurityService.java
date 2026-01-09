package com.study.user.service;

public interface SecurityService {

    String encode(final String password);

    boolean matches(final String password, final String hashedPassword);

    String generateToken(final String username);
}
