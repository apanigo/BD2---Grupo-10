/* \connect --mysql root@localhost */
CREATE DATABASE bd2_2023_grupo10;
ALTER USER 'root'@'localhost' INDENTIFIED WITH mysql_native_password BY 'bbdd2';
/* \use bd2_2023_grupo10 */
GRANT ALL PRIVILEGES ON bd2_2023_grupo10 TO 'root'@'localhost';
/*especificar los permisos*/