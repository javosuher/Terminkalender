/* -- Delete Tables -- */
DROP TABLE teacher;
DROP TABLE games;

/* -- Create Tables -- */
CREATE TABLE teachers(
	username varchar(50) not null,
	password varchar(50) not null
);
CREATE TABLE games(
	name varchar(50) not null,
	teacher varchar(50) not null,
	password varchar(50) not null,
	tasks varchar(10000)
);