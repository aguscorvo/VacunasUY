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
INSERT INTO localidades (nombre) VALUES ('Cardona');
INSERT INTO localidades (nombre) VALUES ('Fray Bentos');
INSERT INTO localidades (nombre) VALUES ('Young');

INSERT INTO localidades (nombre) VALUES ('La Paloma');
INSERT INTO localidades (nombre) VALUES ('Rocha');
INSERT INTO localidades (nombre) VALUES ('Sarandi del Yi');
INSERT INTO localidades (nombre) VALUES ('Durazno');
INSERT INTO localidades (nombre) VALUES ('Treinta y Tres');
INSERT INTO localidades (nombre) VALUES ('Santa Clara de Olimar');
INSERT INTO localidades (nombre) VALUES ('Melo');
INSERT INTO localidades (nombre) VALUES ('Rio Branco');
INSERT INTO localidades (nombre) VALUES ('Rivera');
INSERT INTO localidades (nombre) VALUES ('Tranqueras');

INSERT INTO localidades (nombre) VALUES ('Salto');
INSERT INTO localidades (nombre) VALUES ('Villa Constitucion');
INSERT INTO localidades (nombre) VALUES ('Paysandu');
INSERT INTO localidades (nombre) VALUES ('Guichon');
INSERT INTO localidades (nombre) VALUES ('Tacuarembo');
INSERT INTO localidades (nombre) VALUES ('San Gregorio');
INSERT INTO localidades (nombre) VALUES ('Artigas');
INSERT INTO localidades (nombre) VALUES ('Bella Union');
INSERT INTO localidades (nombre) VALUES ('Ciudad de la Costa');


/* Relación departamentos y localidades */
INSERT INTO departamentos_localidades VALUES (1,1);
INSERT INTO departamentos_localidades VALUES (1,2);
INSERT INTO departamentos_localidades VALUES (2,3);
INSERT INTO departamentos_localidades VALUES (2,4);
INSERT INTO departamentos_localidades VALUES (2,39);
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

