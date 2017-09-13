CREATE TABLE classes(
	ID int NOT NULL auto_increment,
	teacher_id int NOT NULL,
    class_name VARCHAR(30),
    class_start DATE,
    class_end DATE,
    class_time_start TIME,
    class_time_end TIME,
    times_met_per_week int,
    access_code int,
	PRIMARY KEY(ID),
	FOREIGN KEY(teacher_id) REFERENCES users(ID)
);