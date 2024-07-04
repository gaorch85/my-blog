CREATE TABLE user
(
    id        BIGINT AUTO_INCREMENT PRIMARY KEY,
    account  VARCHAR(255) unique NOT NULL,
    username VARCHAR(255),
    password  VARCHAR(255)        NOT NULL,
    salt      VARCHAR(255)        NOT NULL
);

CREATE TABLE blog
(
    id        BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id  BIGINT       NOT NULL,
    time      TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    title     VARCHAR(255) NOT NULL,
    content   TEXT         NOT NULL,
    is_public BOOLEAN               DEFAULT 0,
    FOREIGN KEY (user_id) REFERENCES user (id)
);

CREATE TABLE blog_comment
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    blog_id    BIGINT NOT NULL,
    user_id    BIGINT NOT NULL,
    content    TEXT   NOT NULL,
    region    varchar(255)   NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (blog_id) REFERENCES blog (id),
    FOREIGN KEY (user_id) REFERENCES user (id)
);

CREATE TABLE blog_like
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id    BIGINT NOT NULL,
    blog_id    BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user (id),
    FOREIGN KEY (blog_id) REFERENCES blog (id)
);

CREATE TABLE blog_favorite
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id    BIGINT NOT NULL,
    blog_id    BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user (id),
    FOREIGN KEY (blog_id) REFERENCES blog (id)
);

CREATE TABLE blog_view
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id    BIGINT NOT NULL,
    blog_id    BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user (id),
    FOREIGN KEY (blog_id) REFERENCES blog (id)
);



