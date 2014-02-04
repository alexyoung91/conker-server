-- Conker database definition --

/*
 * - Create the user 'conker' that can log in from 'localhost' only
 * - Grant the user access to the 'Conker' database
 * - Reload privileges?
 */

CREATE USER 'conker'@'localhost' IDENTIFIED BY '********';
GRANT ALL PRIVILEGES ON Conker.* TO 'conker'@'localhost';
FLUSH PRIVILEGES;

-- Change to user: 'conker' --

/*
 * Create the Conker database
 */

CREATE DATABASE IF NOT EXISTS Conker;
USE Conker;

/*
 * Create the User table
 * InnoDB for foreign key support
 */

CREATE TABLE IF NOT EXISTS User (
	id INT(11) NOT NULL AUTO_INCREMENT,
	email VARCHAR(255) NOT NULL, /* email max length is 256 */
	firstName VARCHAR(64) NOT NULL,
	lastName VARCHAR(64) NOT NULL,
	gender VARCHAR(1) NOT NULL, /* m = male, f = female, u = unspecified */
	dob DATE NOT NULL,
	organisation VARCHAR(64),
	password BINARY(60) NOT NULL,
	homeLocationLat DECIMAL(9, 6) NOT NULL,
	homeLocationLong DECIMAL(10, 6) NOT NULL,
	PRIMARY KEY (id)
) ENGINE = INNODB;

/*
 * Create the UserImage table
 * InnoDB for foreign key support
 */

CREATE TABLE IF NOT EXISTS UserImage (
	id INT(11) NOT NULL,
	source VARCHAR(255) NOT NULL,
	userID INT(11) NOT NULL,
	PRIMARY KEY (id)
) ENGINE = INNODB;

/*
 * Create the Project table
 * InnoDB for foreign key support
 */

CREATE TABLE IF NOT EXISTS Project (
	id INT(11) NOT NULL,
	title VARCHAR(255) NOT NULL,
	description TEXT NOT NULL,
	noVolunteersNeeded INT(2) NOT NULL, /* 99 max */
	locationLat DECIMAL(9, 6) NOT NULL,
	locationLong DECIMAL(10, 6) NOT NULL,
	startDate DATE NOT NULL,
	endDate DATE NOT NULL,
	userID INT(11) NOT NULL,
	PRIMARY KEY (id)
) ENGINE = INNODB;

/*
 * Create the ProjectImage table
 * InnoDB for foreign key support
 */

CREATE TABLE IF NOT EXISTS ProjectImage (
	id INT(11) NOT NULL,
	source VARCHAR(255) NOT NULL,
	projectID INT(11) NOT NULL,
	PRIMARY KEY (id)
) ENGINE = INNODB;

/*
 * Create the Volunteer table
 * InnoDB for foreign key support
 */

CREATE TABLE IF NOT EXISTS Volunteer (
	id INT(11) NOT NULL,
	userID INT(11) NOT NULL,
	projectID INT(11) NOT NULL,
	PRIMARY KEY (id)
) ENGINE = INNODB;

/*
 * Create the Star table
 * InnoDB for foreign key support
 */

CREATE TABLE IF NOT EXISTS Star (
	id INT(11) NOT NULL,
	userID INT(11) NOT NULL,
	projectID INT(11) NOT NULL,
	PRIMARY KEY (id)
) ENGINE = INNODB;

/*
 * Create the Star table
 * InnoDB for foreign key support
 */

CREATE TABLE IF NOT EXISTS Search (
	id INT(11) NOT NULL,
	phrase VARCHAR(255) NOT NULL,
	userID INT(11) NOT NULL,
	PRIMARY KEY (id)
) ENGINE = INNODB;

/*
 * Add foreign keys
 */

ALTER TABLE UserImage ADD FOREIGN KEY (userID) REFERENCES User(id);

ALTER TABLE Project ADD FOREIGN KEY (userID) REFERENCES User(id);

ALTER TABLE ProjectImage ADD FOREIGN KEY (projectID) REFERENCES Project(id);

ALTER TABLE Volunteer ADD FOREIGN KEY (userID) REFERENCES User(id);
ALTER TABLE Volunteer ADD FOREIGN KEY (projectID) REFERENCES Project(id);

ALTER TABLE Star ADD FOREIGN KEY (userID) REFERENCES User(id);
ALTER TABLE Star ADD FOREIGN KEY (projectID) REFERENCES Project(id);

ALTER TABLE Search ADD FOREIGN KEY (userID) REFERENCES User(id);

/*
 * To create foreign key in table:
 * 	FOREIGN KEY (userID) REFERENCES User(id)
 *	http://www.w3schools.com/sql/sql_foreignkey.asp
 *
 * Add foreign keys after all tables have been created?
 *
 * If an attempt to drop a table is made while a foreign key constraint exists,
 * the table cannot be dropped.
 *
 * TO DROP FOREIGN KEY:
 * 	ALTER TABLE ProjectPhoto DROP FOREIGN KEY projectID
 */
 
