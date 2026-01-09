package com.study.user.mapper;

import com.study.user.controller.model.response.UserDetailResponse;
import com.study.user.controller.model.response.part.RolePart;
import com.study.user.model.view.UserDetailView;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapper {
    public UserDetailResponse toUserDetailDto(final UserDetailView userDetailView, final List<RolePart> rolePartList) {
        return UserDetailResponse.builder()
            .id(userDetailView.getId())
            .username(userDetailView.getUsername())
            .active(userDetailView.isActive())
            .rolePartList(rolePartList)
            .build();
    }
}
