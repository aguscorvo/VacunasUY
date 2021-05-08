/* Roles */
INSERT INTO roles (nombre) VALUES ('Administrador');
INSERT INTO roles (nombre) VALUES ('Autoridad');
INSERT INTO roles (nombre) VALUES ('Vacunador');
INSERT INTO roles (nombre) VALUES ('Ciudadano');

/* Sectores */
INSERT INTO sectores_laborales (nombre) VALUES ('Salud');
INSERT INTO sectores_laborales (nombre) VALUES ('Educacion');
INSERT INTO sectores_laborales (nombre) VALUES ('Comercio');
INSERT INTO sectores_laborales (nombre) VALUES ('Funcion Publica');
INSERT INTO sectores_laborales (nombre) VALUES ('Defensa');

/* Departamentos */
INSERT INTO departamentos (nombre) VALUES ('Montevideo');
INSERT INTO departamentos (nombre) VALUES ('Canelones');
INSERT INTO departamentos (nombre) VALUES ('San Jose');
INSERT INTO departamentos (nombre) VALUES ('Maldonado');
INSERT INTO departamentos (nombre) VALUES ('Lavalleja');
INSERT INTO departamentos (nombre) VALUES ('Florida');
INSERT INTO departamentos (nombre) VALUES ('Colonia');
INSERT INTO departamentos (nombre) VALUES ('Flores');
INSERT INTO departamentos (nombre) VALUES ('Soriano');
INSERT INTO departamentos (nombre) VALUES ('Rio Negro');
INSERT INTO departamentos (nombre) VALUES ('Rocha');
INSERT INTO departamentos (nombre) VALUES ('Durazno');
INSERT INTO departamentos (nombre) VALUES ('Treina y Tres');
INSERT INTO departamentos (nombre) VALUES ('Cerro Largo');
INSERT INTO departamentos (nombre) VALUES ('Rivera');
INSERT INTO departamentos (nombre) VALUES ('Salto');
INSERT INTO departamentos (nombre) VALUES ('Paysandu');
INSERT INTO departamentos (nombre) VALUES ('Tacuarembo');
INSERT INTO departamentos (nombre) VALUES ('Artigas');

/* Localidades */
INSERT INTO localidades (nombre) VALUES ('Montevideo');
INSERT INTO localidades (nombre) VALUES ('Santiago Vazquez');
INSERT INTO localidades (nombre) VALUES ('Canelones');
INSERT INTO localidades (nombre) VALUES ('Pando');
INSERT INTO localidades (nombre) VALUES ('San Jose de Mayo');
INSERT INTO localidades (nombre) VALUES ('Libertad');
INSERT INTO localidades (nombre) VALUES ('Maldonado');
INSERT INTO localidades (nombre) VALUES ('Pan de Azucar');
INSERT INTO localidades (nombre) VALUES ('Minas');
INSERT INTO localidades (nombre) VALUES ('Solis de Mataojo');
INSERT INTO localidades (nombre) VALUES ('Florida');
INSERT INTO localidades (nombre) VALUES ('Sarandi Grande');
INSERT INTO localidades (nombre) VALUES ('Colonia del Sacramento');
INSERT INTO localidades (nombre) VALUES ('Carmelo');
INSERT INTO localidades (nombre) VALUES ('Trinidad');
INSERT INTO localidades (nombre) VALUES ('Ismael Cortinas');
INSERT INTO localidades (nombre) VALUES ('Mercedes');
INSERT INTO localidades (nombre) VALUES ('Dolores');
INSERT INTO localidades (nombre) VALUES ('Fray Bentos');
INSERT INTO localidades (nombre) VALUES ('Young');
INSERT INTO localidades (nombre) VALUES ('La Paloma');
INSERT INTO localidades (nombre) VALUES ('Rocha');
INSERT INTO localidades (nombre) VALUES ('Santa Bernardina');
INSERT INTO localidades (nombre) VALUES ('Sarandi del Yi');
INSERT INTO localidades (nombre) VALUES ('Santa Clara de Olimar');
INSERT INTO localidades (nombre) VALUES ('Treinta y Tres');
INSERT INTO localidades (nombre) VALUES ('Melo');
INSERT INTO localidades (nombre) VALUES ('Rio Branco');
INSERT INTO localidades (nombre) VALUES ('Tranqueras');
INSERT INTO localidades (nombre) VALUES ('Rivera');
INSERT INTO localidades (nombre) VALUES ('Salto');
INSERT INTO localidades (nombre) VALUES ('Villa Constitucion');
INSERT INTO localidades (nombre) VALUES ('Paysandu');
INSERT INTO localidades (nombre) VALUES ('Guichon');
INSERT INTO localidades (nombre) VALUES ('San Gregorio');
INSERT INTO localidades (nombre) VALUES ('Tacuarembo');
INSERT INTO localidades (nombre) VALUES ('Artigas');
INSERT INTO localidades (nombre) VALUES ('Bella Union');

