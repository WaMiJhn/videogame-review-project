CREATE TABLE genre (
   id INT NOT NULL AUTO_INCREMENT,
   name VARCHAR(255) NOT NULL,
   PRIMARY KEY (id)
);


CREATE TABLE game (
    id           INT NOT NULL AUTO_INCREMENT,
    title        VARCHAR(255) NOT NULL,
    developer    VARCHAR(255) NOT NULL,
    genre_id     INT NOT NULL,
    release_year INT NOT NULL,
    description  TEXT NOT NULL,
    image_url    VARCHAR(6000) NOT NULL,
    trailer_url  VARCHAR(6000) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (genre_id) REFERENCES genre(id)
);


create table user
(
    id         int          NOT NULL AUTO_INCREMENT,
    username   varchar(100)  NOT NULL,
    password   varchar(2000) NOT NULL,
    profile_pic varchar(200),
    PRIMARY KEY (id),
    UNIQUE (username)
);

CREATE TABLE user_role
(
    id        int         NOT NULL AUTO_INCREMENT,
    user_id   int         NOT NULL,
    role_name varchar(50) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (user_id, role_name),
    FOREIGN KEY (user_id) REFERENCES user (id)
);