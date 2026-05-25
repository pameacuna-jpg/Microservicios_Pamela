#  VetNova — Plataforma Veterinaria Basada en Microservicios

## Descripción General

VetNova es una plataforma backend desarrollada para la gestión integral de clínicas veterinarias, implementando una arquitectura basada en microservicios distribuidos utilizando Spring Boot, Oracle Database y comunicación REST entre servicios.
---

# Arquitectura del Proyecto

El sistema implementa una arquitectura de microservicios distribuidos, separando cada dominio de negocio en servicios independientes.
---

# Estructura General

MICROSERVICIOS-VET
│
├── ms-inventario
├── ms-notificaciones
├── ms-sucursales-administracion
│
└── README.md
---

# Arquitectura Interna por Microservicio

Cada microservicio implementa arquitectura en capas:

controller
service
repository
model
dto
exception
config
---
# Buenas prácticas Implementadas

-Manejo global de excepciones mediante @RestControllerAdvice.
-Validaciones utilizando Jakarta Validation.
-Logs estructurados mediante SLF4J Logger.
-Arquitectura desacoplada basada en microservicios.
-Persistencia mediante JPA + Hibernate.
---

# Microservicios Implementados

| Microservicio                | Puerto | Responsabilidad                                          |
| ---------------------------- | ------ | -------------------------------------------------------- |
| ms-inventario                | 8087   | Gestión de productos, proveedores y movimientos de stock |
| ms-notificaciones            | 8089   | Gestión y envío automático de notificaciones             |
| ms-sucursales-administracion | 8090   | Gestión de sucursales, boxes y horarios                  |
---

# Tecnologías Utilizadas

-Backend
SLF4J Logger
ResponseEntity
Jakarta Validation
Java 21
Spring Boot
Spring Data JPA
Hibernate ORM
Maven

-Base de Datos
Oracle Database XE

-APIs y Comunicación
REST API
RestTemplate
JSON

-Herramientas
Postman
Git
GitHub
Visual Studio Code
---

# Funcionalidades Implementadas

ms-inventario

*CRUD de Productos
*CRUD de Proveedores
*Movimientos de Inventario
*Entradas y salidas de stock
*Validación de stock insuficiente
*Actualización automática de stock
*Alertas automáticas de stock bajo

ms-sucursales-administracion

*CRUD de Sucursales
*CRUD de Boxes de Atención
*CRUD de Horarios
*Gestión de disponibilidad

ms-notificaciones

*Gestión de notificaciones
*Prioridades
*Estados
*Alertas automáticas
*Comunicación REST entre microservicios
---

# Comunicación entre Microservicios
La comunicación entre microservicios se implementó mediante REST API utilizando RestTemplate y JSON. 

RestTemplate

Ejemplo de comunicación:

ms-inventario
        ↓
detecta stock bajo
        ↓
consume API REST
        ↓
ms-notificaciones
        ↓
genera alerta automática
---

# Patrones y Estrategias Implementadas

Arquitectura

*Microservicios Distribuidos
*Arquitectura en Capas

Patrones de Diseño

Repository Pattern
*DTO Pattern
*Dependency Injection
*REST Client Pattern

Estrategia Arquitectónica

*Domain Driven Design (DDD)
---

# Configuración Oracle

spring.datasource.url=jdbc:oracle:thin:@localhost:1521:xe
spring.datasource.username=SYSTEM
spring.datasource.password=TU_PASSWORD
---

# Ejecución del Proyecto

Ejecutar Microservicios

ms-inventario

Puerto: 8087

ms-notificaciones

Puerto: 8089

ms-sucursales-administracion

Puerto: 8090
---
# Pruebas Realizadas

Las pruebas fueron ejecutadas utilizando:

*Postman
*Oracle Database
*Endpoints REST
*Comunicación entre microservicios
*Validaciones de negocio
---

# Endpoints Principales

Inventario
http
/api/v1/productos
/api/v1/proveedores
/api/v1/movimientos

Sucursales
http
/api/v1/sucursales
/api/v1/boxes
/api/v1/horarios

Notificaciones
http
/api/v1/notificaciones
---

# Manejo Global de Errores

El proyecto implementa:
@RestControllerAdvice

para de esta manera centralizar:
*validaciones
*errores de negocio
*respuestas estructuradas JSON
---
# Logs y Trazabilidad

El sistema implementa logs estructurados utilizando SLF4J Logger para registrar:

-movimientos de inventario,
-actualización de stock,
-detección de stock bajo,
-creación automática de notificaciones,
-monitoreo de eventos críticos.

Ejemplo:

INFO Registrando movimiento de inventario
WARN Stock bajo detectado
INFO Notificación registrada correctamente
---


