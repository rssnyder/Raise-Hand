/** Adding the decided upon roles for the application**/
INSERT INTO roles (role_name)
VALUES ('admin');
INSERT INTO roles (role_name)
VALUES ('teacher');
INSERT INTO roles (role_name)
VALUES('ta');
INSERT INTO roles (role_name)
VALUES ('student');

-- Now check to ensure that it worked 
SELECT * FROM roles;

-- Worked and ID's auto incremented as it should