package com.study.demo.service;

import com.study.demo.dto.user.ChangePasswordRequest;
import com.study.demo.dto.user.ChangeRoleRequest;
import com.study.demo.dto.user.UserCreateRequest;
import com.study.demo.dto.user.UserDetailResponse;

import java.util.List;

public interface UserService {
    void createUser(final UserCreateRequest userCreateRequest) throws Exception;

    void changePassword(final Long userId, final ChangePasswordRequest changePasswordRequest) throws Exception;

    void changeRoles(final Long userId, final ChangeRoleRequest changeRoleRequest) throws Exception;

    void inactive(final Long userId) throws Exception;

    List<UserDetailResponse> fetchAllUsers();

    UserDetailResponse fetchUser(final Long userId);
}
