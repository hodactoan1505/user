package com.study.user.service.impl;

import com.study.user.common.exception.BusinessException;
import com.study.user.controller.model.request.ChangePasswordRequest;
import com.study.user.controller.model.request.ChangeRoleRequest;
import com.study.user.controller.model.request.UserCreateRequest;
import com.study.user.controller.model.response.UserDetailResponse;
import com.study.user.controller.model.response.part.RolePart;
import com.study.user.enums.ErrorCode;
import com.study.user.mapper.UserMapper;
import com.study.user.model.RoleEntity;
import com.study.user.model.UserEntity;
import com.study.user.model.view.UserDetailView;
import com.study.user.model.view.UserRoleRowView;
import com.study.user.repository.RoleRepository;
import com.study.user.repository.UserRepository;
import com.study.user.repository.UserRoleRepository;
import com.study.user.service.SecurityService;
import com.study.user.service.UserService;
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
    private final UserMapper userMapper;
    private final SecurityService securityService;

    @Override
    public void createUser(final UserCreateRequest userCreateRequest) throws Exception {
        final String username = userCreateRequest.getUsername();
        boolean isExistUsername = userRepository.checkExistsUsername(username);

        if (isExistUsername) {
            throw new BusinessException(ErrorCode.USER_EXISTED);
        }

        final List<RoleEntity> roleEntityList = roleRepository.findByCodeIn(userCreateRequest.getRoleList());
        if (userCreateRequest.getRoleList().size() != roleEntityList.size()) {
            throw new BusinessException(ErrorCode.DATA_NOT_FOUND);
        }

        final UserEntity userEntity = UserEntity.builder()
            .username(userCreateRequest.getUsername())
            .password(securityService.encode(userCreateRequest.getPassword()))
            .build();

        userRepository.save(userEntity);
        roleEntityList.forEach(userEntity::assignRole);
    }

    @Override
    public void changePassword(final Long userId, final ChangePasswordRequest changePasswordRequest) throws Exception {
        final UserEntity userEntity = userRepository.findById(userId)
            .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        userEntity.changePassword(securityService.encode(changePasswordRequest.getNewPassword()));
    }

    @Override
    public void changeRoles(final Long userId, final ChangeRoleRequest changeRoleRequest) throws Exception {
        final UserEntity userEntity = userRepository.findById(userId)
            .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        final List<RoleEntity> roleEntityList = roleRepository.findByCodeIn(changeRoleRequest.getRoleList());
        if (changeRoleRequest.getRoleList().size() != roleEntityList.size()) {
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
        final List<UserDetailView> userDetailViewList = userRepository.fetchAllUsers();

        final List<Long> userIdList = userDetailViewList
            .stream()
            .map(UserDetailView::getId)
            .toList();

        final List<UserRoleRowView> userRoleRowViewList = userRoleRepository.fetchRolesByUserIds(userIdList);

        final Map<Long, List<RolePart>> roleMap = userRoleRowViewList
            .stream()
            .collect(Collectors.groupingBy(
                UserRoleRowView::getUserId,
                Collectors.mapping(
                    role -> new RolePart(role.getCode(), role.getName()),
                    Collectors.toList()
                )
            ));

        return userDetailViewList.stream()
            .map(user -> userMapper.toUserDetailDto(user, roleMap.get(user.getId())))
            .toList();
    }

    @Override
    public UserDetailResponse fetchUser(final Long userId) {
        final UserDetailView userDetailView = userRepository.fetchUser(userId)
            .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        final List<UserRoleRowView> userRoleRowViewList =
            userRoleRepository.fetchRolesByUserIds(List.of(userDetailView.getId()));

        final Map<Long, List<RolePart>> roleMap = userRoleRowViewList
            .stream()
            .collect(Collectors.groupingBy(
                UserRoleRowView::getUserId,
                Collectors.mapping(
                    role -> new RolePart(role.getCode(), role.getName()),
                    Collectors.toList()
                )
            ));

        return userMapper.toUserDetailDto(userDetailView, roleMap.get(userDetailView.getId()));
    }
}
