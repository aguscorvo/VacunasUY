# VacunasUy

Laboratorio para la asignatura Taller de Sistemas Empresariales de la Facultad de Ingeniería (FIng-UdelaR).

## Contenido

- [Link Frontoffice web](#link-frontoffice-web)
- [Datos de prueba](#datos-de-prueba)
- [Descripción](#descripción)
  - [Screenshots](#screenshots)
- [Tecnologías](#tecnologías)
- [Despliegue](#despliegue)

## Link Frontoffice web

[VacunasUy - ElasticCloud](http://vacunasuy.web.elasticloud.uy)

## Datos de prueba

Usuarios por defecto en backoffice:
| Perfil        | Correo          | Contraseña |
|---------------|-----------------|------------|
| Administrador | admin@admin.com | 1234       |
| Autoridad     | msp@gub.uy      | 1234       |

## Descripción

La plataforma VacunasUy surge en el contexto actual, a nivel mundial, que está atravesando la sociedad por la pandemia de Coronavirus (COVID-19). Si bien ya se cuenta con vacunas desarrolladas para combatir esta enfermedad, es necesario, a su vez, contar con un sistema informático que sea capaz de gestionar todo el ciclo de vacunación, desde la recepción de las unidades, envío a los centros de vacunación, asignación del personal de la salud para realizar las vacunaciones, así como también permitir a los ciudadanos inscribirse en las distintas agendas para recibir las vacunas, entre otras funcionalidades. Además, no solo se espera que esta plataforma pueda gestionar el contexto actual, sino que pueda utilizarse a futuro para gestionar cualquier plan de vacunación.

Más información en el documento de arquitectura. 
`Documentacion/documento-de-arquitectura-de-software.pdf`

### Screenshots

Frontoffice web
![frontoffice-web-screenshot](./)

Frontoffice mobile
![frontoffice-mobile-screenshot](./)

Backoffice
![backoffice-web-screenshot](./)


## Tecnologías

- JakartaEE
- PostgreSQL con PostGIS
- Java (para Android)
- Flutter
- JQuery
- SpringBoot
- MongoDB

## Despliegue

- WildFly 21
- [Mi Nube - Elastic Cloud](https://minubeantel.uy/)
- Firebase
- Heroku