/* Vacunatorios - Total: 42*/
INSERT INTO vacunatorios (nombre, latitud, longitud, direccion, fk_localidad, fk_departamento, clave) VALUES ('Antel Arena', -34.86297, -56.15348, 'Dámaso A. Larrañaga entre Jose P. Varela y J. Serrato', 1, 1, '78a7506b-74e5-4d34-81ba-6aff0765c87d');
INSERT INTO vacunatorios (nombre, latitud, longitud, direccion, fk_localidad, fk_departamento, clave) VALUES ('Hospital de Clinicas', -34.8913, -56.15215, 'Av.Italia S/N 1º Piso Consulta Externa Ala Este', 1, 1, 'c9aa375f-a4cf-47ed-bae0-2e9f4c49515b');
INSERT INTO vacunatorios (nombre, latitud, longitud, direccion, fk_localidad, fk_departamento, clave) VALUES ('Hospital Pereira Rossell', -34.89885, -56.16283, 'Lord Ponsomby (Vacunatorio)', 1, 1, 'eb310d55-e120-4d40-90f8-9dfe428146fe');
INSERT INTO vacunatorios (nombre, latitud, longitud, direccion, fk_localidad, fk_departamento, clave) VALUES ('Hospital de Canelones', -34.51517, -56.28450, 'Cendán esq G Taube', 3, 2, 'c20d9d32-8305-46ae-935e-78800c14e097');
INSERT INTO vacunatorios (nombre, latitud, longitud, direccion, fk_localidad, fk_departamento, clave) VALUES ('Hospital de Pando', -34.72082, -55.95509, 'Luis Correch y Ruta 8', 4, 2, '7f9314ad-0973-4a55-88f7-942b610df462');
INSERT INTO vacunatorios (nombre, latitud, longitud, direccion, fk_localidad, fk_departamento, clave) VALUES ('Centro Proteccion de Choferes', -34.33762, -56.71323, 'Doctor Julian Bengoa ente 25 de Mayo y Sarandi', 5, 3, '61880536-6bec-4122-99ea-5c74eefc4ec9');
INSERT INTO vacunatorios (nombre, latitud, longitud, direccion, fk_localidad, fk_departamento, clave) VALUES ('Hospital ASSE', -34.62828, -56.61485, 'Artigas 626', 6, 3, 'c334c98d-8de1-4a55-bfd4-8444dc289d49');
INSERT INTO vacunatorios (nombre, latitud, longitud, direccion, fk_localidad, fk_departamento, clave) VALUES ('Campus Municipal de Maldonado', -34.91315, -54.95528, '3 de Febrero entre Av. Fco. Acuña de Figueroa y Santa Teresa', 7, 4, '941bd7e0-1810-4141-ad2e-95322b3c835a');
INSERT INTO vacunatorios (nombre, latitud, longitud, direccion, fk_localidad, fk_departamento, clave) VALUES ('Hospital de Pan de Azucar', -34.77912, -55.22403, 'Rincón 571 entre Francisco Bonilla y Lazo Batista y Goicochea', 8, 4, 'bd26242e-0d36-4035-bb0b-9168961901a4');
INSERT INTO vacunatorios (nombre, latitud, longitud, direccion, fk_localidad, fk_departamento, clave) VALUES ('Estadio Municipal Juan Antonio Lavalleja', -34.37106, -55.24818, 'Hector Gutierrez Ruiz s/nº entre Ciudad de Aiguá y Matías Lazarte', 9, 5, 'bc9f787e-af30-40cb-8fa4-6e02fc191fb4');
INSERT INTO vacunatorios (nombre, latitud, longitud, direccion, fk_localidad, fk_departamento, clave) VALUES ('Hospital ASSE - CP Florida CHLAEP', -34.09870, -56.20887, 'Baltasar Brum 414', 11, 6, '30c811de-bf61-4021-804e-3ac4ddff0065');
INSERT INTO vacunatorios (nombre, latitud, longitud, direccion, fk_localidad, fk_departamento, clave) VALUES ('Hospital Samuel Berton CHLA-EP', -34.45834, -57.83026, 'Av Batlle y Ordoñez y Bassahun', 13, 7, 'a0e0be6a-af47-4e7a-8e5b-3d3aa1993b4c');
INSERT INTO vacunatorios (nombre, latitud, longitud, direccion, fk_localidad, fk_departamento, clave) VALUES ('Hospital ASSE', -33.98991, -58.28301, 'Av Artigas 346 entre Ruta 21 y 19 de Abril', 14, 7, 'a4136fb1-7eff-4304-a4b8-e5833672e1ea');
INSERT INTO vacunatorios (nombre, latitud, longitud, direccion, fk_localidad, fk_departamento, clave) VALUES ('Hospital de Mercedes', -33.25333, -58.04080, 'Florencio Sánchez s/n esq Cerrito', 17, 9, 'b3d5ab44-5854-4a70-bffc-91c404170b52');
INSERT INTO vacunatorios (nombre, latitud, longitud, direccion, fk_localidad, fk_departamento, clave) VALUES ('Centro de Salud Cerro', -34.87350, -56.25216, 'Doctor Pedro Catellino', 1, 1, '4abd3f66-eb49-4894-a07e-6a055cf0c820');

