ALTER TABLE users
	ADD university_id int;
ALTER TABLE classes
	ADD university_id int;
ALTER TABLE users
	ADD CONSTRAINT fk_user_universityID FOREIGN KEY(university_id) 
    references universities(ID);
ALTER TABLE classes
	ADD CONSTRAINT fk_class_universityID FOREIGN KEY(university_id) 
    references universities(ID);