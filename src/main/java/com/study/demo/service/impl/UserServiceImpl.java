package com.study.demo.service.impl;

import com.study.demo.common.exception.BusinessException;
import com.study.demo.dto.role.RoleDto;
import com.study.demo.dto.user.*;
import com.study.demo.enums.ErrorCode;
import com.study.demo.model.RoleEntity;
import com.study.demo.model.UserEntity;
import com.study.demo.repository.RoleRepository;
import com.study.demo.repository.UserRepository;
import com.study.demo.repository.UserRoleRepository;
import com.study.demo.service.SecurityService;
import com.study.demo.service.UserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional(rollbackOn = Exception.class)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final SecurityService securityService;

    @Override
    public void createUser(final UserCreateRequest userCreateRequest) throws Exception {
        final String username = userCreateRequest.username();
        boolean isExistUsername = userRepository.checkExistsUsername(username);

        if (isExistUsername) {
            throw new BusinessException(ErrorCode.USER_EXISTED);
        }

        final List<RoleEntity> roleEntityList = roleRepository.findByCodeIn(userCreateRequest.roleList());
        if (userCreateRequest.roleList().size() != roleEntityList.size()) {
            throw new BusinessException(ErrorCode.DATA_NOT_FOUND);
        }

        final UserEntity userEntity = UserEntity.builder()
            .username(userCreateRequest.username())
            .password(securityService.encode(userCreateRequest.password()))
            .build();

        userRepository.save(userEntity);
        roleEntityList.forEach(userEntity::assignRole);
    }

    @Override
    public void changePassword(final Long userId, final ChangePasswordRequest changePasswordRequest) throws Exception {
        final UserEntity userEntity = userRepository.findById(userId)
            .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        userEntity.changePassword(securityService.encode(changePasswordRequest.newPassword()));
    }

    @Override
    public void changeRoles(final Long userId, final ChangeRoleRequest changeRoleRequest) throws Exception {
        final UserEntity userEntity = userRepository.findById(userId)
            .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        final List<RoleEntity> roleEntityList = roleRepository.findByCodeIn(changeRoleRequest.roleList());
        if (changeRoleRequest.roleList().size() != roleEntityList.size()) {
            throw new BusinessException(ErrorCode.DATA_NOT_FOUND);
        }

        userEntity.clearRoles();
        roleEntityList.forEach(userEntity::assignRole);
    }

    @Override
    public void inactive(final Long userId) throws Exception {
        final UserEntity userEntity = userRepository.findById(userId)
            .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        userEntity.inactive();
    }

    @Override
    public List<UserDetailResponse> fetchAllUsers() {
        final List<UserDetailDto> userDetailDtoList = userRepository.fetchAllUsers();

        final List<Long> userIdList = userDetailDtoList
            .stream()
            .map(UserDetailDto::getId)
            .toList();

        final List<UserRoleRowDto> userRoleRowDtoList = userRoleRepository.fetchRolesByUserIds(userIdList);

        final Map<Long, List<RoleDto>> roleMap = userRoleRowDtoList
            .stream()
            .collect(Collectors.groupingBy(
                UserRoleRowDto::getUserId,
                Collectors.mapping(
                    role -> new RoleDto(role.getCode(), role.getName()),
                    Collectors.toList()
                )
            ));

        return userDetailDtoList.stream()
            .map(user -> UserDetailResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .active(user.isActive())
                .roleDtoList(roleMap.get(user.getId()))
                .build())
            .toList();
    }

    @Override
    public UserDetailResponse fetchUser(final Long userId) {
        final UserDetailDto userDetailDto = userRepository.fetchUser(userId)
            .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        final List<UserRoleRowDto> userRoleRowDtoList =
            userRoleRepository.fetchRolesByUserIds(List.of(userDetailDto.getId()));

        final Map<Long, List<RoleDto>> roleMap = userRoleRowDtoList
            .stream()
            .collect(Collectors.groupingBy(
                UserRoleRowDto::getUserId,
                Collectors.mapping(
                    role -> new RoleDto(role.getCode(), role.getName()),
                    Collectors.toList()
                )
            ));

        return UserDetailResponse.builder()
            .id(userDetailDto.getId())
            .username(userDetailDto.getUsername())
            .active(userDetailDto.isActive())
            .roleDtoList(roleMap.get(userDetailDto.getId()))
            .build();
    }
}