/* Relación departamentos y localidades */
INSERT INTO departamentos_localidades VALUES (1,1);
INSERT INTO departamentos_localidades VALUES (1,2);
INSERT INTO departamentos_localidades VALUES (2,3);
INSERT INTO departamentos_localidades VALUES (2,4);
INSERT INTO departamentos_localidades VALUES (3,5);
INSERT INTO departamentos_localidades VALUES (3,6);
INSERT INTO departamentos_localidades VALUES (4,7);
INSERT INTO departamentos_localidades VALUES (4,8);
INSERT INTO departamentos_localidades VALUES (5,9);
INSERT INTO departamentos_localidades VALUES (5,10);
INSERT INTO departamentos_localidades VALUES (6,11);
INSERT INTO departamentos_localidades VALUES (6,12);
INSERT INTO departamentos_localidades VALUES (7,13);
INSERT INTO departamentos_localidades VALUES (7,14);
INSERT INTO departamentos_localidades VALUES (8,15);
INSERT INTO departamentos_localidades VALUES (8,16);
INSERT INTO departamentos_localidades VALUES (9,17);
INSERT INTO departamentos_localidades VALUES (9,18);
INSERT INTO departamentos_localidades VALUES (10,19);
INSERT INTO departamentos_localidades VALUES (10,20);
INSERT INTO departamentos_localidades VALUES (11,21);
INSERT INTO departamentos_localidades VALUES (11,22);
INSERT INTO departamentos_localidades VALUES (12,23);
INSERT INTO departamentos_localidades VALUES (12,24);
INSERT INTO departamentos_localidades VALUES (13,25);
INSERT INTO departamentos_localidades VALUES (13,26);
INSERT INTO departamentos_localidades VALUES (14,27);
INSERT INTO departamentos_localidades VALUES (14,28);
INSERT INTO departamentos_localidades VALUES (15,29);
INSERT INTO departamentos_localidades VALUES (15,30);
INSERT INTO departamentos_localidades VALUES (16,31);
INSERT INTO departamentos_localidades VALUES (16,32);
INSERT INTO departamentos_localidades VALUES (17,33);
INSERT INTO departamentos_localidades VALUES (17,34);
INSERT INTO departamentos_localidades VALUES (18,35);
INSERT INTO departamentos_localidades VALUES (18,36);
INSERT INTO departamentos_localidades VALUES (19,37);
INSERT INTO departamentos_localidades VALUES (19,38);

/* Puestos - Total: 95*/
INSERT INTO puestos (numero) VALUES(1);
INSERT INTO puestos (numero) VALUES(2);
INSERT INTO puestos (numero) VALUES(3);
INSERT INTO puestos (numero) VALUES(4);
INSERT INTO puestos (numero) VALUES(5);
INSERT INTO puestos (numero) VALUES(6);
INSERT INTO puestos (numero) VALUES(7);
INSERT INTO puestos (numero) VALUES(8);
INSERT INTO puestos (numero) VALUES(9);
INSERT INTO puestos (numero) VALUES(10);
INSERT INTO puestos (numero) VALUES(11);
INSERT INTO puestos (numero) VALUES(12);
INSERT INTO puestos (numero) VALUES(13);
INSERT INTO puestos (numero) VALUES(14);
INSERT INTO puestos (numero) VALUES(15);
INSERT INTO puestos (numero) VALUES(1);
INSERT INTO puestos (numero) VALUES(2);
INSERT INTO puestos (numero) VALUES(3);
INSERT INTO puestos (numero) VALUES(4);
INSERT INTO puestos (numero) VALUES(5);
INSERT INTO puestos (numero) VALUES(1);
INSERT INTO puestos (numero) VALUES(2);
INSERT INTO puestos (numero) VALUES(3);
INSERT INTO puestos (numero) VALUES(4);
INSERT INTO puestos (numero) VALUES(5);
INSERT INTO puestos (numero) VALUES(1);
INSERT INTO puestos (numero) VALUES(2);
INSERT INTO puestos (numero) VALUES(3);
INSERT INTO puestos (numero) VALUES(4);
INSERT INTO puestos (numero) VALUES(5);
INSERT INTO puestos (numero) VALUES(1);
INSERT INTO puestos (numero) VALUES(2);
INSERT INTO puestos (numero) VALUES(3);
INSERT INTO puestos (numero) VALUES(4);
INSERT INTO puestos (numero) VALUES(5); /* 35 */

