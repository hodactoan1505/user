package com.study.user.service.impl;

import com.study.user.common.exception.BusinessException;
import com.study.user.dto.ChangePasswordDto;
import com.study.user.dto.UserCreateDto;
import com.study.user.dto.UserDetailDto;
import com.study.user.mapper.UserMapper;
import com.study.user.model.UserEntity;
import com.study.user.model.view.UserDetailView;
import com.study.user.repository.UserRepository;
import com.study.user.service.UserService;
import com.study.user.util.PasswordUtil;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional
    @Override
    public void createUser(final UserCreateDto userCreateDto) throws Exception {
        final String username = userCreateDto.getUsername();
        boolean isExistUsername = userRepository.existsActiveUsername(username);

        if (isExistUsername) {
            throw new BusinessException("Username is exists");
        }

        final UserEntity userEntity = UserEntity.builder()
            .username(userCreateDto.getUsername())
            .password(PasswordUtil.encode(userCreateDto.getPassword()))
            .build();

        userRepository.save(userEntity);
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
    public void inactive(final Long userId) throws Exception {
        final UserEntity userEntity = userRepository.findById(userId)
            .orElseThrow(() -> new BusinessException("UserId %s is not found", userId));

        userEntity.inactive();
    }

    @Override
    public List<UserDetailDto> fetchAllUsers() {
        return userRepository.fetchAllUsers()
            .stream()
            .map(userMapper::toUserDetailDto)
            .toList();
    }

    @Override
    public UserDetailDto fetchUser(final Long userId) {
        final UserDetailView userDetailView = userRepository.fetchUser(userId)
            .orElseThrow(() -> new BusinessException("UserId %s is not found", userId));
        return userMapper.toUserDetailDto(userDetailView);
    }
}