INSERT INTO vacunatorios (nombre, latitud, longitud, direccion, fk_localidad, fk_departamento, clave) VALUES ('Asociación Española', -34.80257, -56.22389, 'Margarita 5718 esq. Garzón', 1, 1, '7413744e-7b9c-4769-b54d-de95f10dbdc4');
INSERT INTO vacunatorios (nombre, latitud, longitud, direccion, fk_localidad, fk_departamento, clave) VALUES ('Centro Salud ASSE', -34.83063, -55.97701, 'Secco García y García Arocena s/n', 39, 2, '9e36add3-0460-4cb7-9ae7-6f8290b2b852');
INSERT INTO vacunatorios (nombre, latitud, longitud, direccion, fk_localidad, fk_departamento, clave) VALUES ('Local Espacio Adolescente', -34.59776, -55.46589, 'Eduardo Fabini esquina Minas', 10, 5, 'ff491c79-9326-4240-9c02-562b8a1594fe');
INSERT INTO vacunatorios (nombre, latitud, longitud, direccion, fk_localidad, fk_departamento, clave) VALUES ('Hospital ASSE', -33.25333, -58.04080, 'Pasteur 1052 entre Treinta y Tres y Faustino Harrison', 12, 6, '808532b1-820b-4f31-bbc8-9b9d9fbb2786'); 
INSERT INTO vacunatorios (nombre, latitud, longitud, direccion, fk_localidad, fk_departamento, clave) VALUES ('Círculo Católico', -33.87021, -57.36780, 'Libertad 136', 18, 9, '5b3fd21a-e961-4763-8f99-e05f96927a76'); 
INSERT INTO vacunatorios (nombre, latitud, longitud, direccion, fk_localidad, fk_departamento, clave) VALUES ('Plaza de Deportes', -33.52134, -56.89787, 'Artigas entre Fondar y Herrera', 15, 8, '90eda8d0-0d55-4bc4-a6d3-84b75f5a307f'); 
INSERT INTO vacunatorios (nombre, latitud, longitud, direccion, fk_localidad, fk_departamento, clave) VALUES ('Policlínica ASSE', -33.95997, -57.08991, 'Artigas y Rivera', 16, 8, 'b1ccecbf-f9d5-453d-b53b-147a045d396d'); 
INSERT INTO vacunatorios (nombre, latitud, longitud, direccion, fk_localidad, fk_departamento, clave) VALUES ('Hospital ASSE', -33.11815, -58.30437, 'Oribe entre Instrucciones y Echeverria', 19, 10, 'e78d4aac-17d1-48ed-8668-a7f5a9f21cb6'); 
INSERT INTO vacunatorios (nombre, latitud, longitud, direccion, fk_localidad, fk_departamento, clave) VALUES ('CAMY Pediatría', -32.69803, -57.62954, '25 de Agosto 3417', 20, 10, '944e599a-64e1-4cd9-8ae0-93d79794894f'); 
INSERT INTO vacunatorios (nombre, latitud, longitud, direccion, fk_localidad, fk_departamento, clave) VALUES ('COMERO', -34.47703, -54.33720, 'Eliseo Marsol 152 entre Treinta y Tres y Florencio Sánchez', 22, 11, '661186d1-c018-482c-896b-b009ea80f1aa'); 
INSERT INTO vacunatorios (nombre, latitud, longitud, direccion, fk_localidad, fk_departamento, clave) VALUES ('Centro Cultural La Paloma', -34.65856, -54.15528, 'Javier Barrios Amorín s/n', 21, 11, '4d96bdda-f823-4cd3-b754-7e40dffb445f'); 
INSERT INTO vacunatorios (nombre, latitud, longitud, direccion, fk_localidad, fk_departamento, clave) VALUES ('CHLAEP', -33.37911, -56.52165, '18 de Julio 617 entre Manuel Oribe y E Piriz', 24, 12, 'd1351ce1-a739-4887-a5ad-96582b20c22d'); 
INSERT INTO vacunatorios (nombre, latitud, longitud, direccion, fk_localidad, fk_departamento, clave) VALUES ('CHLAEP', -33.34001, -55.62403, 'Av Dr Petrini 501 entre Presidente Berro y Dolores Vidal de Pereira', 23, 12, 'ca155ddc-e6c8-41f8-a7fc-b17f1ab16539'); 
INSERT INTO vacunatorios (nombre, latitud, longitud, direccion, fk_localidad, fk_departamento, clave) VALUES ('Estadio Municipal Centro Empleados de Comercio', -33.22127, -54.37563, 'Florencio Sánchez entre 10 de Marzo y Maracopa', 25, 13, 'c4a454df-f9f9-4644-ae55-a8d1facb5eb8'); 
INSERT INTO vacunatorios (nombre, latitud, longitud, direccion, fk_localidad, fk_departamento, clave) VALUES ('Centro Auxiliar de ASSE', -32.92362, -54.95583, 'Av. Chiquito Saravia s/n casi Ruta 7', 26, 13, 'ff994ad7-f393-4d1a-9995-bec934c65789'); 