INSERT INTO puestos (numero) VALUES(1);
INSERT INTO puestos (numero) VALUES(2);
INSERT INTO puestos (numero) VALUES(3);
INSERT INTO puestos (numero) VALUES(4);
INSERT INTO puestos (numero) VALUES(5);
INSERT INTO puestos (numero) VALUES(1);
INSERT INTO puestos (numero) VALUES(2);
INSERT INTO puestos (numero) VALUES(3);
INSERT INTO puestos (numero) VALUES(4);
INSERT INTO puestos (numero) VALUES(5);
INSERT INTO puestos (numero) VALUES(1);
INSERT INTO puestos (numero) VALUES(2);
INSERT INTO puestos (numero) VALUES(3);
INSERT INTO puestos (numero) VALUES(4);
INSERT INTO puestos (numero) VALUES(5);
INSERT INTO puestos (numero) VALUES(1);
INSERT INTO puestos (numero) VALUES(2);
INSERT INTO puestos (numero) VALUES(3);
INSERT INTO puestos (numero) VALUES(4);
INSERT INTO puestos (numero) VALUES(5);
INSERT INTO puestos (numero) VALUES(1);
INSERT INTO puestos (numero) VALUES(2);
INSERT INTO puestos (numero) VALUES(3);
INSERT INTO puestos (numero) VALUES(4);
INSERT INTO puestos (numero) VALUES(5); /* 60 */

INSERT INTO puestos (numero) VALUES(1);
INSERT INTO puestos (numero) VALUES(2);
INSERT INTO puestos (numero) VALUES(3);
INSERT INTO puestos (numero) VALUES(4);
INSERT INTO puestos (numero) VALUES(5);
INSERT INTO puestos (numero) VALUES(1);
INSERT INTO puestos (numero) VALUES(2);
INSERT INTO puestos (numero) VALUES(3);
INSERT INTO puestos (numero) VALUES(4);
INSERT INTO puestos (numero) VALUES(5);
INSERT INTO puestos (numero) VALUES(1);
INSERT INTO puestos (numero) VALUES(2);
INSERT INTO puestos (numero) VALUES(3);
INSERT INTO puestos (numero) VALUES(4);
INSERT INTO puestos (numero) VALUES(5);
INSERT INTO puestos (numero) VALUES(1);
INSERT INTO puestos (numero) VALUES(2);
INSERT INTO puestos (numero) VALUES(3);
INSERT INTO puestos (numero) VALUES(4);
INSERT INTO puestos (numero) VALUES(5);
INSERT INTO puestos (numero) VALUES(1);
INSERT INTO puestos (numero) VALUES(2);
INSERT INTO puestos (numero) VALUES(3);
INSERT INTO puestos (numero) VALUES(4);
INSERT INTO puestos (numero) VALUES(5); /* 85 */

INSERT INTO puestos (numero) VALUES(1);
INSERT INTO puestos (numero) VALUES(2);
INSERT INTO puestos (numero) VALUES(3);
INSERT INTO puestos (numero) VALUES(4);
INSERT INTO puestos (numero) VALUES(5);
INSERT INTO puestos (numero) VALUES(1);
INSERT INTO puestos (numero) VALUES(2);
INSERT INTO puestos (numero) VALUES(3);
INSERT INTO puestos (numero) VALUES(4);
INSERT INTO puestos (numero) VALUES(5); /* 95 */

