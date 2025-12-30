package com.study.user.mapper;

import com.study.user.dto.RoleDto;
import com.study.user.dto.UserDetailDto;
import com.study.user.model.view.UserDetailView;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapper {
    public UserDetailDto toUserDetailDto(final UserDetailView userDetailView, final List<RoleDto> roleDtoList) {
        return UserDetailDto.builder()
            .id(userDetailView.getId())
            .username(userDetailView.getUsername())
            .active(userDetailView.isActive())
            .roleDtoList(roleDtoList)
            .build();
    }
}
