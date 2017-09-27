CREATE TABLE ClassesPerUser (
FOREIGN KEY (user_id) references users(ID),
FOREIGN KEY (class_id) references classes(ID)
);