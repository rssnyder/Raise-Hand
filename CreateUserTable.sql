CREATE TABLE users (
first_name VARCHAR(20), 
last_name VARCHAR(20),
pass VARCHAR(20),
username VARCHAR(10),
posts_written numeric,
answers_written numeric,
role VARCHAR(10)
);

ALTER TABLE users
	ADD CONSTRAINT username_c
    UNIQUE(username);