/* Vacunatorios - Total: 14*/
INSERT INTO vacunatorios (nombre, latitud, longitud, direccion, localidad_id, departamento_id) VALUES ('Antel Arena', -34.86297, -56.15348, 'Dámaso A. Larrañaga entre Jose P. Varela y J. Serrato', 1, 1);
INSERT INTO vacunatorios (nombre, latitud, longitud, direccion, localidad_id, departamento_id) VALUES ('Hospital de Clinicas', -25.28013, -57.65262, 'Av.Italia S/N 1º Piso Consulta Externa Ala Este', 1, 1);
INSERT INTO vacunatorios (nombre, latitud, longitud, direccion, localidad_id, departamento_id) VALUES ('Hospital Pereira Rossell', -34.89885, -56.16283, 'Lord Ponsomby (Vacunatorio)', 1, 1);
INSERT INTO vacunatorios (nombre, latitud, longitud, direccion, localidad_id, departamento_id) VALUES ('Hospital de Canelones', -34.51517, -56.28450, 'Cendán esq G Taube', 3, 2);
INSERT INTO vacunatorios (nombre, latitud, longitud, direccion, localidad_id, departamento_id) VALUES ('Hospital de Pando', -34.72082, -55.95509, 'Luis Correch y Ruta 8', 4, 2);
INSERT INTO vacunatorios (nombre, latitud, longitud, direccion, localidad_id, departamento_id) VALUES ('Centro Proteccion de Choferes', -34.33762, -56.71323, 'Doctor Julian Bengoa ente 25 de Mayo y Sarandi', 5, 3);
INSERT INTO vacunatorios (nombre, latitud, longitud, direccion, localidad_id, departamento_id) VALUES ('Hospital ASSE', -34.62828, -56.61485, 'Artigas 626', 6, 3);
INSERT INTO vacunatorios (nombre, latitud, longitud, direccion, localidad_id, departamento_id) VALUES ('Campus Municipal de Maldonado', -34.91315, -54.95528, '3 de Febrero entre Av. Fco. Acuña de Figueroa y Santa Teresa', 7, 4);
INSERT INTO vacunatorios (nombre, latitud, longitud, direccion, localidad_id, departamento_id) VALUES ('Hospital de Pan de Azucar', -34.77912, -55.22403, 'Rincón 571 entre Francisco Bonilla y Lazo Batista y Goicochea', 8, 4);
INSERT INTO vacunatorios (nombre, latitud, longitud, direccion, localidad_id, departamento_id) VALUES ('Estadio Municipal Juan Antonio Lavalleja', -34.37106, -55.24818, 'Hector Gutierrez Ruiz s/nº entre Ciudad de Aiguá y Matías Lazarte', 9, 5);
INSERT INTO vacunatorios (nombre, latitud, longitud, direccion, localidad_id, departamento_id) VALUES ('Hospital ASSE - CP Florida CHLAEP', -34.09870, -56.20887, 'Baltasar Brum 414', 11, 6);
INSERT INTO vacunatorios (nombre, latitud, longitud, direccion, localidad_id, departamento_id) VALUES ('Hospital Samuel Berton CHLA-EP', -34.45834, -57.83026, 'Av Batlle y Ordoñez y Bassahun', 13, 7);
INSERT INTO vacunatorios (nombre, latitud, longitud, direccion, localidad_id, departamento_id) VALUES ('Hospital ASSE', -33.98991, -58.28301, 'Av Artigas 346 entre Ruta 21 y 19 de Abril', 14, 7);
INSERT INTO vacunatorios (nombre, latitud, longitud, direccion, localidad_id, departamento_id) VALUES ('Hospital de Mercedes', -33.25333, -58.04080, 'Florencio Sánchez s/n esq Cerrito', 17, 9);


/* Relación vacunatorios y puestos - Total: 93*/
INSERT INTO vacunatorios_puestos VALUES (1,1);
INSERT INTO vacunatorios_puestos VALUES (1,2);
INSERT INTO vacunatorios_puestos VALUES (1,3);
INSERT INTO vacunatorios_puestos VALUES (1,4);
INSERT INTO vacunatorios_puestos VALUES (1,5);
INSERT INTO vacunatorios_puestos VALUES (1,6);
INSERT INTO vacunatorios_puestos VALUES (1,7);
INSERT INTO vacunatorios_puestos VALUES (1,8);
INSERT INTO vacunatorios_puestos VALUES (1,9);
INSERT INTO vacunatorios_puestos VALUES (1,10);
INSERT INTO vacunatorios_puestos VALUES (1,11);
INSERT INTO vacunatorios_puestos VALUES (1,12);
INSERT INTO vacunatorios_puestos VALUES (1,13);
INSERT INTO vacunatorios_puestos VALUES (1,14);
INSERT INTO vacunatorios_puestos VALUES (1,15);
INSERT INTO vacunatorios_puestos VALUES (2,16);
INSERT INTO vacunatorios_puestos VALUES (2,17);
INSERT INTO vacunatorios_puestos VALUES (2,18);
INSERT INTO vacunatorios_puestos VALUES (2,19);
INSERT INTO vacunatorios_puestos VALUES (2,20);
INSERT INTO vacunatorios_puestos VALUES (2,21);
INSERT INTO vacunatorios_puestos VALUES (3,22);
INSERT INTO vacunatorios_puestos VALUES (3,23);
INSERT INTO vacunatorios_puestos VALUES (3,24);
INSERT INTO vacunatorios_puestos VALUES (3,25);
INSERT INTO vacunatorios_puestos VALUES (3,26);
INSERT INTO vacunatorios_puestos VALUES (3,27);
INSERT INTO vacunatorios_puestos VALUES (4,28);
INSERT INTO vacunatorios_puestos VALUES (4,29);
INSERT INTO vacunatorios_puestos VALUES (4,30);
INSERT INTO vacunatorios_puestos VALUES (4,31);
INSERT INTO vacunatorios_puestos VALUES (4,32);
INSERT INTO vacunatorios_puestos VALUES (4,33);
INSERT INTO vacunatorios_puestos VALUES (5,34);
INSERT INTO vacunatorios_puestos VALUES (5,35);
INSERT INTO vacunatorios_puestos VALUES (5,36);
INSERT INTO vacunatorios_puestos VALUES (5,37);
INSERT INTO vacunatorios_puestos VALUES (5,38);
INSERT INTO vacunatorios_puestos VALUES (5,39);