INSERT INTO vacunatorios (nombre, latitud, longitud, direccion, fk_localidad, fk_departamento, clave) VALUES ('UTU Gimnasio', -32.37674, -54.16675, 'Navarrete s/n entre Manuel Oribe y Juan Antonio Lavalleja', 27, 14, 'c4a9e6e0-d2aa-4cd1-b454-12a093f205a2'); 
INSERT INTO vacunatorios (nombre, latitud, longitud, direccion, fk_localidad, fk_departamento, clave) VALUES ('Plaza de Deportes', -32.59059, -53.37905, 'Joaquín Gundin S/N entre Victoria Ipar de Zamora y 10 de Junio', 28, 14, '44317212-82e9-43ff-aa56-00929fa7e4ae'); 
INSERT INTO vacunatorios (nombre, latitud, longitud, direccion, fk_localidad, fk_departamento, clave) VALUES ('Casa de la Cultura', -30.90691, -55.54542, 'Presidente Viera s/n', 29, 15, '461c38fe-f13c-4a91-ae53-27128d14d392');
INSERT INTO vacunatorios (nombre, latitud, longitud, direccion, fk_localidad, fk_departamento, clave) VALUES ('Policlínica de ASSE', -31.19440, -55.76235, 'Calle 33 orientales S/N', 30, 15, 'bb15a6b1-05e3-40fa-a32e-de42f9dabf2c'); 
INSERT INTO vacunatorios (nombre, latitud, longitud, direccion, fk_localidad, fk_departamento, clave) VALUES ('Hospital de Agudos', -31.40356, -57.94944, 'Florencio Sánchez s/n esq Cerrito', 31, 16, 'b365d765-9ff3-468d-9a2a-91b9664f2fb0'); 
INSERT INTO vacunatorios (nombre, latitud, longitud, direccion, fk_localidad, fk_departamento, clave) VALUES ('Local Comercial', -31.38576, -57.96400, 'Dr Soca y Brasil', 31, 16, 'e0af0aa5-d64f-4dcb-a06c-a8250127f355'); 
INSERT INTO vacunatorios (nombre, latitud, longitud, direccion, fk_localidad, fk_departamento, clave) VALUES ('CHLAEP', -32.32579, -58.08101, 'Monte Caseros 520 (entrada por Vizconde de Maua)', 33, 17, '9ef05006-5264-4e28-bd5f-4f42e39b1303'); 
INSERT INTO vacunatorios (nombre, latitud, longitud, direccion, fk_localidad, fk_departamento, clave) VALUES ('Centro Auxiliar de ASSE', -32.35442, -57.20890, 'Avenida Pedro Guichón', 34, 17, '82edd03f-030e-426c-a5b2-4a3719acea32'); 
INSERT INTO vacunatorios (nombre, latitud, longitud, direccion, fk_localidad, fk_departamento, clave) VALUES ('COMTA - Clínica Fisioterapia', -31.71601, -55.98710, 'Olimpia Pintos entre Dr Ivo Ferreira y Gral Rivera', 35, 18, 'cb682256-e221-4e94-9105-362c398fa8ec');
INSERT INTO vacunatorios (nombre, latitud, longitud, direccion, fk_localidad, fk_departamento, clave) VALUES ('Centro de Salud ', -32.61968, -55.83663, 'Yamandú Gamba 46', 36, 18, 'ae2eae80-1b51-413e-8f1e-ff48f904c2a6'); 

