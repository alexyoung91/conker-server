-- Conker database definition --

/*
 * - Create the user 'conker' that can log in from 'localhost' only
 * - Grant the user access to the 'conker' database
 * - Reload privileges
 */

CREATE USER 'conker'@'localhost' IDENTIFIED BY 'qNMwUQ9XX5Yx7AMX';
GRANT ALL PRIVILEGES ON conker.* TO 'conker'@'localhost';
FLUSH PRIVILEGES;

-- Change to user: 'conker' --

/*
 * Create the conker database
 */

CREATE DATABASE IF NOT EXISTS conker;
USE conker;

/*
 * Create the user table
 * InnoDB for foreign key support
 */

CREATE TABLE IF NOT EXISTS users (
	id INT(11) NOT NULL AUTO_INCREMENT,
	email VARCHAR(255) NOT NULL, /* email max length is 256 */
	first_name VARCHAR(64) NOT NULL,
	last_name VARCHAR(64) NOT NULL,
	gender VARCHAR(1) NOT NULL, /* m = male, f = female, u = unspecified */
	dob DATE NOT NULL,
	organisation VARCHAR(64),
	password BINARY(60) NOT NULL,
	home_location_lat DECIMAL(9, 6) NOT NULL,
	home_location_long DECIMAL(10, 6) NOT NULL,
	PRIMARY KEY (id)
) ENGINE = INNODB;

/*
 * Create the user_image table
 * InnoDB for foreign key support
 */

CREATE TABLE IF NOT EXISTS user_image (
	id INT(11) NOT NULL AUTO_INCREMENT,
	source VARCHAR(255) NOT NULL,
	user_id INT(11) NOT NULL,
	PRIMARY KEY (id)
) ENGINE = INNODB;

/*
 * Create the project table
 * InnoDB for foreign key support
 */

CREATE TABLE IF NOT EXISTS project (
	id INT(11) NOT NULL AUTO_INCREMENT,
	title VARCHAR(255) NOT NULL,
	description TEXT NOT NULL,
	no_volunteers_needed INT(2) NOT NULL, /* 99 max */
	location_lat DECIMAL(9, 6) NOT NULL,
	location_long DECIMAL(10, 6) NOT NULL,
	start_date DATE NOT NULL,
	end_date DATE NOT NULL,
	user_id INT(11) NOT NULL,
	PRIMARY KEY (id)
) ENGINE = INNODB;

/*
 * Create the project_image table
 * InnoDB for foreign key support
 */

CREATE TABLE IF NOT EXISTS project_image (
	id INT(11) NOT NULL AUTO_INCREMENT,
	source VARCHAR(255) NOT NULL,
	project_id INT(11) NOT NULL,
	PRIMARY KEY (id)
) ENGINE = INNODB;

/*
 * Create the volunteer table
 * InnoDB for foreign key support
 */

CREATE TABLE IF NOT EXISTS volunteer (
	id INT(11) NOT NULL AUTO_INCREMENT,
	user_id INT(11) NOT NULL,
	project_id INT(11) NOT NULL,
	PRIMARY KEY (id)
) ENGINE = INNODB;

/*
 * Create the star table
 * InnoDB for foreign key support
 */

CREATE TABLE IF NOT EXISTS star (
	id INT(11) NOT NULL AUTO_INCREMENT,
	user_id INT(11) NOT NULL,
	project_id INT(11) NOT NULL,
	PRIMARY KEY (id)
) ENGINE = INNODB;

/*
 * Create the search table
 * InnoDB for foreign key support
 */

CREATE TABLE IF NOT EXISTS search (
	id INT(11) NOT NULL AUTO_INCREMENT,
	phrase VARCHAR(255) NOT NULL,
	user_id INT(11) NOT NULL,
	PRIMARY KEY (id)
) ENGINE = INNODB;

/*
 * Add foreign keys
 */

ALTER TABLE user_image ADD FOREIGN KEY (user_id) REFERENCES user(id);

ALTER TABLE project ADD FOREIGN KEY (user_id) REFERENCES user(id);

ALTER TABLE project_image ADD FOREIGN KEY (project_id) REFERENCES project(id);

ALTER TABLE volunteer ADD FOREIGN KEY (user_id) REFERENCES user(id);
ALTER TABLE volunteer ADD FOREIGN KEY (project_id) REFERENCES project(id);

ALTER TABLE star ADD FOREIGN KEY (user_id) REFERENCES user(id);
ALTER TABLE star ADD FOREIGN KEY (project_id) REFERENCES project(id);

ALTER TABLE search ADD FOREIGN KEY (user_id) REFERENCES user(id);

/*
 * To create foreign key in table:
 * 	FOREIGN KEY (user_id) REFERENCES User(id)
 *	http://www.w3schools.com/sql/sql_foreignkey.asp
 *
 * Add foreign keys after all tables have been created?
 *
 * If an attempt to drop a table is made while a foreign key constraint exists,
 * the table cannot be dropped.
 *
 * TO DROP FOREIGN KEY:
 * 	ALTER TABLE project_photo DROP FOREIGN KEY project_id
 */
 
