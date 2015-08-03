#DROP SCHEMA imports;
#CREATE SCHEMA imports;
#USE imports;
USE geolocate;
DROP TABLE IF EXISTS etablissement_sante1;
CREATE TABLE etablissement_sante1 (
	A VARCHAR(256),
	B VARCHAR(256),
	C VARCHAR(256),
	D VARCHAR(256),
    E VARCHAR(256),
    F VARCHAR(256));
    
LOAD DATA INFILE 'C:/etablissement-sante.csv' 
INTO TABLE etablissement_sante1 
FIELDS TERMINATED BY ';' 
LINES TERMINATED BY '\n'
IGNORE 1 ROWS;
SELECT * FROM etablissement_sante1;

DROP TABLE IF EXISTS etablissement_enfance1;
CREATE TABLE etablissement_enfance1 (
	A VARCHAR(256),
	B VARCHAR(256),
	C VARCHAR(256),
	D VARCHAR(256),
    E VARCHAR(256),
    F VARCHAR(256));


LOAD DATA INFILE 'C:/etablissement-petite-enfance.csv' 
INTO TABLE etablissement_enfance1 
FIELDS TERMINATED BY ';' 
LINES TERMINATED BY '\n'
IGNORE 1 ROWS;
DROP TABLE IF EXISTS etablissement_sante2;
CREATE TABLE etablissement_sante2
	(id INT PRIMARY KEY auto_increment,
	ident_nca VARCHAR(256),
    nom VARCHAR(256),
	sous_type VARCHAR(256),
	code_insee VARCHAR(256),
	type VARCHAR(256),
	geometry VARCHAR(256),
    tag VARCHAR(255)
    );
    
INSERT INTO etablissement_sante2 (ident_nca, nom, sous_type, code_insee, type, geometry, tag)
	 (SELECT A, B, C, D, E, F, 'sante' FROM etablissement_sante1);
SELECT * FROM etablissement_sante2;
DROP TABLE etablissement_sante1;

INSERT INTO etablissement_sante2 (ident_nca, nom, sous_type, code_insee, type, geometry, tag)
	 (SELECT A, B, C, D, E, F, 'enfance' FROM etablissement_enfance1);
SELECT * FROM etablissement_sante2;
DROP TABLE etablissement_enfance1;


DROP TABLE IF EXISTS etablissement_sante;
CREATE TABLE etablissement_sante
	(id INT PRIMARY KEY auto_increment,
    uuid VARCHAR(256),
	ident_nca VARCHAR(256),
    nom VARCHAR(256),
	sous_type VARCHAR(256),
	code_insee VARCHAR(256),
	type VARCHAR(256),
	latitude DOUBLE,
    longitude DOUBLE,
    tag VARCHAR(255));

INSERT INTO etablissement_sante (id, uuid, ident_nca, nom, sous_type, code_insee, type, latitude, longitude, tag)
SELECT 
  id, uuid(), ident_nca, nom, sous_type, code_insee, type,
  substring_index(substring_index(substring_index(geometry, ']', 1), '[', -1), ',', -1) AS latitude,
  substring_index(substring_index(substring_index(geometry, ']', 1), '[', -1), ',', 1) AS longitude, tag
FROM etablissement_sante2;

SELECT * FROM etablissement_sante;
DROP TABLE etablissement_sante2;

 
INSERT INTO located_object
  (name, uuid, latitude, longitude)
  SELECT nom, uuid,  latitude, longitude FROM etablissement_sante WHERE latitude IS NOT NULL;
    
SELECT * FROM located_object;
SELECT * FROM tag;
INSERT INTO tag (name) 
  SELECT DISTINCT lower(type) FROM etablissement_sante 
  WHERE type NOT IN (SELECT name FROM tag);
INSERT INTO tag (name) 
  SELECT DISTINCT lower(sous_type) FROM etablissement_sante 
  WHERE sous_type NOT IN (SELECT name FROM tag);
INSERT IGNORE INTO tag (name) 
  VALUES ('etablissement-nice');  
  
INSERT INTO object_tag (id_object, id_tag)
  SELECT located_object.id, (SELECT tag.id  FROM tag WHERE tag.name = 'etablissement-nice')
  FROM etablissement_sante
  INNER JOIN located_object
  on located_object.uuid = etablissement_sante.uuid;

INSERT IGNORE INTO tag (name) 
  (SELECT DISTINCT etablissement_sante.tag FROM etablissement_sante);  
  
INSERT INTO object_tag (id_object, id_tag)
  SELECT located_object.id, tag.id
  FROM etablissement_sante
  INNER JOIN tag ON tag.name = etablissement_sante.tag
  INNER JOIN located_object
  on located_object.uuid = etablissement_sante.uuid;
  
INSERT INTO object_tag (id_object, id_tag)
  SELECT located_object.id, tag.id 
  FROM etablissement_sante
  LEFT OUTER JOIN located_object
  on located_object.uuid = etablissement_sante.uuid
  LEFT OUTER JOIN tag
  on tag.name = lower(etablissement_sante.type);
  
INSERT INTO object_tag (id_object, id_tag)
  SELECT located_object.id, tag.id 
  FROM etablissement_sante
  LEFT OUTER JOIN located_object
  on located_object.uuid = etablissement_sante.uuid
  LEFT OUTER JOIN tag
  on tag.name = lower(etablissement_sante.sous_type);  

SELECT COUNT(*) FROM etablissement_sante;
SELECT COUNT(*) FROM object_tag;
SELECT * FROM etablissement_sante;
SELECT * FROM tag;
SELECT * FROM located_object;
 
 