INSERT INTO vacunatorios (nombre, latitud, longitud, direccion, fk_localidad, fk_departamento, clave) VALUES ('Centro Tisiológico', -30.39867, -56.46245, 'Amaro F. Ramos S/N', 37, 19, '5fb6c043-eaa4-4151-9e22-256a5d569bf9');
INSERT INTO vacunatorios (nombre, latitud, longitud, direccion, fk_localidad, fk_departamento, clave) VALUES ('Hospital ASSE', -30.25807, -57.59560, 'Rivera y Treinta y Tres', 38, 19, '453aa201-7285-45d7-a2c2-51d370f0f81f');


/* Puestos - Total: 192*/
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(1, 1);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(2, 1);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(3, 1);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(4, 1);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(5, 1);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(6, 1);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(7, 1);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(8, 1);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(9, 1);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(10, 1);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(11, 1);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(12, 1);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(13, 1);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(14, 1);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(15, 1);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(1, 2); /* 16*/
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(2, 2);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(3, 2);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(4, 2);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(5, 2); /* 20 */
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(1, 3);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(2, 3);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(3, 3);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(4, 3);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(5, 3);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(1, 4);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(2, 4);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(3, 4);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(4, 4);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(5, 4);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(1, 5);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(2, 5);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(3, 5);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(4, 5);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(5, 5); /* 35 */

INSERT INTO puestos (numero, fk_vacunatorio) VALUES(1, 6);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(2, 6);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(3, 6);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(4, 6);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(5, 6);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(1, 7);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(2, 7);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(3, 7);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(4, 7);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(5, 7);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(1, 8);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(2, 8);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(3, 8);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(4, 8);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(5, 8);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(1, 9);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(2, 9);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(3, 9);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(4, 9);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(5, 9);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(1, 10);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(2, 10);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(3, 10);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(4, 10);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(5, 10); /* 60 */

INSERT INTO puestos (numero, fk_vacunatorio) VALUES(1, 11);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(2, 11);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(3, 11);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(4, 11);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(5, 11);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(1, 11);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(2, 11);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(3, 11);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(4, 11);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(5, 11);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(1, 12);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(2, 12);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(3, 12);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(4, 12);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(5, 12);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(1, 13);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(2, 13);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(3, 13);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(4, 13);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(5, 13);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(1, 14);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(2, 14);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(3, 14);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(4, 14);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(5, 14); /* 85 */

INSERT INTO puestos (numero, fk_vacunatorio) VALUES(1, 15);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(2, 15);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(3, 15);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(4, 15);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(5, 15);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(1, 16);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(2, 16);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(3, 16);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(4, 16);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(5, 16); /* 95 */

INSERT INTO puestos (numero, fk_vacunatorio) VALUES(1, 17);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(2, 17);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(3, 17);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(4, 17);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(5, 17);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(1, 18);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(2, 18);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(3, 18);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(4, 18);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(5, 18); /* 105 */

INSERT INTO puestos (numero, fk_vacunatorio) VALUES(1, 19);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(2, 19);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(3, 19);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(4, 19);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(5, 19);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(1, 20);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(2, 20);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(3, 20);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(4, 20);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(5, 20); /* 115 */

INSERT INTO puestos (numero, fk_vacunatorio) VALUES(1, 21);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(2, 21);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(3, 21);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(4, 21);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(5, 21);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(1, 22);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(2, 22);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(3, 22);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(4, 22);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(5, 22); /* 125 */

INSERT INTO puestos (numero, fk_vacunatorio) VALUES(1, 23);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(2, 23);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(3, 23);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(4, 23);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(5, 23);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(1, 23);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(2, 24);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(3, 24);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(4, 24);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(5, 24); /* 125 */

