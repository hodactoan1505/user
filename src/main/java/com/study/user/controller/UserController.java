package com.study.user.controller;

import com.study.user.common.reponse.ResponseNormal;
import com.study.user.dto.ChangePasswordDto;
import com.study.user.dto.UserCreateDto;
import com.study.user.dto.UserDetailDto;
import com.study.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    // TODO: add role
    private final UserService userService;

    @PostMapping("/")
    public ResponseEntity<ResponseNormal<Void>> create(@RequestBody final UserCreateDto userCreateDto) throws Exception {
        userService.createUser(userCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(
            ResponseNormal.createResponse("User created is success")
        );
    }

    @PutMapping("/{userId}/change-password/")
    public ResponseEntity<ResponseNormal<Void>> changePassword(
        @PathVariable final Long userId,
        @RequestBody final ChangePasswordDto changePasswordDto
    ) throws Exception {
        userService.changePassword(userId, changePasswordDto);
        return ResponseEntity.ok().body(
            ResponseNormal.createResponse("Password changed is success")
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
    public ResponseEntity<ResponseNormal<List<UserDetailDto>>> fetchUserList() {
        final List<UserDetailDto> userDetailDtoList = userService.fetchAllUsers();
        return ResponseEntity.ok().body(
            ResponseNormal.createResponse(userDetailDtoList, "Fetch user is success")
        );
    }

    @GetMapping("/{userId}/detail/")
    public ResponseEntity<ResponseNormal<UserDetailDto>> detailUser(@PathVariable final Long userId) {
        final UserDetailDto userDetailDto = userService.fetchUser(userId);
        return ResponseEntity.ok().body(
            ResponseNormal.createResponse(userDetailDto, "Fetch user is success")
        );
    }
}
