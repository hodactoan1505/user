package com.study.demo.service.impl;

import com.study.demo.dto.auth.CustomUserDetail;
import com.study.demo.dto.user.UserLoginDto;
import com.study.demo.dto.user.UserRoleRowDto;
import com.study.demo.enums.ErrorCode;
import com.study.demo.repository.UserRepository;
import com.study.demo.repository.UserRoleRepository;
import lombok.AllArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    @Override
    @NonNull
    public UserDetails loadUserByUsername(final @NonNull String username) throws UsernameNotFoundException {
        final UserLoginDto userLoginDto = userRepository.fetchUserByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException(ErrorCode.USER_NOT_FOUND.getMessage()));

        final List<UserRoleRowDto> userRoleRowDtoList =
            userRoleRepository.fetchRolesByUserIds(List.of(userLoginDto.getId()));

        return new CustomUserDetail(
            userLoginDto,
            userRoleRowDtoList
        );
    }
}
