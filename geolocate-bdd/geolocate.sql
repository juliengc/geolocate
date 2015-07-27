DROP database geolocate;

CREATE DATABASE IF NOT EXISTS geolocate;

USE geolocate;

CREATE TABLE address (
	id INTEGER PRIMARY KEY AUTO_INCREMENT,
    street VARCHAR(4000) ,
	zip_code int(10)  ,
	city VARCHAR(255) ,
    state VARCHAR(255) ,
	country VARCHAR(255)   default "France",
    uuid VARCHAR(255)  NOT NULL UNIQUE
);



CREATE TABLE located_object (
	id INTEGER PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(255)  NOT NULL,
	description  VARCHAR(4000)  NOT NULL,
    latitude double NOT NULL,
    longitude double NOT NULL,
    altitude double,
    created_in DATETIME default current_timestamp,
    uuid VARCHAR(255)  NOT NULL UNIQUE,
    id_address INTEGER, 
    CONSTRAINT fk_id_address_ref_address_id FOREIGN KEY (id_address) REFERENCES address (id)

);


CREATE TABLE IF NOT EXISTS tag (
	id INTEGER PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS object_tag (
	id INTEGER PRIMARY KEY AUTO_INCREMENT,
	id_tag INTEGER,
    id_object INTEGER,
    CONSTRAINT fk_id_tag_ref_tag_id FOREIGN KEY (id_tag) REFERENCES tag (id),
    CONSTRAINT fk_id_object_ref_object_id FOREIGN KEY (id_object) REFERENCES located_object (id)
);




INSERT INTO address (street, zip_code, city, uuid) VALUES ('Corniche des Maurettes', '06270', 'Villeneuve Loubet', 1);
INSERT INTO located_object(id, name, description, latitude, longitude, altitude, uuid) VALUES(1, 'NY', 'BigApple', '40.7127837', '-74.0059413', '0', 1);
INSERT INTO tag (name) VALUES ('city'), ('building'), ('tree'), ('hospital'), ('school'), ('market');