INSERT INTO puestos (numero, fk_vacunatorio) VALUES(1, 25);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(2, 25);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(3, 25);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(4, 25);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(5, 25);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(1, 26);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(2, 26);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(3, 26);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(4, 26);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(5, 26); /* 135 */

INSERT INTO puestos (numero, fk_vacunatorio) VALUES(1, 27);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(2, 27);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(3, 27);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(4, 27);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(5, 27);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(1, 28);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(2, 28);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(3, 28);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(4, 28);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(5, 28); /* 145 */

INSERT INTO puestos (numero, fk_vacunatorio) VALUES(1, 29);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(2, 29);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(3, 29);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(4, 29);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(5, 29);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(1, 30);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(2, 30);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(3, 30);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(4, 30);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(5, 30); /* 155 */

INSERT INTO puestos (numero, fk_vacunatorio) VALUES(1, 31);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(2, 31);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(3, 31);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(4, 31);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(5, 31);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(1, 32);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(2, 32);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(3, 32);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(4, 32);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(5, 32); /* 165 */

INSERT INTO puestos (numero, fk_vacunatorio) VALUES(1, 33);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(2, 33);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(3, 33);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(1, 34);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(2, 34);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(3, 34);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(1, 35);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(2, 35);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(3, 35); /* 174 */

INSERT INTO puestos (numero, fk_vacunatorio) VALUES(1, 36);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(2, 36);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(3, 36);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(1, 37);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(2, 37);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(3, 37);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(1, 38);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(2, 38);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(3, 38); /* 183 */

INSERT INTO puestos (numero, fk_vacunatorio) VALUES(1, 39);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(2, 39);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(3, 39);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(1, 40);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(2, 40);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(3, 40);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(1, 41);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(2, 41);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(3, 41); /* 192 */

INSERT INTO puestos (numero, fk_vacunatorio) VALUES(1, 42);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(2, 42);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(3, 42); /* 193 */


