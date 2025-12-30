package com.study.user.model;

import com.study.user.model.base.BaseEntity;
import com.study.user.util.PasswordUtil;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username", nullable = false, unique = true, length = 100)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @OneToMany(
        mappedBy = "userEntity",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private Set<UserRoleEntity> userRoleEntitySet = new HashSet<>();

    @Builder
    public UserEntity(final String username, final String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof UserEntity that)) return false;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public void changePassword(final String password) {
        this.password = PasswordUtil.encode(password);
    }

    public void inactive() {
        this.delFlg = true;
        this.deletedAt = Instant.now();
    }

    public void assignRole(final RoleEntity role) {
        boolean exists = userRoleEntitySet.stream()
            .anyMatch(ur -> Objects.equals(ur.getRoleEntity().getId(), role.getId()));

        if (exists) {
            return;
        }

        final UserRoleEntity userRole = UserRoleEntity.builder()
            .user(this)
            .role(role)
            .build();

        userRoleEntitySet.add(userRole);
    }

    public void clearRoles() {
        this.userRoleEntitySet.clear();
    }
}
