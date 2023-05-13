/* \connect --mysql root@localhost */

CREATE DATABASE bd2_2023_grupo10;

CREATE USER 'grupo10bd2'@'localhost' IDENTIFIED WITH mysql_native_password BY 'bbdd2';

/* \use bd2_2023_grupo10 */

/*GRANT ALL PRIVILEGES ON bd2_2023_grupo10 TO 'grupo10bd2'@'localhost';*/
GRANT ALTER ON bd2_2023_grupo10.* to 'grupo10bd2'@'localhost';
GRANT CREATE ON bd2_2023_grupo10.* to 'grupo10bd2'@'localhost';
GRANT DELETE ON bd2_2023_grupo10.* to 'grupo10bd2'@'localhost';
GRANT DROP ON bd2_2023_grupo10.* to 'grupo10bd2'@'localhost';
GRANT INSERT ON bd2_2023_grupo10.* to 'grupo10bd2'@'localhost';
GRANT SELECT ON bd2_2023_grupo10.* to 'grupo10bd2'@'localhost';
GRANT UPDATE ON bd2_2023_grupo10.* to 'grupo10bd2'@'localhost';
FLUSH PRIVILEGES;