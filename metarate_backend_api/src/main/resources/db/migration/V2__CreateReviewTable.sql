CREATE TABLE review (
                        id INT NOT NULL AUTO_INCREMENT,
                        game_id INT NOT NULL,
                        user_id INT NOT NULL,
                        rating DOUBLE NOT NULL,
                        content TEXT NOT NULL,
                        date DATETIME NOT NULL,
                        PRIMARY KEY (id),
                        FOREIGN KEY (game_id) REFERENCES game (id),
                        FOREIGN KEY (user_id) REFERENCES user (id),
                        UNIQUE (game_id, user_id)
);
