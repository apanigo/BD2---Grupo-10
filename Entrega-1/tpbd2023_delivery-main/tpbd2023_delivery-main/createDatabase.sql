/* \connect --mysql root@localhost */

CREATE DATABASE bd2_2023_grupo10;
ALTER USER 'root'@'localhost' INDENTIFIED WITH mysql_native_password BY 'bbdd2';

/* \use bd2_2023_grupo10 */

/*GRANT ALL PRIVILEGES ON bd2_2023_grupo10 TO 'root'@'localhost';*/
GRANT ALTER ON bd2_2023_grupo10.* to 'root'@'localhost';
GRANT CREATE ON bd2_2023_grupo10.* to 'root'@'localhost';
GRANT DELETE ON bd2_2023_grupo10.* to 'root'@'localhost';
GRANT DROP ON bd2_2023_grupo10.* to 'root'@'localhost';
GRANT INSERT ON bd2_2023_grupo10.* to 'root'@'localhost';
GRANT SELECT ON bd2_2023_grupo10.* to 'root'@'localhost';
GRANT UPDATE ON bd2_2023_grupo10.* to 'root'@'localhost';
FLUSH PRIVILEGES;
