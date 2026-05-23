# Microservicio de Agenda - Clínica Veterinaria VetNova

## 1. Descripción del Proyecto
El presente proyecto corresponde al Microservicio de Agenda, un componente esencial del ecosistema de arquitectura distribuida de la Clínica Veterinaria VetNova. Su objetivo principal es la administración y gestión eficiente de las horas médicas, permitiendo coordinar la atención de los pacientes, el registro de citas y el seguimiento de sus estados (Agendada, Confirmada, Reprogramada, Cancelada).

Este desarrollo ha sido diseñado bajo los principios de "Domain-Driven Design" (DDD), asegurando que el dominio de la agenda opere de manera independiente, con su propia base de datos, y se comunique con otros microservicios (Clientes y Mascotas) únicamente a través de clientes HTTP (OpenFeign), manteniendo un bajo nivel de acoplamiento.

## 2. Arquitectura y Patrones de Diseño
El microservicio ha sido estructurado bajo el Patrón CSR (Controller - Service - Repository), garantizando la escalabilidad y el mantenimiento del código:
* **Controller:** Capa expuesta mediante `@RestController`. Orquesta las peticiones HTTP y maneja estrictamente las respuestas mediante el objeto `ResponseEntity` para asegurar el uso correcto de los códigos de estado HTTP (200, 201, 400, 404).
* **Service:** Contiene la lógica de negocio. Es responsable de asignar estados por defecto, coordinar la búsqueda en base de datos y preparar la integración remota con otros microservicios mediante llamadas REST sincrónicas.
* **Repository:** Capa de persistencia que extiende de `JpaRepository` para el manejo automatizado de las consultas hacia la base de datos relacional.
* **Patrón DTO (Data Transfer Object):** Implementación de la clase `CitaRequestDTO` para desacoplar el modelo de base de datos de los datos de entrada, mejorando la seguridad y limpieza del código.
* **Manejo de Errores Global:** Implementación del patrón `@RestControllerAdvice` y manejo de excepciones personalizadas (`ResourceNotFoundException`) para interceptar errores y devolver estructuras JSON estandarizadas en lugar de trazas de servidor.

## 3. Stack Tecnológico Utilizado
* **Lenguaje:** Java 17.
* **Framework Principal:** Spring Boot 3.4.x.
* **Comunicación entre microservicios:** Spring Cloud OpenFeign 2024.0.0.
* **Persistencia:** Spring Data JPA e Hibernate.
* **Base de Datos:** MySQL (Puerto 3306).
* **Validaciones:** Jakarta Bean Validation (JSR 380).
* **Trazabilidad:** SLF4J para logs estructurados.
* **Librerías de Soporte:** Lombok.

## 4. Requisitos Funcionales Implementados
El microservicio cubre íntegramente las operaciones CRUD y las reglas de negocio exigidas:
1. **Agendar hora:** Creación de una nueva cita médica asignando automáticamente el estado "AGENDADA".
2. **Consultar agenda:** Lectura de todas las citas registradas en el sistema.
3. **Consultar cita específica:** Búsqueda detallada de un registro mediante su identificador único.
4. **Reprogramar hora:** Modificación de la fecha y hora de una cita existente, cambiando su estado a "REPROGRAMADA".
5. **Confirmar asistencia:** Actualización del estado de una cita a "CONFIRMADA" cuando el paciente llega a la clínica.
6. **Cancelar hora:** Anulación de una reserva, actualizando su estado a "CANCELADA".

## 5. Endpoints de la API y Funcionamiento

### A. Operaciones de Escritura y Modificación
* **POST /api/v1/agenda/agendar**
  * **Uso:** Crea una nueva cita médica.
  * **Body (JSON):** Requiere `idCliente`, `idMascota`, `idVeterinario`, `fechaHora` y `motivo`.
  * **Retorno:** `201 Created` / `400 Bad Request` (Si falla la validación del DTO).

* **PUT /api/v1/agenda/{id}/reprogramar**
  * **Uso:** Modifica la fecha de una cita existente.
  * **Body (JSON):** Requiere `nuevaFechaHora`.
  * **Retorno:** `200 OK` / `404 Not Found`.

* **PUT /api/v1/agenda/{id}/confirmar**
  * **Uso:** Confirma la asistencia del paciente.
  * **Body:** No requiere.
  * **Retorno:** `200 OK`.

* **PUT /api/v1/agenda/{id}/cancelar**
  * **Uso:** Cancela la cita médica.
  * **Body:** No requiere.
  * **Retorno:** `200 OK`.

### B. Operaciones de Lectura
* **GET /api/v1/agenda**
  * **Uso:** Lista todas las citas médicas agendadas en el sistema.
  * **Retorno:** `200 OK` (Array de objetos JSON).

* **GET /api/v1/agenda/{id}**
  * **Uso:** Retorna los detalles de una cita en particular.
  * **Retorno:** `200 OK` / `404 Not Found`.

## 6. Instrucciones de Configuración y Ejecución
Para ejecutar este microservicio en un entorno local, siga los siguientes pasos:

1. **Preparación de la Base de Datos:**
   * Inicie su servidor MySQL (ej. a través de Laragon o XAMPP) en el puerto `3306`.
   * Cree un esquema de base de datos completamente vacío nombrado exactamente como: `db_agenda`.
2. **Configuración del Entorno:**
   * Verifique que en el archivo `src/main/resources/application.properties` se encuentre configurado el puerto exclusivo de este microservicio:
     ```properties
     server.port=8086
     spring.datasource.url=jdbc:mysql://localhost:3306/db_agenda?useSSL=false&serverTimezone=UTC
     spring.datasource.username=root
     spring.jpa.hibernate.ddl-auto=update
     ```
3. **Compilación y Ejecución:**
   * Posiciónese en el directorio raíz del proyecto mediante una terminal y ejecute el comando `mvn clean install` para descargar las dependencias necesarias (incluyendo Spring Cloud OpenFeign).
   * Ejecute la clase principal `AgendaApplication.java` utilizando su entorno de desarrollo (IDE).
4. **Validación:**
   * Utilice la herramienta Postman para enviar peticiones a la ruta base `http://localhost:8086/api/v1/agenda` y compruebe los flujos de creación, lectura y actualización.