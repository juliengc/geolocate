#DROP SCHEMA imports;
#CREATE SCHEMA imports;
#USE imports;
USE geolocate;
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

LOAD DATA INFILE 'C:/etablissement-petite-enfance.csv' 
INTO TABLE etablissement_sante1 
FIELDS TERMINATED BY ';' 
LINES TERMINATED BY '\n'
IGNORE 1 ROWS;

CREATE TABLE etablissement_sante2
	(id INT PRIMARY KEY auto_increment,
	ident_nca VARCHAR(256),
    nom VARCHAR(256),
	sous_type VARCHAR(256),
	code_insee VARCHAR(256),
	type VARCHAR(256),
	geometry VARCHAR(256));
    
INSERT INTO etablissement_sante2 (ident_nca, nom, sous_type, code_insee, type, geometry)
	 (SELECT * FROM etablissement_sante1);
SELECT * FROM etablissement_sante2;
DROP TABLE etablissement_sante1;

CREATE TABLE etablissement_sante
	(id INT PRIMARY KEY auto_increment,
	ident_nca VARCHAR(256),
    nom VARCHAR(256),
	sous_type VARCHAR(256),
	code_insee VARCHAR(256),
	type VARCHAR(256),
	latitude DOUBLE,
    longitude DOUBLE);

INSERT INTO etablissement_sante (id, ident_nca, nom, sous_type, code_insee, type, latitude, longitude)
SELECT 
  id, ident_nca, nom, sous_type, code_insee, type,
  substring_index(substring_index(substring_index(geometry, ']', 1), '[', -1), ',', 1) AS latitude,
  substring_index(substring_index(substring_index(geometry, ']', 1), '[', -1), ',', -1) AS longitude
FROM etablissement_sante2;

SELECT * FROM etablissement_sante;
DROP TABLE etablissement_sante2;

 
INSERT INTO located_object
  (name, uuid, latitude, longitude)
  SELECT nom, UUID(),  latitude, longitude FROM etablissement_sante WHERE latitude IS NOT NULL;
    
SELECT * FROM located_object;
SELECT * FROM tag;
INSERT INTO tag (name) 
  SELECT DISTINCT type FROM etablissement_sante 
  WHERE type NOT IN (SELECT name FROM tag);
INSERT INTO tag (name) 
  SELECT DISTINCT sous_type FROM etablissement_sante 
  WHERE sous_type NOT IN (SELECT name FROM tag);

INSERT INTO object_tag (id_object, id_tag)
  SELECT located_object.id, tag.id 
  FROM etablissement_sante
  LEFT OUTER JOIN located_object
  on located_object.name = etablissement_sante.nom
  LEFT OUTER JOIN tag
  on tag.name = etablissement_sante.type;
  
INSERT INTO object_tag (id_object, id_tag)
  SELECT located_object.id, tag.id 
  FROM etablissement_sante
  LEFT OUTER JOIN located_object
  on located_object.name = etablissement_sante.nom
  LEFT OUTER JOIN tag
  on tag.name = etablissement_sante.sous_type;  

SELECT COUNT(*) FROM etablissement_sante;
SELECT COUNT(*) FROM object_tag;
SELECT * FROM etablissement_sante;
SELECT * FROM tag;

 
 




