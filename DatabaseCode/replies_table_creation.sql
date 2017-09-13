CREATE TABLE replies(
	ID int NOT NULL auto_increment,
	thread_id int NOT NULL,
    owner_id int NOT NULL,
    endorsed_user_id int,
    txt VARCHAR(2000), 
    flagged boolean,
    creation timestamp,
    points int,
	PRIMARY KEY(ID),
	FOREIGN KEY(thread_id) REFERENCES threads(ID),
    FOREIGN KEY (owner_id) REFERENCES users(ID),
    FOREIGN KEY (endorsed_user_id) REFERENCES users(ID)
);