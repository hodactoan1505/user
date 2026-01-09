package com.study.user.service.impl;

import com.study.user.common.exception.BusinessException;
import com.study.user.config.CommonProperties;
import com.study.user.enums.ErrorCode;
import com.study.user.service.SecurityService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
@AllArgsConstructor
public class SecurityServiceImpl implements SecurityService {
    private final CommonProperties commonProperties;

    private final PasswordEncoder passwordEncoder;

    @Override
    public String encode(final String password) {
        if (StringUtils.isBlank(password)) {
            throw new BusinessException(ErrorCode.INVALID_PASSWORD_FORMAT);
        }

        return passwordEncoder.encode(password);
    }

    @Override
    public boolean matches(final String password, final String hashedPassword) {
        if (StringUtils.isBlank(password)) {
            return false;
        }

        return passwordEncoder.matches(password, hashedPassword);
    }

    @Override
    public String generateToken(final String username) {
        final SecretKey key = Keys.hmacShaKeyFor(commonProperties.jwt().secret().getBytes(StandardCharsets.UTF_8));
        return Jwts.builder()
            .subject(username)
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() + commonProperties.jwt().expirationMs()))
            .signWith(key)
            .compact();
    }
}
