# Microservicio de Autenticación - Clínica Veterinaria VetNova

## 1. Descripción del Proyecto
El presente proyecto corresponde al Microservicio de Autenticación, componente crítico y transversal del ecosistema de arquitectura distribuida de la Clínica Veterinaria VetNova. Su objetivo principal es centralizar la gestión de identidades, la validación estricta de credenciales y asegurar la comunicación entre los distintos microservicios mediante la emisión de tokens criptográficos (JWT).

Este desarrollo ha sido diseñado bajo un enfoque "Stateless" (sin estado), cumpliendo con los estándares de diseño de APIs REST seguras y los requerimientos establecidos para la evaluación técnica, garantizando alta disponibilidad, escalabilidad horizontal y un bajo nivel de acoplamiento.

## 2. Arquitectura y Patrones de Diseño
El microservicio ha sido estructurado bajo el Patrón CSR (Controller - Service - Repository), asegurando el principio de responsabilidad única:
* **Security Filter Chain:** Implementación de Spring Security configurada para deshabilitar CSRF y manejar sesiones sin estado (Stateless), asegurando que los endpoints de autenticación sean públicos mientras se protege el resto del ecosistema.
* **Controller:** Encargado de recibir las peticiones HTTP (REST), orquestar la llamada a los servicios y retornar el objeto `ResponseEntity` con el código de estado HTTP correspondiente (200 OK, 401 Unauthorized, etc.).
* **Service:** Contiene la lógica de negocio, incluyendo la validación de credenciales, la interacción con la utilidad de tokens (`JwtUtil`) y la integración remota mediante Feign Client.
* **Manejo de Errores Global:** Implementación del patrón `@RestControllerAdvice` y `@ExceptionHandler` para capturar excepciones de validación o seguridad y retornar JSON estructurados sin exponer trazas de error internas.

## 3. Stack Tecnológico Utilizado
* **Lenguaje:** Java 17.
* **Framework Principal:** Spring Boot 3.4.x.
* **Seguridad:** Spring Security y JSON Web Token (Librería `jjwt` 0.11.5).
* **Gestor de Dependencias:** Maven.
* **Comunicación entre microservicios:** Spring Cloud OpenFeign.
* **Base de Datos:** MySQL (Puerto 3306).
* **Validaciones:** Jakarta Bean Validation (JSR 380).
* **Trazabilidad:** SLF4J para logs estructurados.
* **Librerías de Soporte:** Lombok.

## 4. Requisitos Funcionales Implementados
El microservicio cubre íntegramente las siguientes funcionalidades requeridas en el dominio de seguridad:
1. **Validación de credenciales:** Verificación de las credenciales proporcionadas por el usuario.
2. **Emisión de JWT:** Generación de un JSON Web Token firmado criptográficamente tras una autenticación exitosa.
3. **Login:** Endpoint público para iniciar sesión en la plataforma.
4. **Recuperación de contraseña:** Endpoint para iniciar el flujo de restablecimiento de credenciales de un usuario.

## 5. Endpoints de la API y Funcionamiento

### A. Iniciar Sesión (Login y obtención de JWT)
* **Método:** `POST`
* **Ruta:** `/api/v1/auth/login`
* **Body (JSON):**
  ```json
  {
      "email": "admin@vetnova.cl",
      "password": "password123"
  }
  ```
* **Retorno Exitoso:** `200 OK` (Devuelve el token JWT en la respuesta).
* **Retorno Fallido:** `401 Unauthorized` (Si las credenciales son incorrectas).
* **Retorno Fallido:** `400 Bad Request` (Si el formato del email o los campos no superan la validación `@Valid`).

### B. Recuperación de Contraseña
* **Método:** `POST`
* **Ruta:** `/api/v1/auth/recuperar-password`
* **Body (JSON):**
  ```json
  {
      "email": "admin@vetnova.cl"
  }
  ```
* **Retorno Exitoso:** `200 OK` (Devuelve un mensaje confirmando el envío de instrucciones).

## 6. Instrucciones de Configuración y Ejecución
Para ejecutar este microservicio en un entorno local, siga los siguientes pasos:

1. **Preparación de Entorno:**
   * Tener instalado Java Development Kit (JDK) 17 o superior.
   * Disponer de un servidor MySQL activo escuchando en el puerto local `3306`.
2. **Configuración de Propiedades:**
   * Verifique el archivo `src/main/resources/application.properties` con la siguiente configuración:
     ```properties
     spring.application.name=auth-service
     server.port=8081

     spring.datasource.url=jdbc:mysql://localhost:3306/Auth_Service?useSSL=false&serverTimezone=UTC
     spring.datasource.username=root
     spring.datasource.password=
     spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

     spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
     spring.jpa.show-sql=true
     spring.jpa.hibernate.ddl-auto=update

     logging.level.org.springframework.web=INFO
     logging.level.com.vetnova=DEBUG
     logging.level.org.hibernate=ERROR
     ```
3. **Compilación y Ejecución:**
   * En la terminal, sitúese en la raíz del proyecto y ejecute `mvn clean install` para descargar dependencias y limpiar construcciones previas.
   * Ejecute la clase principal `AuthApplication.java` desde su IDE.
4. **Validación:**
   * Utilice Postman para realizar peticiones `POST` a la ruta `http://localhost:8081/api/v1/auth/login` con el formato JSON indicado en la sección 5.