package com.study.user.service;

import com.study.user.dto.ChangePasswordDto;
import com.study.user.dto.UserCreateDto;
import com.study.user.dto.UserDetailDto;

import java.util.List;

public interface UserService {
    void createUser(final UserCreateDto userCreateDto) throws Exception;

    void changePassword(final Long userId, final ChangePasswordDto changePasswordDto) throws Exception;

    void inactive(final Long userId) throws Exception;

    List<UserDetailDto> fetchAllUsers();

    UserDetailDto fetchUser(final Long userId);
}
