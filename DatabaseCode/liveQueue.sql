CREATE TABLE liveQueue(
ID int NOT NULL auto_increment,
username VARCHAR(20),
class_id int not null,
creation timestamp,
PRIMARY KEY (ID),
FOREIGN KEY (class_id) references classes(ID),
FOREIGN KEY (username) references users(username)
);