/* Relación vacunatorios y puestos - Total: 80*/
--INSERT INTO vacunatorios_puestos VALUES (1,1);
--INSERT INTO vacunatorios_puestos VALUES (1,2);
--INSERT INTO vacunatorios_puestos VALUES (1,3);
--INSERT INTO vacunatorios_puestos VALUES (1,4);
--INSERT INTO vacunatorios_puestos VALUES (1,5);
--INSERT INTO vacunatorios_puestos VALUES (1,6);
--INSERT INTO vacunatorios_puestos VALUES (1,7);
--INSERT INTO vacunatorios_puestos VALUES (1,8);
--INSERT INTO vacunatorios_puestos VALUES (1,9);
--INSERT INTO vacunatorios_puestos VALUES (1,10);
--INSERT INTO vacunatorios_puestos VALUES (1,11);
--INSERT INTO vacunatorios_puestos VALUES (1,12);
--INSERT INTO vacunatorios_puestos VALUES (1,13);
--INSERT INTO vacunatorios_puestos VALUES (1,14);
--INSERT INTO vacunatorios_puestos VALUES (1,15);
--
--INSERT INTO vacunatorios_puestos VALUES (2,16);
--INSERT INTO vacunatorios_puestos VALUES (2,17);
--INSERT INTO vacunatorios_puestos VALUES (2,18);
--INSERT INTO vacunatorios_puestos VALUES (2,19);
--INSERT INTO vacunatorios_puestos VALUES (2,20);
--
--INSERT INTO vacunatorios_puestos VALUES (3,21);
--INSERT INTO vacunatorios_puestos VALUES (3,22);
--INSERT INTO vacunatorios_puestos VALUES (3,23);
--INSERT INTO vacunatorios_puestos VALUES (3,24);
--INSERT INTO vacunatorios_puestos VALUES (3,25);
--
--INSERT INTO vacunatorios_puestos VALUES (4,26);
--INSERT INTO vacunatorios_puestos VALUES (4,27);
--INSERT INTO vacunatorios_puestos VALUES (4,28);
--INSERT INTO vacunatorios_puestos VALUES (4,29);
--INSERT INTO vacunatorios_puestos VALUES (4,30);
--
--INSERT INTO vacunatorios_puestos VALUES (5,31);
--INSERT INTO vacunatorios_puestos VALUES (5,32);
--INSERT INTO vacunatorios_puestos VALUES (5,33);
--INSERT INTO vacunatorios_puestos VALUES (5,34);
--INSERT INTO vacunatorios_puestos VALUES (5,35);
--
--INSERT INTO vacunatorios_puestos VALUES (6,36);
--INSERT INTO vacunatorios_puestos VALUES (6,37);
--INSERT INTO vacunatorios_puestos VALUES (6,38);
--INSERT INTO vacunatorios_puestos VALUES (6,39);
--INSERT INTO vacunatorios_puestos VALUES (6,40);
--
--INSERT INTO vacunatorios_puestos VALUES (7,41);
--INSERT INTO vacunatorios_puestos VALUES (7,42);
--INSERT INTO vacunatorios_puestos VALUES (7,43);
--INSERT INTO vacunatorios_puestos VALUES (7,44);
--INSERT INTO vacunatorios_puestos VALUES (7,45);
--
--INSERT INTO vacunatorios_puestos VALUES (8,46);
--INSERT INTO vacunatorios_puestos VALUES (8,47);
--INSERT INTO vacunatorios_puestos VALUES (8,48);
--INSERT INTO vacunatorios_puestos VALUES (8,49);
--INSERT INTO vacunatorios_puestos VALUES (8,50);
--
--INSERT INTO vacunatorios_puestos VALUES (9,51);
--INSERT INTO vacunatorios_puestos VALUES (9,52);
--INSERT INTO vacunatorios_puestos VALUES (9,53);
--INSERT INTO vacunatorios_puestos VALUES (9,54);
--INSERT INTO vacunatorios_puestos VALUES (9,55);
--
--INSERT INTO vacunatorios_puestos VALUES (10,56);
--INSERT INTO vacunatorios_puestos VALUES (10,57);
--INSERT INTO vacunatorios_puestos VALUES (10,58);
--INSERT INTO vacunatorios_puestos VALUES (10,59);
--INSERT INTO vacunatorios_puestos VALUES (10,60);
--
--INSERT INTO vacunatorios_puestos VALUES (11,61);
--INSERT INTO vacunatorios_puestos VALUES (11,62);
--INSERT INTO vacunatorios_puestos VALUES (11,63);
--INSERT INTO vacunatorios_puestos VALUES (11,64);
--INSERT INTO vacunatorios_puestos VALUES (11,65);
--
--INSERT INTO vacunatorios_puestos VALUES (12,66);
--INSERT INTO vacunatorios_puestos VALUES (12,67);
--INSERT INTO vacunatorios_puestos VALUES (12,68);
--INSERT INTO vacunatorios_puestos VALUES (12,69);
--INSERT INTO vacunatorios_puestos VALUES (12,70);
--
--INSERT INTO vacunatorios_puestos VALUES (13,71);
--INSERT INTO vacunatorios_puestos VALUES (13,72);
--INSERT INTO vacunatorios_puestos VALUES (13,73);
--INSERT INTO vacunatorios_puestos VALUES (13,74);
--INSERT INTO vacunatorios_puestos VALUES (13,75);
--
--INSERT INTO vacunatorios_puestos VALUES (14,76);
--INSERT INTO vacunatorios_puestos VALUES (14,77);
--INSERT INTO vacunatorios_puestos VALUES (14,78);
--INSERT INTO vacunatorios_puestos VALUES (14,79);
--INSERT INTO vacunatorios_puestos VALUES (14,80);


/* Transportistas - Total: 2 */
INSERT INTO transportistas (nombre) VALUES ('Fuerza Aerea Uruguaya');
INSERT INTO transportistas (nombre) VALUES ('Agencia Central');

