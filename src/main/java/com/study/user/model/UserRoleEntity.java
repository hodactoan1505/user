package com.study.user.model;

import com.study.user.model.base.UserRoleId;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Getter
@Table(name = "user_roles")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserRoleEntity {

    @EmbeddedId
    private UserRoleId id;

    @Column(name = "assigned_at", nullable = false)
    private Instant assignedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @MapsId("userId")
    private UserEntity userEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    @MapsId("roleId")
    private RoleEntity roleEntity;

    @Builder
    public UserRoleEntity(final UserEntity user, final RoleEntity role) {
        this.userEntity = user;
        this.roleEntity = role;
        this.id = new UserRoleId(user.getId(), role.getId());
        this.assignedAt = Instant.now();
    }

}
