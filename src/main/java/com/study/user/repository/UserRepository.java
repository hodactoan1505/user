package com.study.user.repository;

import com.study.user.model.UserEntity;
import com.study.user.model.view.UserDetailView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    @Query("""
            SELECT COUNT(u) > 0
            FROM UserEntity u
            WHERE u.username = :username
              AND u.delFlg = false
        """)
    boolean existsActiveUsername(@Param("username") final String username);

    @Query("""
        SELECT
            u.id AS id,
            u.username AS username,
            NOT u.delFlg AS active
        FROM UserEntity u
        """)
    List<UserDetailView> fetchAllUsers();

    @Query("""
        SELECT
            u.id AS id,
            u.username AS username,
            NOT u.delFlg AS active
        FROM UserEntity u
        WHERE u.id = :userId
        """)
    Optional<UserDetailView> fetchUser(@Param("userId") final Long userId);
}
