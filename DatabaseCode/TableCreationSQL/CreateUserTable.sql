CREATE TABLE users (
ID int NOT NULL auto_increment,
role_id int NOT NULL,
class_id int,
first_name VARCHAR(20), 
last_name VARCHAR(20),
pass VARCHAR(260),
username VARCHAR(20),
PRIMARY KEY(ID),
FOREIGN KEY (role_id) references roles(ID)
);

ALTER TABLE users
	ADD CONSTRAINT username_c
    UNIQUE(username);