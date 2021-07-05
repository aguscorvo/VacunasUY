/* Roles */
INSERT INTO roles (nombre) VALUES ('Administrador');
INSERT INTO roles (nombre) VALUES ('Autoridad');
INSERT INTO roles (nombre) VALUES ('Vacunador');
INSERT INTO roles (nombre) VALUES ('Ciudadano');

/* Sectores */
INSERT INTO sectores_laborales (nombre) VALUES ('Bombero');
INSERT INTO sectores_laborales (nombre) VALUES ('Militar');
INSERT INTO sectores_laborales (nombre) VALUES ('Personal de la Salud');
INSERT INTO sectores_laborales (nombre) VALUES ('Policía');
INSERT INTO sectores_laborales (nombre) VALUES ('Docente');
INSERT INTO sectores_laborales (nombre) VALUES ('Otra ocupación');
INSERT INTO sectores_laborales (nombre) VALUES ('No tiene');
INSERT INTO sectores_laborales (nombre) VALUES ('Jubilado');

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


/* Puestos - Total: 205*/
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
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(5, 24); /* 135 */

INSERT INTO puestos (numero, fk_vacunatorio) VALUES(1, 25);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(2, 25);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(3, 25);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(4, 25);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(5, 25);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(1, 26);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(2, 26);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(3, 26);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(4, 26);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(5, 26); /* 145 */

INSERT INTO puestos (numero, fk_vacunatorio) VALUES(1, 27);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(2, 27);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(3, 27);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(4, 27);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(5, 27);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(1, 28);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(2, 28);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(3, 28);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(4, 28);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(5, 28); /* 155 */

INSERT INTO puestos (numero, fk_vacunatorio) VALUES(1, 29);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(2, 29);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(3, 29);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(4, 29);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(5, 29);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(1, 30);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(2, 30);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(3, 30);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(4, 30);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(5, 30); /* 165 */

INSERT INTO puestos (numero, fk_vacunatorio) VALUES(1, 31);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(2, 31);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(3, 31);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(4, 31);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(5, 31);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(1, 32);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(2, 32);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(3, 32);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(4, 32);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(5, 32); /* 175 */

INSERT INTO puestos (numero, fk_vacunatorio) VALUES(1, 33);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(2, 33);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(3, 33);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(1, 34);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(2, 34);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(3, 34);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(1, 35);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(2, 35);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(3, 35); /* 184 */

INSERT INTO puestos (numero, fk_vacunatorio) VALUES(1, 36);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(2, 36);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(3, 36);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(1, 37);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(2, 37);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(3, 37);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(1, 38);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(2, 38);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(3, 38); /* 193 */

INSERT INTO puestos (numero, fk_vacunatorio) VALUES(1, 39);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(2, 39);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(3, 39);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(1, 40);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(2, 40);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(3, 40);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(1, 41);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(2, 41);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(3, 41); /*202 */

INSERT INTO puestos (numero, fk_vacunatorio) VALUES(1, 42);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(2, 42);
INSERT INTO puestos (numero, fk_vacunatorio) VALUES(3, 42); /* 205 */


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

/* Enfermedades - Total: 4*/
INSERT INTO enfermedades (nombre) VALUES ('COVID-19');
INSERT INTO enfermedades (nombre) VALUES ('GRIPE');
INSERT INTO enfermedades (nombre) VALUES ('TUBERCULOSIS INFANTIL');
INSERT INTO enfermedades (nombre) VALUES ('TÉTANOS');

/* Vacunas - Total: 6 */
INSERT INTO vacunas (cant_dosis, inmunidad, nombre, periodo, fk_enfermedad) VALUES (2, 1, 'CoronaVac', 28, 1);
INSERT INTO vacunas (cant_dosis, inmunidad, nombre, periodo, fk_enfermedad) VALUES (2, 1, 'Pfizer-BioNTech', 28, 1);
INSERT INTO vacunas (cant_dosis, inmunidad, nombre, periodo, fk_enfermedad) VALUES (3, 1, 'Antigripal', 20, 2);
INSERT INTO vacunas (cant_dosis, inmunidad, nombre, periodo, fk_enfermedad) VALUES (1, 10, 'BCG', 0, 3);
INSERT INTO vacunas (cant_dosis, inmunidad, nombre, periodo, fk_enfermedad) VALUES (1, 10, 'Triple bacteriana (DPT)', 0, 4);
INSERT INTO vacunas (cant_dosis, inmunidad, nombre, periodo, fk_enfermedad) VALUES (1, 10, 'Doble bacteriana', 0, 4);

/* Planes de Vacunacion - Total: 6*/
INSERT INTO planes_vacunacion (edadminima, edadmaxima, fechainicio, fechafin, fk_vacuna) VALUES (30, 70, '2021-01-01', '2021-12-31', 1);
INSERT INTO planes_vacunacion (edadminima, edadmaxima, fechainicio, fechafin, fk_vacuna) VALUES (71, 99, '2021-07-01', '2021-12-31', 2);
INSERT INTO planes_vacunacion (edadminima, edadmaxima, fechainicio, fechafin, fk_vacuna) VALUES (18, 99, '2021-09-01', '2021-12-31', 1);
INSERT INTO planes_vacunacion (edadminima, edadmaxima, fechainicio, fechafin, fk_vacuna) VALUES (18, 99, '2021-06-01', '2021-12-31', 3);
INSERT INTO planes_vacunacion (edadminima, edadmaxima, fechainicio, fechafin, fk_vacuna) VALUES (18, 70, '2021-08-01', '2021-12-31', 2);
INSERT INTO planes_vacunacion (edadminima, edadmaxima, fechainicio, fechafin, fk_vacuna) VALUES (18, 99, '2021-06-01', '2021-10-31', 4);

/* Planes de Vacunacion - Sectores - Total: 28*/
INSERT INTO planes_vacunacion_sectores (fk_plan_vacunacion, fk_sector) VALUES (1, 1);
INSERT INTO planes_vacunacion_sectores (fk_plan_vacunacion, fk_sector) VALUES (1, 2);
INSERT INTO planes_vacunacion_sectores (fk_plan_vacunacion, fk_sector) VALUES (1, 5);
INSERT INTO planes_vacunacion_sectores (fk_plan_vacunacion, fk_sector) VALUES (2, 3);
INSERT INTO planes_vacunacion_sectores (fk_plan_vacunacion, fk_sector) VALUES (3, 3);
INSERT INTO planes_vacunacion_sectores (fk_plan_vacunacion, fk_sector) VALUES (3, 4);
INSERT INTO planes_vacunacion_sectores (fk_plan_vacunacion, fk_sector) VALUES (4, 1);
INSERT INTO planes_vacunacion_sectores (fk_plan_vacunacion, fk_sector) VALUES (4, 2);
INSERT INTO planes_vacunacion_sectores (fk_plan_vacunacion, fk_sector) VALUES (4, 3);
INSERT INTO planes_vacunacion_sectores (fk_plan_vacunacion, fk_sector) VALUES (4, 4);
INSERT INTO planes_vacunacion_sectores (fk_plan_vacunacion, fk_sector) VALUES (4, 5);
INSERT INTO planes_vacunacion_sectores (fk_plan_vacunacion, fk_sector) VALUES (4, 6);
INSERT INTO planes_vacunacion_sectores (fk_plan_vacunacion, fk_sector) VALUES (4, 7);
INSERT INTO planes_vacunacion_sectores (fk_plan_vacunacion, fk_sector) VALUES (4, 8);
INSERT INTO planes_vacunacion_sectores (fk_plan_vacunacion, fk_sector) VALUES (5, 1);
INSERT INTO planes_vacunacion_sectores (fk_plan_vacunacion, fk_sector) VALUES (5, 2);
INSERT INTO planes_vacunacion_sectores (fk_plan_vacunacion, fk_sector) VALUES (5, 3);
INSERT INTO planes_vacunacion_sectores (fk_plan_vacunacion, fk_sector) VALUES (5, 4);
INSERT INTO planes_vacunacion_sectores (fk_plan_vacunacion, fk_sector) VALUES (5, 5);
INSERT INTO planes_vacunacion_sectores (fk_plan_vacunacion, fk_sector) VALUES (5, 6);
INSERT INTO planes_vacunacion_sectores (fk_plan_vacunacion, fk_sector) VALUES (5, 7);
INSERT INTO planes_vacunacion_sectores (fk_plan_vacunacion, fk_sector) VALUES (6, 1);
INSERT INTO planes_vacunacion_sectores (fk_plan_vacunacion, fk_sector) VALUES (6, 2);
INSERT INTO planes_vacunacion_sectores (fk_plan_vacunacion, fk_sector) VALUES (6, 3);
INSERT INTO planes_vacunacion_sectores (fk_plan_vacunacion, fk_sector) VALUES (6, 4);
INSERT INTO planes_vacunacion_sectores (fk_plan_vacunacion, fk_sector) VALUES (6, 5);
INSERT INTO planes_vacunacion_sectores (fk_plan_vacunacion, fk_sector) VALUES (6, 6);
INSERT INTO planes_vacunacion_sectores (fk_plan_vacunacion, fk_sector) VALUES (6, 8);

