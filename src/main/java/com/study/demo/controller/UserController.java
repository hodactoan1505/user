package com.study.demo.controller;

import com.study.demo.common.response.ResponseNormal;
import com.study.demo.dto.user.ChangePasswordRequest;
import com.study.demo.dto.user.ChangeRoleRequest;
import com.study.demo.dto.user.UserCreateRequest;
import com.study.demo.dto.user.UserDetailResponse;
import com.study.demo.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/")
    public ResponseEntity<ResponseNormal<Void>> create(
        @RequestBody final UserCreateRequest userCreateRequest
    ) throws Exception {
        userService.createUser(userCreateRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(
            ResponseNormal.createResponse("User created is success")
        );
    }

    @PostMapping("/{userId}/change-password/")
    public ResponseEntity<ResponseNormal<Void>> changePassword(
        @PathVariable final Long userId,
        @RequestBody final ChangePasswordRequest changePasswordRequest
    ) throws Exception {
        userService.changePassword(userId, changePasswordRequest);
        return ResponseEntity.ok().body(
            ResponseNormal.createResponse("Password changed is success")
        );
    }

    @PostMapping("/{userId}/change-roles")
    public ResponseEntity<ResponseNormal<Void>> changeRoles(
        @PathVariable final Long userId,
        @RequestBody final ChangeRoleRequest changeRoleRequest
    ) throws Exception {
        userService.changeRoles(userId, changeRoleRequest);
        return ResponseEntity.ok().body(
            ResponseNormal.createResponse("Changed role is success")
        );
    }

    @DeleteMapping("/{userId}/inactive/")
    public ResponseEntity<ResponseNormal<Void>> inactive(@PathVariable final Long userId) throws Exception {
        userService.inactive(userId);
        return ResponseEntity.ok().body(
            ResponseNormal.createResponse("User inactive is success")
        );
    }

    @GetMapping("/fetch-users/")
    public ResponseEntity<ResponseNormal<List<UserDetailResponse>>> fetchUserList() {
        final List<UserDetailResponse> userDetailResponseList = userService.fetchAllUsers();
        return ResponseEntity.ok().body(
            ResponseNormal.createResponse(userDetailResponseList, "Fetch user is success")
        );
    }

    @GetMapping("/{userId}/detail/")
    public ResponseEntity<ResponseNormal<UserDetailResponse>> detailUser(@PathVariable final Long userId) {
        final UserDetailResponse userDetailResponse = userService.fetchUser(userId);
        return ResponseEntity.ok().body(
            ResponseNormal.createResponse(userDetailResponse, "Fetch user is success")
        );
    }
}
