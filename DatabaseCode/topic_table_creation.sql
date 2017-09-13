CREATE TABLE topics(
	ID int NOT NULL auto_increment,
	class_id int NOT NULL,
    creation_time timestamp,
    topic_name VARCHAR(30),
	PRIMARY KEY(ID),
	FOREIGN KEY(class_id) REFERENCES classes(ID)
);