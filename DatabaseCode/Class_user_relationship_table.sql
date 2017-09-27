CREATE TABLE UserClasses (
relationship int NOT NULL auto_increment,
user_id int not null,
class_id int not null,
PRIMARY KEY (relationship),
FOREIGN KEY (class_id) references classes(ID),
FOREIGN KEY (user_id) references users(ID)
);