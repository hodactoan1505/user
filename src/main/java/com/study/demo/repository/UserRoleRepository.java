package com.study.demo.repository;

import com.study.demo.dto.user.UserRoleRowDto;
import com.study.demo.model.UserRoleEntity;
import com.study.demo.model.base.UserRoleId;
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
    List<UserRoleRowDto> fetchRolesByUserIds(@Param("userIdList") final List<Long> userIdList);
}
