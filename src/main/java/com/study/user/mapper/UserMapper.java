package com.study.user.mapper;

import com.study.user.dto.UserDetailDto;
import com.study.user.model.view.UserDetailView;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserDetailDto toUserDetailDto(final UserDetailView userDetailView) {
        return UserDetailDto.builder()
            .id(userDetailView.getId())
            .username(userDetailView.getUsername())
            .active(userDetailView.isActive())
            .build();
    }
}
