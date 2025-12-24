-- =========================
-- TABLE: users
-- =========================
CREATE TABLE users (
   id BIGSERIAL PRIMARY KEY,

   username VARCHAR(100) NOT NULL,
   password VARCHAR(255) NOT NULL,

   created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
   created_by VARCHAR(100) NULL,

   updated_at TIMESTAMP NULL,
   updated_by VARCHAR(100) NULL,

   del_flg BOOLEAN NULL DEFAULT FALSE,
   deleted_at TIMESTAMP NULL,

   CONSTRAINT uk_users_username UNIQUE (username)
);

-- =========================
-- TABLE: roles
-- =========================
CREATE TABLE roles (
    id BIGSERIAL PRIMARY KEY,

    code VARCHAR(50) NOT NULL,
    name VARCHAR(100) NOT NULL,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100) NULL,

    updated_at TIMESTAMP NULL,
    updated_by VARCHAR(100) NULL,

    del_flg BOOLEAN NULL DEFAULT FALSE,
    deleted_at TIMESTAMP NULL,

    CONSTRAINT uk_roles_code UNIQUE (code)
);

-- =========================
-- TABLE: user_roles (N-N)
-- =========================
CREATE TABLE user_roles (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,

    assigned_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY (user_id, role_id),

    CONSTRAINT fk_user_roles_user
        FOREIGN KEY (user_id)
            REFERENCES users (id)
            ON DELETE CASCADE,

    CONSTRAINT fk_user_roles_role
        FOREIGN KEY (role_id)
            REFERENCES roles (id)
            ON DELETE CASCADE
);
