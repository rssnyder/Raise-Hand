CREATE TABLE userClasses (
ID int NOT NULL auto_increment,
relationship int NOT NULL,
user_id int not null,
class_id int not null,
PRIMARY KEY (ID),
FOREIGN KEY (class_id) references classes(ID),
FOREIGN KEY (user_id) references users(ID)
);