DROP SCHEMA imports;
CREATE SCHEMA imports;
USE imports;

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

CREATE TABLE etablissement_petite_enfance1 (
	A VARCHAR(256),
	B VARCHAR(256),
	C VARCHAR(256),
	D VARCHAR(256),
    E VARCHAR(256),
    F VARCHAR(256));
    