INSERT INTO vacunatorios_puestos VALUES (6,40);
INSERT INTO vacunatorios_puestos VALUES (6,41);
INSERT INTO vacunatorios_puestos VALUES (6,42);
INSERT INTO vacunatorios_puestos VALUES (6,43);
INSERT INTO vacunatorios_puestos VALUES (6,44);
INSERT INTO vacunatorios_puestos VALUES (6,45);
INSERT INTO vacunatorios_puestos VALUES (7,46);
INSERT INTO vacunatorios_puestos VALUES (7,47);
INSERT INTO vacunatorios_puestos VALUES (7,48);
INSERT INTO vacunatorios_puestos VALUES (7,49);
INSERT INTO vacunatorios_puestos VALUES (7,50);
INSERT INTO vacunatorios_puestos VALUES (7,51);
INSERT INTO vacunatorios_puestos VALUES (8,52);
INSERT INTO vacunatorios_puestos VALUES (8,53);
INSERT INTO vacunatorios_puestos VALUES (8,54);
INSERT INTO vacunatorios_puestos VALUES (8,55);
INSERT INTO vacunatorios_puestos VALUES (8,56);
INSERT INTO vacunatorios_puestos VALUES (8,57);
INSERT INTO vacunatorios_puestos VALUES (9,58);
INSERT INTO vacunatorios_puestos VALUES (9,59);
INSERT INTO vacunatorios_puestos VALUES (9,60);
INSERT INTO vacunatorios_puestos VALUES (9,61);
INSERT INTO vacunatorios_puestos VALUES (9,62);
INSERT INTO vacunatorios_puestos VALUES (9,63);
INSERT INTO vacunatorios_puestos VALUES (10,64);
INSERT INTO vacunatorios_puestos VALUES (10,65);
INSERT INTO vacunatorios_puestos VALUES (10,66);
INSERT INTO vacunatorios_puestos VALUES (10,67);
INSERT INTO vacunatorios_puestos VALUES (10,68);
INSERT INTO vacunatorios_puestos VALUES (10,69);

INSERT INTO vacunatorios_puestos VALUES (11,70);
INSERT INTO vacunatorios_puestos VALUES (11,71);
INSERT INTO vacunatorios_puestos VALUES (11,72);
INSERT INTO vacunatorios_puestos VALUES (11,73);
INSERT INTO vacunatorios_puestos VALUES (11,74);
INSERT INTO vacunatorios_puestos VALUES (11,75);
INSERT INTO vacunatorios_puestos VALUES (12,76);
INSERT INTO vacunatorios_puestos VALUES (12,77);
INSERT INTO vacunatorios_puestos VALUES (12,78);
INSERT INTO vacunatorios_puestos VALUES (12,79);
INSERT INTO vacunatorios_puestos VALUES (12,80);
INSERT INTO vacunatorios_puestos VALUES (12,81);
INSERT INTO vacunatorios_puestos VALUES (13,82);
INSERT INTO vacunatorios_puestos VALUES (13,83);
INSERT INTO vacunatorios_puestos VALUES (13,84);
INSERT INTO vacunatorios_puestos VALUES (13,85);
INSERT INTO vacunatorios_puestos VALUES (13,86);
INSERT INTO vacunatorios_puestos VALUES (13,87);
INSERT INTO vacunatorios_puestos VALUES (14,88);
INSERT INTO vacunatorios_puestos VALUES (14,89);
INSERT INTO vacunatorios_puestos VALUES (14,90);
INSERT INTO vacunatorios_puestos VALUES (14,91);
INSERT INTO vacunatorios_puestos VALUES (14,92);
INSERT INTO vacunatorios_puestos VALUES (14,93);

/* Transportistas Total: 2 */
INSERT INTO transportistas (nombre) VALUES ('Fuerza Aerea Uruguaya');
INSERT INTO transportistas (nombre) VALUES ('Agencia Central');








