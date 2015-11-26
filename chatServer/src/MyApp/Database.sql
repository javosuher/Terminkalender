/* -- Delete Database -- */
DROP DATABASE IF EXISTS Terminkalender;

/* ----------------------------------------------------- */

/* -- Create Database and User -- */
CREATE DATABASE Terminkalender;
GRANT all ON Terminkalender.* to 'kalenderUser'@'localhost' identified by '1475';

/* -- Create Tables -- */
USE Terminkalender;
CREATE TABLE teachers(
	username varchar(50) not null,
	password varchar(50) not null
);
CREATE TABLE games(
	name varchar(50) not null,
	teacher varchar(50) not null,
	password varchar(50) not null,
	tasks varchar(100000),
	users varchar(100000)
);