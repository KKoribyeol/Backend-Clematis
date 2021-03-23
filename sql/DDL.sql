DROP DATABASE IF EXISTS kkoribyeol;
CREATE DATABASE IF NOT EXISTS kkoribyeol;
USE kkoribyeol;

DROP TABLE IF EXISTS template;
DROP TABLE IF EXISTS affiliation;
DROP TABLE IF EXISTS target;
DROP TABLE IF EXISTS target_group;
DROP TABLE IF EXISTS project;
DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS push_result;
DROP TABLE IF EXISTS push_history;

CREATE TABLE account(
    id VARCHAR(20) NOT NULL,
    password VARCHAR(120) NOT NULL,
    name VARCHAR(12) NOT NULL,

    PRIMARY KEY (id)
);

CREATE TABLE project(
    code VARCHAR(28) NOT NULL,
    name VARCHAR(20) NOT NULL,
    description VARCHAR(100),
    owner VARCHAR(20),

    FOREIGN KEY (owner) REFERENCES account(id) ON DELETE CASCADE,

    PRIMARY KEY (code)
);

CREATE TABLE target(
    id BIGINT AUTO_INCREMENT,

    token VARCHAR(255) NOT NULL,
    project_code VARCHAR(28) NOT NULL,
    nickname VARCHAR(255) NOT NULL,

    name VARCHAR(12),

    UNIQUE (token, project_code),
    UNIQUE (nickname, project_code),

    FOREIGN KEY (project_code) REFERENCES project(code) ON DELETE CASCADE,

    PRIMARY KEY (id)
);

CREATE TABLE target_group(
    id BIGINT AUTO_INCREMENT,
    name VARCHAR(20) NOT NULL,
    project_code VARCHAR(28) NOT NULL,

    UNIQUE (name, project_code),

    FOREIGN KEY (project_code) REFERENCES project(code) ON DELETE CASCADE,

    PRIMARY KEY (id)
);

CREATE TABLE affiliation(
    id BIGINT AUTO_INCREMENT,
    target_id BIGINT NOT NULL,
    group_id BIGINT NOT NULL,

    FOREIGN KEY (target_id) REFERENCES target(id) ON DELETE CASCADE,
    FOREIGN KEY (group_id) REFERENCES target_group(id) ON DELETE CASCADE,

    PRIMARY KEY (id)
);

CREATE TABLE push_history(
    id BIGINT AUTO_INCREMENT,

    title VARCHAR(40) NOT NULL,
    content VARCHAR(255) NOT NULL,

    create_at DATETIME NOT NULL,
    complete_at DATETIME,
    reserve_at DATETIME,

    PRIMARY KEY (id)
);

CREATE TABLE push_result(
    id BIGINT AUTO_INCREMENT,

    target_id BIGINT NOT NULL,
    history_id BIGINT NOT NULL,
    status VARCHAR(8) NOT NULL,

    FOREIGN KEY (target_id) REFERENCES target(id),
    FOREIGN KEY (history_id) REFERENCES push_history(id),

    PRIMARY KEY (id)
);

CREATE TABLE template(
    id BIGINT AUTO_INCREMENT,
    title VARCHAR(40) NOT NULL,
    body VARCHAR(255) NOT NULL,

    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL,

    PRIMARY KEY (id)
);