/* Lotes - Total: 4*/
INSERT INTO lotes (cantidad, cantidaddisponible, fk_proveedor, fk_vacuna) VALUES (50000, 50000, 1, 1);
INSERT INTO lotes (cantidad, cantidaddisponible, fk_proveedor, fk_vacuna) VALUES (30000, 30000, 3, 2);
INSERT INTO lotes (cantidad, cantidaddisponible, fk_proveedor, fk_vacuna) VALUES (50000, 50000, 3, 3);
INSERT INTO lotes (cantidad, cantidaddisponible, fk_proveedor, fk_vacuna) VALUES (80000, 80000, 2, 4);

/*Eventos - Total: 8*/
INSERT INTO eventos (cantidad, detalle, fecha, fk_lote, fk_transportista, fk_vacunatorio) VALUES (1000, 'Evento1', '2021-06-02', 1, 1, 14);
INSERT INTO eventos (cantidad, detalle, fecha, fk_lote, fk_transportista, fk_vacunatorio) VALUES (1000, 'Evento2', '2021-06-02', 2, 1, 26);
INSERT INTO eventos (cantidad, detalle, fecha, fk_lote, fk_transportista, fk_vacunatorio) VALUES (1000, 'Evento3', '2021-06-02', 3, 1, 35);
INSERT INTO eventos (cantidad, detalle, fecha, fk_lote, fk_transportista, fk_vacunatorio) VALUES (1000, 'Evento4', '2021-06-02', 1, 1, 41);
INSERT INTO eventos (cantidad, detalle, fecha, fk_lote, fk_transportista, fk_vacunatorio) VALUES (1000, 'Evento5', '2021-06-02', 4, 1, 1);
INSERT INTO eventos (cantidad, detalle, fecha, fk_lote, fk_transportista, fk_vacunatorio) VALUES (1000, 'Evento5', '2021-06-02', 4, 1, 2);
INSERT INTO eventos (cantidad, detalle, fecha, fk_lote, fk_transportista, fk_vacunatorio) VALUES (1000, 'Evento5', '2021-06-02', 4, 1, 3);
INSERT INTO eventos (cantidad, detalle, fecha, fk_lote, fk_transportista, fk_vacunatorio) VALUES (1000, 'Evento6', '2021-06-02', 1, 1, 4);


/* Usuarios */
/* Administrador */
INSERT INTO usuarios (nombre, correo, password) VALUES ('Administrador', 'admin@admin.com', '$2a$12$z4dj/MDu1T.tiJSJT.11te3iz9VVHawqjxvDIiGShnIg/E8WIcgUO');
INSERT INTO usuarios_roles VALUES (1, 1);
/* Autoridad */
INSERT INTO usuarios (nombre, correo, password) VALUES ('M.S.P.', 'msp@gub.uy', '$2a$12$z4dj/MDu1T.tiJSJT.11te3iz9VVHawqjxvDIiGShnIg/E8WIcgUO');
INSERT INTO usuarios_roles VALUES (2,2);
/* Vacunador */
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Juan', 'Pérez', 'jp@medicina.uy', '12345671', '1980-01-01', 3);
INSERT INTO usuarios_roles VALUES (3, 3);

/*Ciudadanos - Total: 300*/

