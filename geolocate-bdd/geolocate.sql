DROP database geolocateM2i;

CREATE DATABASE IF NOT EXISTS geolocateM2i;

USE geolocateM2i;


CREATE TABLE locatedObject (
	id_object INTEGER PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(255)  NOT NULL,
	description  VARCHAR(4000)  NOT NULL,
    coordLat float NOT NULL,
    coordLong float NOT NULL,
    coordAlt float,
    created_in DATETIME default current_timestamp,
    uuid VARCHAR(255)  NOT NULL,
    adresses VARCHAR(4000),
    tags VARCHAR(255) 
);

CREATE TABLE address (
	id_address INTEGER PRIMARY KEY AUTO_INCREMENT,
    num int(10) ,
    street VARCHAR(255) ,
	zip_code int(10)  ,
	city VARCHAR(255) ,
	country VARCHAR(255)   default "France",
    uuid VARCHAR(255)  NOT NULL,
    locatedObjects VARCHAR(4000)
);

CREATE TABLE IF NOT EXISTS tag (
	id INTEGER PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS object_tag (
	id_object INTEGER,
	id_tag INTEGER,
    CONSTRAINT PRIMARY KEY (id_object, id_tag)
);




INSERT INTO address (id_address, num, street, zip_code, city, uuid) VALUES (1,'236', 'Corniche des Maurettes', '06270', 'Villeneuve Loubet', 1);
INSERT INTO locatedObject(id_object, name, description, coordLong, coordLat, coordAlt, uuid) VALUES(1, 'NY', 'BigApple', '40.7127837', '-74.0059413', '0', 1);