/* Paises - Total: 16 */
INSERT INTO paises (nombre) VALUES ('China');
INSERT INTO paises (nombre) VALUES ('Estados Unidos');
INSERT INTO paises (nombre) VALUES ('Alemania');
INSERT INTO paises (nombre) VALUES ('India');
INSERT INTO paises (nombre) VALUES ('Reino Unido');
INSERT INTO paises (nombre) VALUES ('Holanda');
INSERT INTO paises (nombre) VALUES ('Belgica');
INSERT INTO paises (nombre) VALUES ('Rusia');
INSERT INTO paises (nombre) VALUES ('Suiza');
INSERT INTO paises (nombre) VALUES ('Corea del Sur');
INSERT INTO paises (nombre) VALUES ('Brasil');
INSERT INTO paises (nombre) VALUES ('Sudafrica');
INSERT INTO paises (nombre) VALUES ('Argentina');
INSERT INTO paises (nombre) VALUES ('Uruguay');
INSERT INTO paises (nombre) VALUES ('Espania');
INSERT INTO paises (nombre) VALUES ('Francia');

/* Proveedores - Total: 3*/
INSERT INTO proveedores (nombre, fk_pais) VALUES ('Sinovac Biotech', 1); /* En caso de COVID-19: vacuna: CoronaVac*/
INSERT INTO proveedores (nombre, fk_pais) VALUES ('AstraZeneca', 5); /* En caso de COVID-19: vacuna: chequear nombre*/
INSERT INTO proveedores (nombre, fk_pais) VALUES ('Pfizer', 3); /* En caso de COVID-19: vacuna: Pfizer-BioNTech*/

/* Enfermedades - Total: 1*/
INSERT INTO enfermedades (nombre) VALUES ('COVID-19');

/* Vacunas - Total: 3 */
INSERT INTO vacunas (cant_dosis, inmunidad, nombre, periodo, fk_enfermedad) VALUES (2, 1, 'CoronaVac', 1, 1);
INSERT INTO vacunas (cant_dosis, inmunidad, nombre, periodo, fk_enfermedad) VALUES (2, 1, 'Pfizer-BioNTech', 1, 1);

/* Planes de Vacunacion - Total: 3*/
INSERT INTO planes_vacunacion (edadminima, edadmaxima, fechainicio, fechafin, fk_vacuna) VALUES (30, 70, '2021-01-01', '2021-12-31', 1);
INSERT INTO planes_vacunacion (edadminima, edadmaxima, fechainicio, fechafin, fk_vacuna) VALUES (71, 99, '2021-07-01', '2021-12-31', 2);
INSERT INTO planes_vacunacion (edadminima, edadmaxima, fechainicio, fechafin, fk_vacuna) VALUES (18, 99, '2021-09-01', '2021-12-31', 1);

/* Planes de Vacunacion - Sectores - Total: 6*/
INSERT INTO planes_vacunacion_sectores (fk_plan_vacunacion, fk_sector) VALUES (1, 1);
INSERT INTO planes_vacunacion_sectores (fk_plan_vacunacion, fk_sector) VALUES (1, 2);
INSERT INTO planes_vacunacion_sectores (fk_plan_vacunacion, fk_sector) VALUES (1, 5);
INSERT INTO planes_vacunacion_sectores (fk_plan_vacunacion, fk_sector) VALUES (2, 3);
INSERT INTO planes_vacunacion_sectores (fk_plan_vacunacion, fk_sector) VALUES (3, 3);
INSERT INTO planes_vacunacion_sectores (fk_plan_vacunacion, fk_sector) VALUES (3, 4);

/* Lotes - Total: 2*/
INSERT INTO lotes (cantidad, cantidaddisponible, fk_proveedor, fk_vacuna) VALUES (50000, 50000, 1, 1);
INSERT INTO lotes (cantidad, cantidaddisponible, fk_proveedor, fk_vacuna) VALUES (30000, 30000, 3, 2);
