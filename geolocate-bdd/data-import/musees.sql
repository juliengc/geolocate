#CREATE SCHEMA IF NOT EXISTS musees;
#USE musees;
USE geolocate;
SET SQL_SAFE_UPDATES = 0; 
DROP TABLE IF EXISTS musees_import;
CREATE TABLE IF NOT EXISTS  musees_import ( 
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
M varchar(255));

LOAD DATA INFILE "C:/Liste_musees_de_France-geocoded.csv"
  INTO TABLE musees_import
  COLUMNS TERMINATED BY ';'
  OPTIONALLY ENCLOSED BY '"'
  ESCAPED BY '"'
  LINES TERMINATED BY '\n'
  IGNORE 1 LINES;

SELECT * FROM musees_import;
SELECT L FROM musees_import WHERE L=''; 
SELECT M FROM musees_import WHERE M='';

SET SQL_SAFE_UPDATES = 0;
UPDATE musees_import
  SET L = NULL, M = NULL
  WHERE L = '' OR M = '';
SET SQL_SAFE_UPDATES = 1;

SELECT * FROM musees_import;
DROP TABLE IF EXISTS musees;
CREATE TABLE IF NOT EXISTS musees (
id INTEGER PRIMARY KEY AUTO_INCREMENT, 
nomreg VARCHAR(255)  ,
nomdep VARCHAR(255)  ,
dateappelation VARCHAR(255),
ferme VARCHAR(255),
annreouv VARCHAR(255),
annexe VARCHAR(255),
nom_du_musee VARCHAR(255),
adr VARCHAR(255),
cp VARCHAR(255)  ,
ville VARCHAR(255),
sitweb VARCHAR(255),
latitude DOUBLE ,
longitude DOUBLE,
uuid VARCHAR(255)
);

CREATE INDEX musees_ville_index on musees (ville); 


SET sql_mode = '';

INSERT INTO musees
  (nomreg, nomdep, dateappelation, ferme, annreouv, 
  annexe, nom_du_musee, adr, cp, ville,sitweb,latitude, longitude, uuid)
  SELECT A, B, C, D, E, F, G, H, I, J, K, L, M, uuid() FROM musees_import ;
  SELECT * FROM musees;

DROP TABLE musees_import;
SELECT * FROM musees;


#DELETE FROM address;
INSERT INTO address 
  (street, zip_code, city, state, uuid)
  SELECT adr, cp, ville, nomreg , uuid() 
  FROM
  (SELECT DISTINCT adr, cp, ville, nomreg 
    FROM musees 
	WHERE ville NOT in
		(SELECT a.city FROM address a)) AS aa;

SELECT address.id, address.street, address.zip_code, address.city, musees.nom_du_musee, musees.adr
  FROM address
  INNER JOIN musees ON musees.ville = address.city AND address.zip_code = musees.cp and musees.adr=address.street ;

#UPDATE located_object 
#  JOIN address ad ON ad.city  = located_object.name  AND ad.street is null
#  SET located_object.id_address = ad.id;
  
INSERT INTO located_object
  (name, description, uuid, latitude, longitude, id_address)
  SELECT nom_du_musee, ferme, musees.uuid, latitude, longitude, address.id FROM musees 
   INNER JOIN address ON musees.ville = address.city AND address.zip_code = musees.cp and musees.adr=address.street
   WHERE latitude IS NOT NULL;


SELECT * FROM located_object INNER JOIN address ON located_object.id_address = address.id WHERE name like '%usée%';
SELECT * FROM tag ;
SELECT DISTINCT lower(musees.nomdep)  FROM musees 
  WHERE lower(nomdep) IN (SELECT name FROM tag);
  
INSERT INTO tag (name) 
  SELECT DISTINCT lower(nomdep)  FROM musees 
  WHERE lower(nomdep) NOT IN (SELECT name FROM tag);

INSERT IGNORE INTO tag (name) 
  VALUES ('musée');  

INSERT INTO object_tag (id_object, id_tag)
  SELECT located_object.id, tag.id 
  FROM musees
  LEFT OUTER JOIN located_object
  on located_object.uuid = musees.uuid
  LEFT OUTER JOIN tag
  on tag.name = lower(musees.nomdep);
 
SELECT tag.id FROM tag WHERE tag.name = 'musée';
 
INSERT INTO object_tag (id_object, id_tag)
  SELECT located_object.id, (SELECT tag.id FROM tag WHERE tag.name = 'musée')
  FROM musees
  LEFT OUTER JOIN located_object
  on located_object.uuid = musees.uuid;

SELECT * 
  FROM object_tag
  INNER JOIN located_object ON object_tag.id_object = located_object.id
  WHERE object_tag.id_tag = (SELECT tag.id FROM tag WHERE tag.name = 'musée');


SELECT COUNT(*) FROM musees;
SELECT COUNT(*) FROM object_tag;


SELECT codes_postaux, nom_commune, nom_région  
    FROM ville;
        
SET SQL_SAFE_UPDATES = 0;
#UPDATE address
#SET address.uuid = uuid()
#WHERE address.uuid;


SELECT * FROM ville;
SELECT * FROM address;





SELECT * FROM located_object;



