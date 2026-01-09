package com.study.user.service;

import com.study.user.controller.model.request.ChangePasswordRequest;
import com.study.user.controller.model.request.ChangeRoleRequest;
import com.study.user.controller.model.request.UserCreateRequest;
import com.study.user.controller.model.response.UserDetailResponse;

import java.util.List;

public interface UserService {
    void createUser(final UserCreateRequest userCreateRequest) throws Exception;

    void changePassword(final Long userId, final ChangePasswordRequest changePasswordRequest) throws Exception;

    void changeRoles(final Long userId, final ChangeRoleRequest changeRoleRequest) throws Exception;

    void inactive(final Long userId) throws Exception;

    List<UserDetailResponse> fetchAllUsers();

    UserDetailResponse fetchUser(final Long userId);
}
