#DROP SCHEMA data;

#CREATE SCHEMA IF NOT EXISTS data;

USE geolocate;
SET SQL_SAFE_UPDATES = 0;
#DELETE FROM ville;
#DROP TABLE ville;
CREATE TABLE ville_import ( 
A varchar(255),
B varchar(255),
C varchar(255),
D varchar(255),
E varchar(255),
F varchar(255),
G varchar(255),
H varchar(255),
I varchar(255),
J varchar(255),
K varchar(255),
L varchar(255),
M varchar(255),
N varchar(255)

);

LOAD DATA INFILE "C:/eucircos_regions_departements_circonscriptions_communes_gps.csv"
INTO TABLE ville_import
COLUMNS TERMINATED BY ';'
OPTIONALLY ENCLOSED BY '"'
ESCAPED BY '"'
LINES TERMINATED BY '\n'
IGNORE 1 LINES;

SELECT L FROM ville_import WHERE L=''; 
SELECT M FROM ville_import WHERE M='';

SET SQL_SAFE_UPDATES = 0;
UPDATE ville_import
  SET L = NULL, M = NULL
  WHERE L = '' OR M = '';
SET SQL_SAFE_UPDATES = 1;

SELECT * FROM ville_import;

CREATE TABLE IF NOT EXISTS ville (
id INTEGER PRIMARY KEY AUTO_INCREMENT, 
EU_circo VARCHAR(255)  ,
code_région INT  ,
nom_région VARCHAR(255)  ,
chef_lieu_région VARCHAR(255)  ,
numéro_département INT ,
nom_département VARCHAR(255)  ,
préfecture VARCHAR(255),
numéro_circonscription INT  ,
nom_commune VARCHAR(255)  ,
codes_postaux VARCHAR(255)  ,
code_insee INT ,
latitude DOUBLE ,
longitude DOUBLE ,
éloignement VARCHAR(256));

CREATE INDEX ville_nom_commune_index on ville (nom_commune); 

SET sql_mode = '';

INSERT INTO ville
(EU_circo, code_région, nom_région, chef_lieu_région, numéro_département, 
nom_département, préfecture, numéro_circonscription, nom_commune,
codes_postaux,code_insee,latitude, longitude, éloignement)
SELECT A, B, C, D, E, F, G, H, I, J, K, L, M, N FROM ville_import ;

SELECT EU_circo, code_région, nom_région, chef_lieu_région, numéro_département, 
nom_département, préfecture, numéro_circonscription, nom_commune,
codes_postaux,code_insee,latitude, longitude, éloignement FROM ville;

DROP TABLE ville_import;
SELECT * FROM ville;

INSERT INTO located_object
  (name, uuid, latitude, longitude)
  SELECT nom_commune, UUID(),  latitude, longitude FROM ville WHERE latitude IS NOT NULL;

SELECT * FROM located_object;
SELECT * FROM tag;
INSERT INTO tag (name) 
  SELECT DISTINCT EU_circo FROM ville 
  WHERE EU_circo NOT IN (SELECT name FROM tag);
INSERT INTO tag (name) 
  SELECT DISTINCT nom_département FROM ville 
  WHERE nom_département NOT IN (SELECT name FROM tag);

INSERT INTO object_tag (id_object, id_tag)
  SELECT located_object.id, tag.id 
  FROM ville
  LEFT OUTER JOIN located_object
  on located_object.name = ville.nom_commune
  LEFT OUTER JOIN tag
  on tag.name = ville.EU_circo;
  
INSERT INTO object_tag (id_object, id_tag)
  SELECT located_object.id, tag.id 
  FROM ville
  LEFT OUTER JOIN located_object
  on located_object.name = ville.nom_commune
  LEFT OUTER JOIN tag
  on tag.name = ville.nom_département;  

SELECT COUNT(*) FROM ville;
SELECT COUNT(*) FROM object_tag;
#DELETE FROM address;
INSERT INTO address 
  (zip_code, city, state, uuid)
  SELECT codes_postaux, nom_commune, nom_région , uuid() 
  FROM
  (SELECT DISTINCT codes_postaux, nom_commune, nom_région 
    FROM ville 
	WHERE nom_commune NOT in
		(SELECT a.city FROM address a)) AS aa;

SELECT codes_postaux, nom_commune, nom_région  
    FROM ville;
        
SET SQL_SAFE_UPDATES = 0;
#UPDATE address
#SET address.uuid = uuid()
#WHERE address.uuid;


SELECT * FROM ville;
SELECT * FROM address;


SELECT address.id, address.zip_code, address.city, ville.nom_commune, ville.codes_postaux
  FROM address
  INNER JOIN ville ON ville.nom_commune = address.city AND address.zip_code = ville.codes_postaux;

UPDATE located_object 
  JOIN address ad ON ad.city  = located_object.name  AND ad.street is null
  SET located_object.id_address = ad.id;


SELECT * FROM located_object;