--1 a 6 años
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Juan', 'Rodríguez', 'juanrodriguez@gmail.com', '70004821', '2021-01-01', 7);
INSERT INTO usuarios_roles VALUES (4, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Benjamin', 'González', 'benjamingonzalez@gmail.com', '69231952', '2020-07-15', 7);
INSERT INTO usuarios_roles VALUES (5, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('María', 'Martínez', 'mariamartinez@gmail.com', '66503933', '2019-10-25', 7);
INSERT INTO usuarios_roles VALUES (6, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Mateo', 'Fernández', 'mateofernandez@gmail.com', '67500554', '2019-12-07', 7);
INSERT INTO usuarios_roles VALUES (7, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Isabella', 'Pérez', 'isabellaperez@gmail.com', '65039855', '2018-03-03', 7);
INSERT INTO usuarios_roles VALUES (8, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Emma', 'García', 'emmagarcia@gmail.com', '65547536', '2018-05-17', 7);
INSERT INTO usuarios_roles VALUES (9, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Valentín', 'Silva', 'valentinsilva@gmail.com', '64627557', '2017-08-15', 7);
INSERT INTO usuarios_roles VALUES (10, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Valentina', 'López', 'valentinalopez@gmail.com', '64083558', '2017-04-10', 7);
INSERT INTO usuarios_roles VALUES (11, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Martina', 'Pereira', 'marginapereira@gmail.com', '63957559', '2016-11-05', 7);
INSERT INTO usuarios_roles VALUES (12, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Catalina', 'Sosa', 'catalinasosa@gmail.com', '61020821', '2015-01-21', 7);
INSERT INTO usuarios_roles VALUES (13, 4);

--7 a 12 años
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Maia', 'Diaz', 'maiadiaz@gmail.com' ,'60062322', '2014-03-01', 7);
INSERT INTO usuarios_roles VALUES (14, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Braian', 'Olivera', 'braianolivera@gmail.com' ,'59572823', '2014-02-02', 7);
INSERT INTO usuarios_roles VALUES (15, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Marisa', 'Gomez', 'marisagomez@gmail.com' ,'59846824', '2014-01-03', 7);
INSERT INTO usuarios_roles VALUES (16, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Fernanda', 'Suárez', 'fernandasuarez@gmail.com' ,'58935825', '2013-12-04', 7);
INSERT INTO usuarios_roles VALUES (17, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Maicol', 'Ferreira', 'maicolferreira@gmail.com' ,'58635826', '2013-11-05', 7);
INSERT INTO usuarios_roles VALUES (18, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Julían', 'Álvarez', 'julianalvarez@gmail.com' ,'58870827', '2013-10-06', 7);
INSERT INTO usuarios_roles VALUES (19, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Pamela', 'Hernández', 'pamelahernandez@gmail.com' ,'58153828', '2012-09-07', 7);
INSERT INTO usuarios_roles VALUES (20, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Brandon', 'Cabrera', 'brandoncabrera@gmail.com' ,'57484829', '2012-08-08', 7);
INSERT INTO usuarios_roles VALUES (21, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Bautista', 'Acosta', 'bautistaacosta@gmail.com' ,'57761821', '2012-07-09', 7);
INSERT INTO usuarios_roles VALUES (22, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Ema', 'Nuñez', 'emanunez@gmail.com' ,'57654222', '2012-06-10', 7);
INSERT INTO usuarios_roles VALUES (23, 4);

INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Renzo', 'Correa', 'renzocorrea@gmail.com' ,'56036523', '2011-05-11', 7);
INSERT INTO usuarios_roles VALUES (24, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Vicente', 'Machado', 'vicentemachado@gmail.com' ,'56476224', '2011-04-12', 7);
INSERT INTO usuarios_roles VALUES (25, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Camilo', 'Sanchez', 'camilisanchez@gmail.com' ,'56123425', '2011-03-13', 7);
INSERT INTO usuarios_roles VALUES (26, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Melany', 'Castro', 'melanycastro@gmail.com' ,'56654326', '2010-02-14', 7);
INSERT INTO usuarios_roles VALUES (27, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Dahiana', 'Cardozo', 'dahianacardozo@gmail.com' ,'56247827', '2010-01-15', 7);
INSERT INTO usuarios_roles VALUES (28, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Pilar', 'Mendez', 'pilarmendez@gmail.com' ,'5635128', '2010-12-16', 7);
INSERT INTO usuarios_roles VALUES (29, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Paulo', 'Moreira', 'paulomoreira@gmail.com' ,'56876529', '2010-11-17', 7);
INSERT INTO usuarios_roles VALUES (30, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Maite', 'Rivero', 'maiterivero@gmail.com' ,'55936220', '2009-10-18', 7);
INSERT INTO usuarios_roles VALUES (31, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Rita', 'De los Santos', 'ritadelossantos@gmail.com' ,'55040021', '2009-09-19', 7);
INSERT INTO usuarios_roles VALUES (32, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Stephanie', 'Pereyra', 'stephaniepereyra@gmail.com' ,'5597222', '2009-08-20', 7);
INSERT INTO usuarios_roles VALUES (33, 4);

-- 13 a 17 
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Flavia', 'Morales', 'flaviamorales@gmail.com' ,'55152322', '2008-07-21', 7);
INSERT INTO usuarios_roles VALUES (34, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Belén', 'Silvera', 'belensilvera@gmail.com' ,'55451523', '2008-06-22', 7);
INSERT INTO usuarios_roles VALUES (35, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Mercedes', 'Silveira', 'mercedessilveira@gmail.com' ,'55636324', '2008-05-23', 7);
INSERT INTO usuarios_roles VALUES (36, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Marina', 'Viera', 'marinaviera@gmail.com' ,'55994225', '2008-04-24', 7);
INSERT INTO usuarios_roles VALUES (37, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Daiana', 'Rodriguez', 'daianarodriguez@gmail.com' ,'54543226', '2007-03-25', 7);
INSERT INTO usuarios_roles VALUES (38, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Elisa', 'Pintos', 'elisapintos@gmail.com' ,'54871027', '2007-02-26', 7);
INSERT INTO usuarios_roles VALUES (39, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Priscila', 'Romero', 'priscilaromero@gmail.com' ,'54432228', '2007-01-27', 7);
INSERT INTO usuarios_roles VALUES (40, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Vanessa', 'Ramos', 'vanessaramos@gmail.com' ,'54623729', '2006-12-28', 7);
INSERT INTO usuarios_roles VALUES (41, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Katherine', 'Da Silva', 'katherinedasilva@gmail.com' ,'54624120', '2006-11-29', 7);
INSERT INTO usuarios_roles VALUES (42, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Valentino', 'Gutiérrez', 'valentinogutierrez@gmail.com' ,'54052931', '2006-09-30', 7);
INSERT INTO usuarios_roles VALUES (43, 4);

INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Yamila', 'Álvez', 'yamilaalvez@gmail.com' ,'53377422', '2005-10-31', 7);
INSERT INTO usuarios_roles VALUES (44, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Damián', 'De León', 'daniandeleon@gmail.com' ,'53229023', '2005-08-01', 7);
INSERT INTO usuarios_roles VALUES (45, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Kiara', 'Ramírez', 'kiararamirez@gmail.com' ,'53715624', '2005-07-02', 7);
INSERT INTO usuarios_roles VALUES (46, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Fabricio', 'Torres', 'fabriciotorres@gmail.com' ,'53020825', '2005-06-03', 7);
INSERT INTO usuarios_roles VALUES (47, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Emma', 'Delgado', 'emmadelgado@gmail.com' ,'53098726', '2005-05-04', 7);
INSERT INTO usuarios_roles VALUES (48, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Máximo', 'Duarte', 'maximoduarte@gmail.com' ,'53012927', '2004-04-05', 7);
INSERT INTO usuarios_roles VALUES (49, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Humberto', 'Medina', 'humbertomedina@gmail.com' ,'53837428', '2004-03-06', 7);
INSERT INTO usuarios_roles VALUES (50, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Melanie', 'Techera', 'malanietechera@gmail.com' ,'53734129', '2004-02-07', 7);
INSERT INTO usuarios_roles VALUES (51, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Delfina', 'Benitez', 'delfinabenitez@gmail.com' ,'53743220', '2004-01-08', 7);
INSERT INTO usuarios_roles VALUES (52, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Melina', 'Vázquez', 'melinavazquez@gmail.com' ,'53885621', '2004-12-09', 7);
INSERT INTO usuarios_roles VALUES (53, 4);

--18 a 49
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Michael', 'Larrosa', 'michellarrosa@gmail.com' ,'53000002', '2003-11-10', 1);
INSERT INTO usuarios_roles VALUES (54, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Esther', 'Piriz', 'estherpiriz@gmail.com' ,'53094302', '2003-10-11', 1);
INSERT INTO usuarios_roles VALUES (55, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Diana', 'Marquez', 'dianamarquez@gmail.com' ,'53522003', '2003-09-12', 2);
INSERT INTO usuarios_roles VALUES (56, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Rossana', 'Barboza', 'rossanabarboza@gmail.com' ,'52489004', '2002-08-13', 4);
INSERT INTO usuarios_roles VALUES (57, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Edison', 'Costa', 'edisoncosta@gmail.com' ,'52002505', '2002-07-14', 5);
INSERT INTO usuarios_roles VALUES (58, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Ramona', 'Santos', 'ramonasantos@gmail.com' ,'52166006', '2002-06-15', 7);
INSERT INTO usuarios_roles VALUES (59, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Liliana', 'Giménez', 'lilianagimenez@gmail.com' ,'52357007', '2001-05-16', 8);
INSERT INTO usuarios_roles VALUES (60, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Inés', 'Dos Santos', 'inesdossantos@gmail.com' ,'52268508', '2001-04-17', 7);
INSERT INTO usuarios_roles VALUES (61, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Luz', 'Lima', 'luzlima@gmail.com' ,'52274709', '2001-03-18', 7);
INSERT INTO usuarios_roles VALUES (62, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Brenda', 'Rocha', 'brendarocha@gmail.com' ,'52000000', '2000-02-19', 4);
INSERT INTO usuarios_roles VALUES (63, 4);

INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Celia', 'Barrios', 'celiabarrios@gmail.com' ,'51027601', '2000-01-20', 5);
INSERT INTO usuarios_roles VALUES (64, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Ismael', 'Araujo', 'ismaelaraujo@gmail.com' ,'51011402', '2000-12-21', 7);
INSERT INTO usuarios_roles VALUES (65, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Ramiro', 'Perdomo', 'ramiroperdomo@gmail.com' ,'51078903', '1999-11-22', 2);
INSERT INTO usuarios_roles VALUES (66, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Emilio', 'Ruiz', 'emilioruiz@gmail.com' ,'51125004', '1999-10-23', 7);
INSERT INTO usuarios_roles VALUES (67, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Valentín', 'Flores', 'valentinflores@gmail.com' ,'51774005', '1999-08-24', 4);
INSERT INTO usuarios_roles VALUES (68, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('María de los Milagros', 'Reyes', 'mariadelosmilagrosreyes@gmail.com' ,'51452006', '1998-07-25', 2);
INSERT INTO usuarios_roles VALUES (69, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Anthony', 'Curbelo', 'anthonycurbelo@gmail.com' ,'51063607', '1998-06-26', 4);
INSERT INTO usuarios_roles VALUES (70, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Paulina', 'Alonso', 'paulinaalonso@gmail.com' ,'50014708', '1998-02-27', 5);
INSERT INTO usuarios_roles VALUES (71, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Edgardo', 'Blanco', 'edgardoblancos@gmail.com' ,'50017309', '1997-04-28', 1);
INSERT INTO usuarios_roles VALUES (72, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Lorenzo', 'Varela', 'lorenzovarela@gmail.com' ,'50013600', '1997-05-29', 2);
INSERT INTO usuarios_roles VALUES (73, 4);

INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Lidia', 'Barreto', 'lidiabarreto@gmail.com' ,'50013601', '1997-03-30', 7);
INSERT INTO usuarios_roles VALUES (74, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Yolanda', 'Cáceres', 'yolandacaceres@gmail.com' ,'50017102', '1996-01-31', 3);
INSERT INTO usuarios_roles VALUES (75, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Elías', 'Dominguez', 'eliasdominguez@gmail.com' ,'50083503', '1996-12-01', 3);
INSERT INTO usuarios_roles VALUES (76, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Fiorella', 'Ortiz', 'fiorellaortiz@gmail.com' ,'50024804', '1996-11-02', 5);
INSERT INTO usuarios_roles VALUES (77, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Manuela', 'Fagundez', 'manuelafagundez@gmail.com' ,'49002105', '1995-10-03', 6);
INSERT INTO usuarios_roles VALUES (78, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Nadia', 'Peña', 'nadiapenia@gmail.com' ,'49670356', '1995-09-04', 7);
INSERT INTO usuarios_roles VALUES (79, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Evelyn', 'Castillo', 'evelyncastillo@gmail.com' ,'49012307', '1995-08-05', 2);
INSERT INTO usuarios_roles VALUES (80, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Eliana', 'Velázquez', 'elianavelazquez@gmail.com' ,'49065208', '1995-07-06', 5);
INSERT INTO usuarios_roles VALUES (81, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Alexander', 'Vidal', 'algo@gmail.com' ,'49075209', '1994-06-07', 5);
INSERT INTO usuarios_roles VALUES (82, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Luisa', 'Moreno', 'luisamoreno@gmail.com' ,'49763000', '1994-05-08', 7);
INSERT INTO usuarios_roles VALUES (83, 4);

INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Darío', 'Soria', 'dariosoria@gmail.com' ,'49246001', '1994-04-09', 7);
INSERT INTO usuarios_roles VALUES (84, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Anibal', 'Molina', 'anibalmolina@gmail.com' ,'48123402', '1993-03-10', 2);
INSERT INTO usuarios_roles VALUES (85, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Nilda', 'Santana', 'nildasantana@gmail.com' ,'48624003', '1993-02-11', 4);
INSERT INTO usuarios_roles VALUES (86, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Luana', 'Lemos', 'luanalemos@gmail.com' ,'48864604', '1993-01-12', 7);
INSERT INTO usuarios_roles VALUES (87, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Stella', 'Acuña', 'stellaacuna@gmail.com' ,'48003755', '1992-12-13', 6);
INSERT INTO usuarios_roles VALUES (88, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Iván', 'Borges', 'ivanborges@gmail.com' ,'47024606', '1992-11-14', 3);
INSERT INTO usuarios_roles VALUES (89, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('David', 'Bentancor', 'davidbentancor@gmail.com' ,'46044407', '1992-10-15', 3);
INSERT INTO usuarios_roles VALUES (90, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Marcela', 'Franco', 'marcelafranco@gmail.com' ,'45053508', '1991-09-16', 6);
INSERT INTO usuarios_roles VALUES (91, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Shirley', 'Muñoz', 'shirleymunoz@gmail.com' ,'45025109', '1991-08-17', 7);
INSERT INTO usuarios_roles VALUES (92, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Josefina', 'Da Rosa', 'josefinadarosa@gmail.com' ,'45076800', '1991-07-18', 2);
INSERT INTO usuarios_roles VALUES (93, 4);

INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Analía', 'Caballero', 'analiacaballero@gmail.com' ,'44012301', '1990-06-19', 1);
INSERT INTO usuarios_roles VALUES (94, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Horacio', 'Vera', 'horaciovera@gmail.com' ,'43543002', '1990-05-20', 2);
INSERT INTO usuarios_roles VALUES (95, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Hilda', 'Ríos', 'hildarios@gmail.com' ,'43007543', '1990-04-21', 4);
INSERT INTO usuarios_roles VALUES (96, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Axel', 'Fleitas', 'axelfleitas@gmail.com' ,'42056904', '1989-03-22', 1);
INSERT INTO usuarios_roles VALUES (97, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Clara', 'Montero', 'claramontero@gmail.com' ,'42423005', '1989-02-23', 7);
INSERT INTO usuarios_roles VALUES (98, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Alex', 'Britos', 'alexbritos@gmail.com' ,'42157006', '1989-01-24', 4);
INSERT INTO usuarios_roles VALUES (99, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Ezequiel', 'Vargas', 'ezequielvargas@gmail.com' ,'41003757', '1988-12-25', 1);
INSERT INTO usuarios_roles VALUES (100, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Myriam', 'Antunez', 'myriamantunez@gmail.com' ,'41006528', '1988-11-26', 2);
INSERT INTO usuarios_roles VALUES (101, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Ariel', 'Carballo', 'arielcarballo@gmail.com' ,'40005429', '1988-10-27', 1);
INSERT INTO usuarios_roles VALUES (102, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('William', 'Godoy', 'williamgodoy@gmail.com' ,'39415000', '1987-09-28', 3);
INSERT INTO usuarios_roles VALUES (103, 4);

INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Alan', 'Almeida', 'alanalmeida@gmail.com' ,'38662201', '1986-08-29', 5);
INSERT INTO usuarios_roles VALUES (104, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Lilian', 'Umpiérrez', 'lilianumpierrez@gmail.com' ,'37578302', '1986-07-31', 7);
INSERT INTO usuarios_roles VALUES (105, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Teresita', 'Quintana', 'teresitaquintana@gmail.com' ,'37876503', '1985-06-30', 4);
INSERT INTO usuarios_roles VALUES (106, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Erika', 'Aguirre', 'erikaaguirre@gmail.com' ,'36765004', '1984-05-01', 5);
INSERT INTO usuarios_roles VALUES (107, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Sabrina', 'Farías', 'sabrinafarias@gmail.com' ,'35123005', '1983-04-02', 3);
INSERT INTO usuarios_roles VALUES (108, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Rosana', 'Bonilla', 'rosanabonilla@gmail.com' ,'34947006', '1982-03-03', 6);
INSERT INTO usuarios_roles VALUES (109, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Tatiana', 'Miranda', 'tatianamiranda@gmail.com' ,'33135007', '1981-02-04', 4);
INSERT INTO usuarios_roles VALUES (110, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Emilia', 'Rosa', 'emilianarosa@gmail.com' ,'33033208', '1980-01-05', 5);
INSERT INTO usuarios_roles VALUES (111, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Mabel', 'Vega', 'mabelvega@gmail.com' ,'33007909', '1979-12-06', 2);
INSERT INTO usuarios_roles VALUES (112, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Zulma', 'Muniz', 'zilmamuniz@gmail.com' ,'33099900', '1978-11-07', 6);
INSERT INTO usuarios_roles VALUES (113, 4);

INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Catalina', 'Cuello', 'catalinacuello@gmail.com' ,'33636001', '1977-10-08', 2);
INSERT INTO usuarios_roles VALUES (114, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Iris', 'Herrera', 'irisherrera@gmail.com' ,'320225002', '1976-09-09', 6);
INSERT INTO usuarios_roles VALUES (115, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Elbio', 'Maciel', 'elbiomaciel@gmail.com' ,'320136003', '1976-08-10', 5);
INSERT INTO usuarios_roles VALUES (116, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Fabian', 'Cruz', 'fabiancruz@gmail.com' ,'32663004', '1976-07-11', 6);
INSERT INTO usuarios_roles VALUES (117, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Fabiana', 'Caraballo', 'fabianacaraballo@gmail.com' ,'32251005', '1976-06-12', 6);
INSERT INTO usuarios_roles VALUES (118, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Eva', 'Sena', 'elvasena@gmail.com' ,'32749006', '1975-05-13', 4);
INSERT INTO usuarios_roles VALUES (119, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('María del Rosario', 'Rojas', 'mariadelrosariorojas@gmail.com' ,'32853007', '1975-04-14', 2);
INSERT INTO usuarios_roles VALUES (120, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Nélida', 'Tejera', 'nelidatejera@gmail.com' ,'32865008', '1975-03-15', 3);
INSERT INTO usuarios_roles VALUES (121, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Nahuel', 'Guerra', 'nahuelguerra@gmail.com' ,'31374009', '1974-02-16', 1);
INSERT INTO usuarios_roles VALUES (122, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Santino', 'Paz', 'santinopaz@gmail.com' ,'31264000', '1973-01-17', 6);
INSERT INTO usuarios_roles VALUES (123, 4);

INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Enzo', 'Arias', 'enzoarias@gmail.com' ,'31853001', '1973-12-18', 6);
INSERT INTO usuarios_roles VALUES (124, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Viviana', 'Peralta', 'vivianaperalta@gmail.com' ,'31959002', '1973-11-19', 1);
INSERT INTO usuarios_roles VALUES (125, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Rocío', 'Batista', 'rociobatista@gmail.com' ,'31235003', '1972-10-20', 2);
INSERT INTO usuarios_roles VALUES (126, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Adrián', 'Davila', 'adriandavila@gmail.com' ,'31865004', '1972-09-21', 6);
INSERT INTO usuarios_roles VALUES (127, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Brian', 'Souza', 'briansouza@gmail.com' ,'31889405', '1972-08-22', 4);
INSERT INTO usuarios_roles VALUES (128, 4);

--50 a 69
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('German', 'Fuentes', 'germanfuentes@gmail.com' ,'30774406', '1971-07-23', 1);
INSERT INTO usuarios_roles VALUES (129, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Antonella', 'Rosas', 'antonellarosas@gmail.com' ,'30735007', '1971-06-24', 2);
INSERT INTO usuarios_roles VALUES (130, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Mirtha', 'Leites', 'mirthaleites@gmail.com' ,'30909008', '1971-01-25', 3);
INSERT INTO usuarios_roles VALUES (131, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Alexis', 'Espinosa', 'alexisespinosa@gmail.com' ,'30525209', '1971-05-26', 4);
INSERT INTO usuarios_roles VALUES (132, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Margarita', 'Aguiar', 'margaritaaguiar@gmail.com' ,'30817200', '1971-04-27', 5);
INSERT INTO usuarios_roles VALUES (133, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Mathias', 'Corbo', 'mathiascorbo@gmail.com' ,'30552001', '1970-02-28', 6);
INSERT INTO usuarios_roles VALUES (134, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Raquel', 'Rey', 'raquelrey@gmail.com' ,'30013502', '1970-03-29', 7);
INSERT INTO usuarios_roles VALUES (135, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Isabel', 'Camejo', 'isabelcamejo@gmail.com' ,'30846003', '1970-01-30', 8);
INSERT INTO usuarios_roles VALUES (136, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Mía', 'Aguiar', 'miaaguiar@gmail.com' ,'30012304', '1970-03-31', 3);
INSERT INTO usuarios_roles VALUES (137, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Elizabeth', 'Leal', 'elizabethleal@gmail.com' ,'30135705', '1970-02-01', 6);
INSERT INTO usuarios_roles VALUES (138, 4);

INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Karina', 'Clavijo', 'karinaclavijo@gmail.com' ,'30111006', '1970-01-02', 4);
INSERT INTO usuarios_roles VALUES (139, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Robert', 'Ribeiro', 'robetribeiro@gmail.com' ,'30662007', '1970-12-03', 1);
INSERT INTO usuarios_roles VALUES (140, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Milton', 'Furtado', 'miltonfurtado@gmail.com' ,'29452008', '1969-11-04', 2);
INSERT INTO usuarios_roles VALUES (141, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Omar', 'Acevedo', 'omaracevedo@gmail.com' ,'29273509', '1969-10-05', 7);
INSERT INTO usuarios_roles VALUES (142, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Noelia', 'Alves', 'noeliaalves@gmail.com' ,'29977000', '1969-09-06', 3);
INSERT INTO usuarios_roles VALUES (143, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Tomás', 'Iglesias', 'tomasiglesias@gmail.com' ,'29034501', '1969-08-07', 7);
INSERT INTO usuarios_roles VALUES (144, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Rodolfo', 'Figueroa', 'rodolfofigueroa@gmail.com' ,'29695021', '1969-07-08', 4);
INSERT INTO usuarios_roles VALUES (145, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Mauro', 'De Souza', 'maurodesouza@gmail.com' ,'29077703', '1969-06-09', 6);
INSERT INTO usuarios_roles VALUES (146, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Cristina', 'Colman', 'cristinacolman@gmail.com' ,'29123004', '1969-05-10', 7);
INSERT INTO usuarios_roles VALUES (147, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Sara', 'Bueno', 'sarabueno@gmail.com' ,'29876005', '1969-04-11', 6);
INSERT INTO usuarios_roles VALUES (148, 4);

INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Rafael', 'Ferrari', 'rafaelferrari@gmail.com' ,'29523006', '1969-03-12', 6);
INSERT INTO usuarios_roles VALUES (149, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Ernesto', 'Mesa', 'ernestomesa@gmail.com' ,'29883007', '1968-02-13', 6);
INSERT INTO usuarios_roles VALUES (150, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Elida', 'Camacho', 'elidacamacho@gmail.com' ,'29987008', '1968-01-14', 6);
INSERT INTO usuarios_roles VALUES (151, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Mariela', 'Villalba', 'marielavillalba@gmail.com' ,'29765009', '1968-12-15', 6);
INSERT INTO usuarios_roles VALUES (152, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Carla', 'Rivas', 'carlarivas@gmail.com' ,'29523000', '1968-11-16', 4);
INSERT INTO usuarios_roles VALUES (153, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Elena', 'Bermudez', 'elenabermudez@gmail.com' ,'29765001', '1968-10-17', 1);
INSERT INTO usuarios_roles VALUES (154, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Gastón', 'Luzardo', 'gastonluzardo@gmail.com' ,'29123002', '1968-09-18', 2);
INSERT INTO usuarios_roles VALUES (155, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Luciano', 'Pacheco', 'lucianpacheco@gmail.com' ,'29028703', '1968-08-19', 5);
INSERT INTO usuarios_roles VALUES (156, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Paola', 'Melo', 'paolamelo@gmail.com' ,'29062604', '1968-07-20', 8);
INSERT INTO usuarios_roles VALUES (157, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Estela', 'Valdez', 'estelavaldez@gmail.com' ,'29747405', '1968-06-21', 2);
INSERT INTO usuarios_roles VALUES (158, 4);

INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Kevin', 'Cabral', 'kevincabral@gmail.com' ,'29022206', '1968-05-22', 1);
INSERT INTO usuarios_roles VALUES (159, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Milagros', 'Ojeda', 'milagrosojeda@gmail.com' ,'29000007', '1968-04-23', 2);
INSERT INTO usuarios_roles VALUES (160, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Leandro', 'Ayala', 'leandroayala@gmail.com' ,'28425008', '1968-03-24', 3);
INSERT INTO usuarios_roles VALUES (161, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Irma', 'Guerrero', 'irmaguerrero@gmail.com' ,'28044009', '1967-02-25', 4);
INSERT INTO usuarios_roles VALUES (162, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Benjamín', 'Ibarra', 'benjaminibarra@gmail.com' ,'28996000', '1967-01-27', 5);
INSERT INTO usuarios_roles VALUES (163, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Lourdes', 'Gallo', 'lourdesgallo@gmail.com' ,'28738001', '1967-12-28', 6);
INSERT INTO usuarios_roles VALUES (164, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Emiliano', 'Toledo', 'emilianatoledo@gmail.com' ,'28999002', '1967-11-29', 6);
INSERT INTO usuarios_roles VALUES (165, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Christian', 'Mora', 'christianmora@gmail.com' ,'28351003', '1966-09-30', 6);
INSERT INTO usuarios_roles VALUES (166, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Gloria', 'Estevez', 'gloriaestevez@gmail.com' ,'28276004', '1966-10-31', 6);
INSERT INTO usuarios_roles VALUES (167, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Luciana', 'Mederos', 'lucianmederos@gmail.com' ,'28144005', '1966-07-05', 6);
INSERT INTO usuarios_roles VALUES (168, 4);

INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Julia', 'Durán', 'juliaduran@gmail.com' ,'28000006', '1966-06-10', 7);
INSERT INTO usuarios_roles VALUES (169, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Teresa', 'Pirez', 'teresapirez@gmail.com' ,'27727007', '1965-05-09', 1);
INSERT INTO usuarios_roles VALUES (170, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Nancy', 'Conde', 'nancyconde@gmail.com' ,'27906008', '1965-04-08', 2);
INSERT INTO usuarios_roles VALUES (171, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Karen', 'Quiroga', 'karenquiroga@gmail.com' ,'27777009', '1965-03-07', 3);
INSERT INTO usuarios_roles VALUES (172, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Claudio', 'Amaral', 'claudioamaral@gmail.com' ,'27876000', '1965-02-06', 4);
INSERT INTO usuarios_roles VALUES (173, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Norma', 'Bentancur', 'normabentancur@gmail.com' ,'2701230-', '1965-01-05', 5);
INSERT INTO usuarios_roles VALUES (174, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Miriam', 'Sequeira', 'miriamsequeira@gmail.com' ,'27073701', '1964-12-04', 5);
INSERT INTO usuarios_roles VALUES (175, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Virginia', 'Escobar', 'virginiaescobar@gmail.com' ,'27012302', '1964-11-03', 6);
INSERT INTO usuarios_roles VALUES (176, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Jessica', 'Ledesma', 'jessicaledesma@gmail.com' ,'27012303', '1964-10-02', 6);
INSERT INTO usuarios_roles VALUES (177, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Leticia', 'Da Costa', 'liticiadacosta@gmail.com' ,'27000004', '1964-09-01', 6); 
INSERT INTO usuarios_roles VALUES (178, 4);

INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Micaela', 'Maidana', 'micaelamaidana@gmail.com' ,'26177905', '1963-08-31', 6);
INSERT INTO usuarios_roles VALUES (179, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Felipe', 'Trinidad', 'felipetrinidad@gmail.com' ,'26765006', '1963-07-30', 6);
INSERT INTO usuarios_roles VALUES (180, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Esteban', 'Denis', 'estebandenis@gmail.com' ,'26185007', '1963-06-29', 7);
INSERT INTO usuarios_roles VALUES (181, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Daniela', 'Falero', 'danielafalero@gmail.com' ,'26682008', '1963-05-28', 8);
INSERT INTO usuarios_roles VALUES (182, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Wilson', 'Burgos', 'wilsonburgos@gmail.com' ,'26523009', '1962-04-27', 8);
INSERT INTO usuarios_roles VALUES (183, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Maximiliano', 'Cedres', 'maximilianocedres@gmail.com' ,'26846000', '1962-03-26', 2);
INSERT INTO usuarios_roles VALUES (184, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Silvana', 'Amaro', 'silvanaamaro@gmail.com' ,'26624001', '1962-02-25', 3);
INSERT INTO usuarios_roles VALUES (185, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Alejandra', 'Maldonado', 'alejandramaldonado@gmail.com' ,'26000002', '1962-01-24', 4);
INSERT INTO usuarios_roles VALUES (186, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Martha', 'Dutra', 'marthadutra@gmail.com' ,'25848003', '1961-12-23', 5);
INSERT INTO usuarios_roles VALUES (187, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Gerardo', 'Navarro', 'gerardonavarro@gmail.com' ,'25123004', '1961-11-22', 7);
INSERT INTO usuarios_roles VALUES (188, 4);

INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Alfredo', 'Fontes', 'alfredofontes@gmail.com' ,'25937005', '1961-10-21', 6);
INSERT INTO usuarios_roles VALUES (189, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Cecilia', 'Rosano', 'ceciliarosano@gmail.com' ,'25630006', '1961-09-20', 6);
INSERT INTO usuarios_roles VALUES (190, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Ángela', 'Villar', 'angelavillar@gmail.com' ,'25666007', '1960-08-19', 6);
INSERT INTO usuarios_roles VALUES (191, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Lautaro', 'Abreu', 'lautaroabreu@gmail.com' ,'25123008', '1960-07-18', 8);
INSERT INTO usuarios_roles VALUES (192, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Valeria', 'Lorenzo', 'valerialorenzo@gmail.com' ,'25035409', '1960-06-17', 8);
INSERT INTO usuarios_roles VALUES (193, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Lorena', 'Figueredo', 'lorenafigueredo@gmail.com' ,'24000000', '1960-05-16', 8); 
INSERT INTO usuarios_roles VALUES (194, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Paula', 'Machín', 'paulamachin@gmail.com' ,'24009651', '1959-04-15', 2);
INSERT INTO usuarios_roles VALUES (195, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Nelly', 'Ocampo', 'nellyocampo@gmail.com' ,'24333002', '1959-03-14', 1);
INSERT INTO usuarios_roles VALUES (196, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Carolina', 'Mendoza', 'carolinamendoza@gmail.com' ,'24055503', '1959-02-13', 3);
INSERT INTO usuarios_roles VALUES (197, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Elsa', 'Mello', 'elsamello@gmail.com' ,'24013104', '1959-01-12', 4);
INSERT INTO usuarios_roles VALUES (198, 4);

INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Mauricio', 'Piñeiro', 'mauriciopineiro@gmail.com' ,'24012405', '1959-12-11', 8);
INSERT INTO usuarios_roles VALUES (199, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Jonathan', 'Otero', 'jonathanotero@gmail.com' ,'24077306', '1958-11-10', 8);
INSERT INTO usuarios_roles VALUES (200, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Juana', 'Prieto', 'juanaprieto@gmail.com' ,'24865007', '1958-10-09', 8);
INSERT INTO usuarios_roles VALUES (201, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Sonia', 'Tabárez', 'soniatabarez@gmail.com' ,'24634008', '1958-09-08', 8);
INSERT INTO usuarios_roles VALUES (202, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Javier', 'Rossi', 'javierrossi@gmail.com' ,'24994009', '1958-08-07', 1);
INSERT INTO usuarios_roles VALUES (203, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Victoria', 'Alfonso', 'victoriaalfonso@gmail.com' ,'24848000', '1958-07-06', 2);
INSERT INTO usuarios_roles VALUES (204, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Mirta', 'Moraes', 'mirtamoraes@gmail.com' ,'24111001', '1957-06-05', 4);
INSERT INTO usuarios_roles VALUES (205, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Mary', 'Saravia', 'marysaravia@gmail.com' ,'24773002', '1957-04-04', 5);
INSERT INTO usuarios_roles VALUES (206, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Alba', 'Marrero', 'albamarrero@gmail.com' ,'24633003', '1957-03-03', 3);
INSERT INTO usuarios_roles VALUES (207, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Martina', 'Baez', 'martinabaez@gmail.com' ,'24567004', '1957-02-02', 6);
INSERT INTO usuarios_roles VALUES (208, 4);

INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Ramón', 'Nieves', 'ramonnieves@gmail.com' ,'24375005', '1956-01-01', 6);
INSERT INTO usuarios_roles VALUES (209, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Enrique', 'Carrasco', 'enriquecarrasco@gmail.com' ,'24011106', '1956-07-31', 6);
INSERT INTO usuarios_roles VALUES (210, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('César', 'Sierra', 'cesarsierra@gmail.com' ,'24043607', '1956-06-30', 6);
INSERT INTO usuarios_roles VALUES (211, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Guillermo', 'Camargo', 'guillermocamargo@gmail.com' ,'23937008', '1956-05-29', 8);
INSERT INTO usuarios_roles VALUES (212, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('María del Carmen', 'Almada', 'mariadelcarmenalmada@gmail.com' ,'23723009', '1955-04-28', 6);
INSERT INTO usuarios_roles VALUES (213, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Manuel', 'Collazo', 'manuelcollazo@gmail.com' ,'23421000', '1955-03-27', 6);
INSERT INTO usuarios_roles VALUES (214, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Antonio', 'Aquino', 'antonioaquino@gmail.com' ,'23443001', '1955-02-26', 8);
INSERT INTO usuarios_roles VALUES (215, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Cristian', 'Soto', 'cristiansoto@gmail.com' ,'23351002', '1955-01-25', 1);
INSERT INTO usuarios_roles VALUES (216, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Thiago', 'Gularte', 'thiagogularte@gmail.com' ,'23044103', '1954-12-24', 2);
INSERT INTO usuarios_roles VALUES (217, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Alberto', 'Campos', 'albertocampos@gmail.com' ,'23001234', '1954-11-23', 3);
INSERT INTO usuarios_roles VALUES (218, 4);

INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Julieta', 'Freitas', 'julietafreitas@gmail.com' ,'23999005', '1954-10-22', 4);
INSERT INTO usuarios_roles VALUES (219, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Richard', 'Ávila', 'richardavila@gmail.com' ,'23789006', '1954-09-21', 5);
INSERT INTO usuarios_roles VALUES (220, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Carmen', 'Brun', 'carmenbrun@gmail.com' ,'23689007', '1953-08-20', 6);
INSERT INTO usuarios_roles VALUES (221, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Franco', 'Severo', 'francosevero@gmail.com' ,'23425008', '1953-07-19', 8);
INSERT INTO usuarios_roles VALUES (222, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Marta', 'Noble', 'martanoble@gmail.com' ,'23267009', '1953-06-18', 1);
INSERT INTO usuarios_roles VALUES (223, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Mateo', 'Garrido', 'mateogarrido@gmail.com' ,'22520770', '1953-05-17', 2);
INSERT INTO usuarios_roles VALUES (224, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Romina', 'Montes de Oca', 'rominamontesdeoca@gmail.com' ,'22123001', '1952-04-16', 6);
INSERT INTO usuarios_roles VALUES (225, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Ignacio', 'Bravo', 'ignaciobravo@gmail.com' ,'22554002', '1952-03-15', 6);
INSERT INTO usuarios_roles VALUES (226, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Mariana', 'Barreiro', 'marianabarreiro@gmail.com' ,'22234003', '1952-02-14', 6);
INSERT INTO usuarios_roles VALUES (227, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Raúl', 'Gil', 'raulgil@gmail.com' ,'21634004', '1952-01-13', 6);
INSERT INTO usuarios_roles VALUES (228, 4);

--70 a 110
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Gabriela', 'Texeira', 'gabrielatexeira@gmail.com' ,'21844005', '1951-12-12', 6);
INSERT INTO usuarios_roles VALUES (229, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Gabriel', 'Román', 'gabrielroman@gmail.com' ,'21666006', '1951-11-11', 1);
INSERT INTO usuarios_roles VALUES (230, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Marcos', 'Deleón', 'marcosdeleon@gmail.com' ,'21543007', '1951-10-10', 6);
INSERT INTO usuarios_roles VALUES (231, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Agustina', 'Trinidade', 'agustinatrinidade@gmail.com' ,'21732008', '1951-09-09', 2);
INSERT INTO usuarios_roles VALUES (232, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Nestor', 'Gadea', 'nestorgadea@gmail.com' ,'21016609', '1951-08-08', 6);
INSERT INTO usuarios_roles VALUES (233, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Olga', 'Carbajal', 'olgacarbajal@gmail.com' ,'20356000', '1951-07-07', 3);
INSERT INTO usuarios_roles VALUES (234, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Bruno', 'Cardoso', 'brunocardoso@gmail.com' ,'20099901', '1950-06-06', 6);
INSERT INTO usuarios_roles VALUES (235, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Ángel', 'Fonseca', 'angelfonseca@gmail.com' ,'200789002', '1950-05-05', 4);
INSERT INTO usuarios_roles VALUES (236, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Joaquín', 'Barrera', 'joaquinbarrera@gmail.com' ,'20067803', '1950-04-04', 6);
INSERT INTO usuarios_roles VALUES (237, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Patricia', 'De Armas', 'patriciadearmas@gmail.com' ,'20058504', '1950-03-03', 8);
INSERT INTO usuarios_roles VALUES (238, 4);

INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Andrés', 'Vaz', 'andresvaz@gmail.com' ,'20198005', '1950-02-02', 8);
INSERT INTO usuarios_roles VALUES (239, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Graciela', 'Lucas', 'gracielalucas@gmail.com' ,'19073406', '1949-01-01', 6);
INSERT INTO usuarios_roles VALUES (240, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Alicia', 'Villanueva', 'aliciavillanueva@gmail.com' ,'19000007', '1949-12-31', 5);
INSERT INTO usuarios_roles VALUES (241, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Valentina', 'Saavedra', 'valentinasaavedra@gmail.com' ,'19823008', '1949-11-30', 8);
INSERT INTO usuarios_roles VALUES (242, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Rosa', 'Segovia', 'rosasegovia@gmail.com' ,'18651009', '1949-10-29', 4);
INSERT INTO usuarios_roles VALUES (243, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Verónica', 'Ibañez', 'veronicaibanez@gmail.com' ,'18012350', '1949-09-28', 3);
INSERT INTO usuarios_roles VALUES (244, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Nelson', 'Carro', 'nelsoncarro@gmail.com' ,'18123401', '1949-08-27', 2);
INSERT INTO usuarios_roles VALUES (245, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Washington', 'Sellanes', 'washingtonsellanes@gmail.com' ,'18689002', '1949-07-26', 1);
INSERT INTO usuarios_roles VALUES (246, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Susana', 'Centurión', 'susanacenturion@gmail.com' ,'18083503', '1948-06-25', 6);
INSERT INTO usuarios_roles VALUES (247, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Sebastián', 'Parodi', 'sebastianparodi@gmail.com' ,'18062404', '1948-05-24', 8);
INSERT INTO usuarios_roles VALUES (248, 4);

INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Rodrigo', 'Macedo', 'rodrigomacedo@gmail.com' ,'18124505', '1948-04-23', 4);
INSERT INTO usuarios_roles VALUES (249, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Agustín', 'Terra', 'agustinterra@gmail.com' ,'18077706', '1948-03-22', 5);
INSERT INTO usuarios_roles VALUES (250, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Álvaro', 'Roldán', 'alvaroroldan@gmail.com' ,'17666007', '1947-02-21', 6);
INSERT INTO usuarios_roles VALUES (251, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Francisco', 'Nunez', 'francisconunez@gmail.com' ,'17555008', '1947-01-20', 1);
INSERT INTO usuarios_roles VALUES (252, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Leonardo', 'Monzón', 'leonardomonzon@gmail.com' ,'17444009', '1947-12-19', 6);
INSERT INTO usuarios_roles VALUES (253, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Víctor', 'Menéndez', 'victormenendez@gmail.com' ,'17333000', '1946-11-18', 8);
INSERT INTO usuarios_roles VALUES (254, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Pedro', 'Aranda', 'pedroaranda@gmail.com' ,'17431001', '1946-10-17', 6);
INSERT INTO usuarios_roles VALUES (255, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Ricardo', 'Meneses', 'ricardomeneses@gmail.com' ,'17743002', '1945-09-16', 2);
INSERT INTO usuarios_roles VALUES (256, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Federico', 'San Martín', 'federicosanmartin@gmail.com' ,'17642003', '1945-08-15', 6);
INSERT INTO usuarios_roles VALUES (257, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Florencia', 'Chagas', 'florenciachagas@gmail.com' ,'16421004', '1944-07-14', 8);
INSERT INTO usuarios_roles VALUES (258, 4);

INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Mónica', 'Nogueira', 'monicanogueira@gmail.com' ,'16513005', '1944-06-13', 3);
INSERT INTO usuarios_roles VALUES (259, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Adriana', 'Magallanes', 'adrianamagallanes@gmail.com' ,'16312006', '1944-05-12', 4);
INSERT INTO usuarios_roles VALUES (260, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Gonzalo', 'Marichal', 'gonzalomarichal@gmail.com' ,'16023007', '1943-04-11', 5);
INSERT INTO usuarios_roles VALUES (261, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Camila', 'Zapata', 'camilazapata@gmail.com' ,'16414008', '1943-03-10', 6);
INSERT INTO usuarios_roles VALUES (262, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Natalia', 'Piñeyro', 'nataliapiñeyro@gmail.com' ,'15626009', '1943-02-09', 8);
INSERT INTO usuarios_roles VALUES (263, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Gladys', 'Andrade', 'gladysandrade@gmail.com' ,'15949000', '1942-01-08', 6);
INSERT INTO usuarios_roles VALUES (264, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Martín', 'Pedrozo', 'martinpedrozo@gmail.com' ,'15848001', '1942-12-07', 6);
INSERT INTO usuarios_roles VALUES (265, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Facundo', 'Vila', 'facundovila@gmail.com' ,'15247002', '1942-11-06', 8);
INSERT INTO usuarios_roles VALUES (266, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Sandra', 'Viana', 'sandraviana@gmail.com' ,'14111003', '1941-10-05', 8);
INSERT INTO usuarios_roles VALUES (267, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Alejandro', 'Galván', 'alejandrogalvan@gmail.com' ,'14663204', '1941-09-04', 6);
INSERT INTO usuarios_roles VALUES (268, 4);

INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Roberto', 'Portillo', 'robertoportillo@gmail.com' ,'14233005', '1941-08-03', 5);
INSERT INTO usuarios_roles VALUES (269, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Hugo', 'Chaves', 'hugochaves@gmail.com' ,'13024606', '1940-07-02', 6);
INSERT INTO usuarios_roles VALUES (270, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Nicolás', 'Presa', 'nicolaspresa@gmail.com' ,'13433000', '1940-06-01', 4);
INSERT INTO usuarios_roles VALUES (271, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Marcelo', 'Sanguinetti', 'marcelosanguinetti@gmail.com' ,'12016408', '1940-04-30', 3);
INSERT INTO usuarios_roles VALUES (272, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Lucas', 'Arévalo', 'lucasarevalo@gmail.com' ,'12012809', '1940-05-31', 8);
INSERT INTO usuarios_roles VALUES (273, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Laura', 'Zeballos', 'laurazeballos@gmail.com' ,'12016200', '1939-03-29', 8);
INSERT INTO usuarios_roles VALUES (274, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Matías', 'Barceló', 'matiasbarcelo@gmail.com' ,'11025501', '1939-02-28', 6);
INSERT INTO usuarios_roles VALUES (275, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Óscar', 'Freire', 'oscarfreire@gmail.com' ,'11066602', '1939-01-27', 6);
INSERT INTO usuarios_roles VALUES (276, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Blanca', 'Juárez', 'blancajuarez@gmail.com' ,'11074503', '1939-11-26', 6);
INSERT INTO usuarios_roles VALUES (277, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Claudia', 'Ferreyra', 'claudiaferreyra@gmail.com' ,'11462004', '1939-12-25', 6);
INSERT INTO usuarios_roles VALUES (278, 4);

INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Sofía', 'Robaina', 'sofiarobaina@gmail.com' ,'10028305', '1938-01-24', 8);
INSERT INTO usuarios_roles VALUES (279, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Gustavo', 'Soca', 'gustavosoca@gmail.com' ,'10192006', '1938-02-23', 8);
INSERT INTO usuarios_roles VALUES (280, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Sergio', 'Marín', 'sergiomarin@gmail.com' ,'10928007', '1938-03-22', 8);
INSERT INTO usuarios_roles VALUES (281, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Mario', 'Casas', 'mariocasas@gmail.com' ,'10045108', '1938-04-21', 8);
INSERT INTO usuarios_roles VALUES (282, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Daniel', 'Martins', 'danielmartins@gmail.com' ,'10024009', '1937-05-20', 8);
INSERT INTO usuarios_roles VALUES (283, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Andrea', 'Lema', 'andrealema@gmail.com' ,'10038200', '1936-06-19', 8);
INSERT INTO usuarios_roles VALUES (284, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Héctor', 'Martin', 'hectormartin@gmail.com' ,'09000301', '1935-07-18', 8);
INSERT INTO usuarios_roles VALUES (285, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Eduardo', 'Da Luz', 'eduardodaluz@gmail.com' ,'09047302', '1934-08-17', 8);
INSERT INTO usuarios_roles VALUES (286, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Silvia', 'Motta', 'silviamotta@gmail.com' ,'09038103', '1933-09-16', 8);
INSERT INTO usuarios_roles VALUES (287, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Fernando', 'Paez', 'fernandopaez@gmail.com' ,'09012104', '1932-10-15', 8);
INSERT INTO usuarios_roles VALUES (288, 4);

INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Lucía', 'Fraga', 'luciafraga@gmail.com' ,'08016205', '1931-11-14', 8);
INSERT INTO usuarios_roles VALUES (289, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Walter', 'Andrada', 'walterandrada@gmail.com' ,'08001736', '1930-12-13', 8);
INSERT INTO usuarios_roles VALUES (290, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Miguel', 'Bianchi', 'miguelbianchi@gmail.com' ,'08026307', '1929-01-12', 8);
INSERT INTO usuarios_roles VALUES (291, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Ruben', 'Graña', 'rubengrana@gmail.com' ,'08028918', '1928-02-11', 8);
INSERT INTO usuarios_roles VALUES (292, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Santiago', 'Alonzo', 'santiagoalonzo@gmail.com' ,'08056009', '1927-03-10', 8);
INSERT INTO usuarios_roles VALUES (293, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Diego', 'Coitiño', 'diegocoitino@gmail.com' ,'08835000', '1926-04-09', 8);
INSERT INTO usuarios_roles VALUES (294, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Julio', 'Padilla', 'juliopadilla@gmail.com' ,'07153001', '1925-05-08', 8);
INSERT INTO usuarios_roles VALUES (295, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Pablo', 'Caetano', 'pablocaetano@gmail.com' ,'07008762', '1924-06-07', 8);
INSERT INTO usuarios_roles VALUES (296, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Jorge', 'Lemes', 'jorgelemes@gmail.com' ,'07067803', '1923-07-06', 8);
INSERT INTO usuarios_roles VALUES (297, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Ana', 'Albornoz', 'anaalbornoz@gmail.com' ,'07009804', '1922-08-05', 8);
INSERT INTO usuarios_roles VALUES (298, 4);

INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Luis', 'Cano', 'luiscano@gmail.com' ,'06318005', '1921-09-04', 8);
INSERT INTO usuarios_roles VALUES (299, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Carlos', 'Jara', 'carlosjara@gmail.com' ,'06018706', '1920-12-03', 8);
INSERT INTO usuarios_roles VALUES (300, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('José', 'Borba', 'joseborba@gmail.com' ,'060034607', '1917-11-02', 8);
INSERT INTO usuarios_roles VALUES (301, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('Juan', 'Altez', 'juanaltez@gmail.com' ,'06593008', '1915-10-01', 8);
INSERT INTO usuarios_roles VALUES (302, 4);
INSERT INTO usuarios (nombre, apellido, correo, documento, fechanacimiento, fk_sector_laboral) VALUES ('María', 'Paredes', 'mariaparedes@gmail.com' ,'06093609', '1911-07-13', 8);
INSERT INTO usuarios_roles VALUES (303, 4);
