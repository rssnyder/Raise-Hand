CREATE TABLE threads(
	ID int NOT NULL auto_increment,
	topic_id int NOT NULL,
    owner_id int NOT NULL,
    title VARCHAR(30),
    description VARCHAR(2000), 
    endorsed_user_id int,
    flagged boolean,
    creation timestamp,
    points int,
	PRIMARY KEY(ID),
	FOREIGN KEY(topic_id) REFERENCES topics(ID),
    FOREIGN KEY (owner_id) REFERENCES users(ID),
    FOREIGN KEY (endorsed_user_id) REFERENCES users(ID)
);