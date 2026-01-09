package com.study.user.repository;

import com.study.user.model.UserRoleEntity;
import com.study.user.model.base.UserRoleId;
import com.study.user.model.view.UserRoleRowView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRoleEntity, UserRoleId> {

    @Query("""
            SELECT
                u.id AS userId,
                r.code AS code,
                r.name AS name
            FROM UserRoleEntity ur
            JOIN ur.userEntity u
            JOIN ur.roleEntity r
            WHERE u.id IN :userIdList
        """)
    List<UserRoleRowView> fetchRolesByUserIds(@Param("userIdList") final List<Long> userIdList);
}
