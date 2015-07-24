DROP database geolocate;

CREATE DATABASE IF NOT EXISTS geolocate;

USE geolocate;


CREATE TABLE locatedObject (
	id_object INTEGER PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(255)  NOT NULL,
	description  VARCHAR(4000)  NOT NULL,
    latitude double NOT NULL,
    longitude double NOT NULL,
    altitude double,
    created_in DATETIME default current_timestamp,
    uuid VARCHAR(255)  NOT NULL,
    id_address INTEGER
);

CREATE TABLE address (
	id_address INTEGER PRIMARY KEY AUTO_INCREMENT,
    street VARCHAR(4000) ,
	zip_code int(10)  ,
	city VARCHAR(255) ,
    state VARCHAR(255) ,
	country VARCHAR(255)   default "France",
    uuid VARCHAR(255)  NOT NULL
);

CREATE TABLE IF NOT EXISTS tag (
	id_tag INTEGER PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS object_tag (
	id_object INTEGER,
	id_tag INTEGER,
    CONSTRAINT PRIMARY KEY (id_object, id_tag)
);




INSERT INTO address (id_address, street, zip_code, city, uuid) VALUES (1, 'Corniche des Maurettes', '06270', 'Villeneuve Loubet', 1);
INSERT INTO locatedObject(id_object, name, description, latitude, longitude, altitude, uuid) VALUES(1, 'NY', 'BigApple', '40.7127837', '-74.0059413', '0', 1);
INSERT INTO tag (name) VALUES ('city'), ('building'), ('tree'), ('hospital'), ('school'), ('market');

INSERT INTO object_tag VALUES (1, 1), (1, 2), (1, 3);
