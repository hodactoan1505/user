package com.study.user.model.base;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;

@MappedSuperclass
@Getter
public class BaseEntity {
    @CreatedDate
    @Column(name = "created_at", updatable = false)
    protected Instant createdAt;

    @Column(name = "created_by", updatable = false)
    protected String createdBy;

    @LastModifiedDate
    @Column(name = "updated_at")
    protected Instant updatedAt;

    @Column(name = "updated_by")
    protected String updatedBy;

    @Column(name = "del_flg")
    protected boolean delFlg;

    @Column(name = "deleted_at")
    protected Instant deletedAt;
}
