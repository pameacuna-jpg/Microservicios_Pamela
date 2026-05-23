# Microservicio: Autenticacion y Seguridad (Auth-Service)

Este microservicio es el nucleo de seguridad del ecosistema VetNova. Su objetivo es centralizar la gestion de identidades, validar credenciales (Login) de los actores (Administrador, Veterinario, Cliente web) y emitir tokens de seguridad (JWT) de forma Stateless para proteger la comunicacion de las APIs.

## Configuracion y Requisitos Previos

Para ejecutar este microservicio en un entorno local, necesitas tener instalado:
1. Java JDK 17 (o superior).
2. Laragon o XAMPP (con el modulo MySQL iniciado).
3. IDE: IntelliJ IDEA, Eclipse o VS Code.

### Configuracion de la Base de Datos

Antes de ejecutar el proyecto, asegurate de crear la base de datos en MySQL (puedes usar la terminal, HeidiSQL o phpMyAdmin):

```sql
CREATE DATABASE Auth_Service;