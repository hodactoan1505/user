package com.study.demo.controller;

import com.study.demo.common.response.ResponseNormal;
import com.study.demo.dto.auth.AuthRequest;
import com.study.demo.dto.auth.AuthResponse;
import com.study.demo.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login/")
    public ResponseEntity<ResponseNormal<AuthResponse>> login(@RequestBody final AuthRequest authRequest) {
        final AuthResponse authResponse = authService.login(authRequest);
        return ResponseEntity.ok().body(ResponseNormal.createResponse(authResponse, "Login success"));
    }
}
