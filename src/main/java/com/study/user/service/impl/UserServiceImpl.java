package com.study.user.service.impl;

import com.study.user.common.exception.BusinessException;
import com.study.user.dto.*;
import com.study.user.mapper.UserMapper;
import com.study.user.model.RoleEntity;
import com.study.user.model.UserEntity;
import com.study.user.model.view.UserDetailView;
import com.study.user.model.view.UserRoleRowView;
import com.study.user.repository.RoleRepository;
import com.study.user.repository.UserRepository;
import com.study.user.repository.UserRoleRepository;
import com.study.user.service.UserService;
import com.study.user.util.PasswordUtil;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final UserMapper userMapper;

    @Transactional
    @Override
    public void createUser(final UserCreateDto userCreateDto) throws Exception {
        final String username = userCreateDto.getUsername();
        boolean isExistUsername = userRepository.checkExistsUsername(username);

        if (isExistUsername) {
            throw new BusinessException("Username is exists");
        }

        final List<RoleEntity> roleEntityList = roleRepository.findByCodeIn(userCreateDto.getRoleList());
        if (userCreateDto.getRoleList().size() != roleEntityList.size()) {
            throw new BusinessException("Role is not found");
        }

        final UserEntity userEntity = UserEntity.builder()
            .username(userCreateDto.getUsername())
            .password(PasswordUtil.encode(userCreateDto.getPassword()))
            .build();

        userRepository.save(userEntity);
        roleEntityList.forEach(userEntity::assignRole);
    }

    @Transactional
    @Override
    public void changePassword(final Long userId, final ChangePasswordDto changePasswordDto) throws Exception {
        final UserEntity userEntity = userRepository.findById(userId)
            .orElseThrow(() -> new BusinessException("UserId %s is not found", userId));

        userEntity.changePassword(changePasswordDto.getNewPassword());
    }

    @Transactional
    @Override
    public void changeRoles(final Long userId, final ChangeRoleDto changeRoleDto) throws Exception {
        final UserEntity userEntity = userRepository.findById(userId)
            .orElseThrow(() -> new BusinessException("UserId %s is not found", userId));

        final List<RoleEntity> roleEntityList = roleRepository.findByCodeIn(changeRoleDto.getRoleList());
        if (changeRoleDto.getRoleList().size() != roleEntityList.size()) {
            throw new BusinessException("Role is not found");
        }

        userEntity.clearRoles();
        roleEntityList.forEach(userEntity::assignRole);
    }

    @Transactional
    @Override
    public void inactive(final Long userId) throws Exception {
        final UserEntity userEntity = userRepository.findById(userId)
            .orElseThrow(() -> new BusinessException("UserId %s is not found", userId));

        userEntity.inactive();
    }

    @Override
    public List<UserDetailDto> fetchAllUsers() {
        final List<UserDetailView> userDetailViewList = userRepository.fetchAllUsers();

        final List<Long> userIdList = userDetailViewList
            .stream()
            .map(UserDetailView::getId)
            .toList();

        final List<UserRoleRowView> userRoleRowViewList = userRoleRepository.fetchRolesByUserIds(userIdList);

        final Map<Long, List<RoleDto>> roleMap = userRoleRowViewList
            .stream()
            .collect(Collectors.groupingBy(
                UserRoleRowView::getUserId,
                Collectors.mapping(
                    role -> new RoleDto(role.getCode(), role.getName()),
                    Collectors.toList()
                )
            ));

        return userDetailViewList.stream()
            .map(user -> userMapper.toUserDetailDto(user, roleMap.get(user.getId())))
            .toList();
    }

    @Override
    public UserDetailDto fetchUser(final Long userId) {
        final UserDetailView userDetailView = userRepository.fetchUser(userId)
            .orElseThrow(() -> new BusinessException("UserId %s is not found", userId));

        final List<UserRoleRowView> userRoleRowViewList =
            userRoleRepository.fetchRolesByUserIds(List.of(userDetailView.getId()));

        final Map<Long, List<RoleDto>> roleMap = userRoleRowViewList
            .stream()
            .collect(Collectors.groupingBy(
                UserRoleRowView::getUserId,
                Collectors.mapping(
                    role -> new RoleDto(role.getCode(), role.getName()),
                    Collectors.toList()
                )
            ));

        return userMapper.toUserDetailDto(userDetailView, roleMap.get(userDetailView.getId()));
    }
}
