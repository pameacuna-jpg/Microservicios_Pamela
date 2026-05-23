# Microservicio de Ventas y Pagos - Clínica Veterinaria VetNova

## 1. Descripción del Proyecto
El presente proyecto corresponde al Microservicio de Ventas y Pagos, componente crítico del ecosistema de arquitectura distribuida de la Clínica Veterinaria VetNova. Su objetivo principal es la administración financiera de la clínica, gestionando el registro de ventas, el procesamiento de pagos, la emisión de boletas y el registro de devoluciones.

Este desarrollo ha sido diseñado bajo los principios de "Domain-Driven Design" (DDD), asegurando que el dominio de ventas opere de manera independiente, con su propia base de datos, y se comunique con otros microservicios (como el Microservicio de Inventario) únicamente a través de clientes HTTP (OpenFeign), manteniendo un bajo nivel de acoplamiento.

## 2. Arquitectura y Patrones de Diseño
El microservicio ha sido estructurado bajo el Patrón CSR (Controller - Service - Repository), garantizando la escalabilidad y el mantenimiento del código:
* **Controller:** Capa expuesta mediante `@RestController`. Orquesta las peticiones HTTP y maneja estrictamente las respuestas mediante el objeto `ResponseEntity` para asegurar el uso correcto de los códigos de estado HTTP (200, 201, 400, 404).
* **Service:** Contiene la lógica de negocio transaccional (`@Transactional`). Es responsable de asignar estados financieros (PENDIENTE, PAGADA, DEVUELTA), aplicar reglas de negocio (ej. no emitir boleta sin pago) y preparar la integración remota con el Inventario para validar stock mediante llamadas REST sincrónicas.
* **Repository:** Capa de persistencia que extiende de `JpaRepository` para el manejo automatizado de las consultas hacia la base de datos relacional.
* **Patrón DTO (Data Transfer Object):** Implementación de la clase `VentaRequestDTO` para desacoplar el modelo de base de datos de los datos de entrada, aislando la validación y mejorando la seguridad estructural.
* **Manejo de Errores Global:** Implementación del patrón `@RestControllerAdvice` y manejo de excepciones personalizadas (`ResourceNotFoundException`, `MethodArgumentNotValidException`) para interceptar errores y devolver estructuras JSON estandarizadas.

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
1. **Registrar venta:** Creación de una nueva transacción asignando automáticamente la fecha actual y el estado "PENDIENTE".
2. **Procesar pago:** Modificación del estado de una venta existente a "PAGADA".
3. **Emitir boleta:** Lectura de los datos de una venta aplicando la regla de negocio que exige que el estado sea "PAGADA" previo a la emisión.
4. **Registrar devolución:** Actualización del estado de una venta a "DEVUELTA" para trazabilidad de caja e inventario.
5. **Consultar historial:** Lectura de todas las transacciones registradas en el sistema.
6. **Consultar venta específica:** Búsqueda detallada de un registro financiero mediante su identificador único.

## 5. Endpoints de la API y Funcionamiento

### A. Operaciones de Escritura y Modificación
* **POST /api/v1/ventas/registrar**
  * **Uso:** Registra una nueva venta.
  * **Body (JSON):** Requiere `idCliente`, `idProducto`, `cantidad` y `montoTotal`.
  * **Retorno:** `201 Created` / `400 Bad Request` (Si falla la validación del DTO).

* **PUT /api/v1/ventas/{id}/pagar**
  * **Uso:** Procesa el pago de la venta especificada.
  * **Body:** No requiere.
  * **Retorno:** `200 OK` / `404 Not Found`.

* **PUT /api/v1/ventas/{id}/devolucion**
  * **Uso:** Registra la anulación o devolución de una venta.
  * **Body:** No requiere.
  * **Retorno:** `200 OK` / `404 Not Found`.

### B. Operaciones de Lectura (CRUD y Reglas de Negocio)
* **GET /api/v1/ventas/{id}/boleta**
  * **Uso:** Emite la boleta si la venta está en estado PAGADA.
  * **Retorno:** `200 OK` / `500 Internal Server Error` (Si se intenta emitir sin pago previo).

* **GET /api/v1/ventas**
  * **Uso:** Lista el historial completo de ventas del sistema.
  * **Retorno:** `200 OK` (Array de objetos JSON).

* **GET /api/v1/ventas/{id}**
  * **Uso:** Retorna los detalles de una transacción financiera en particular.
  * **Retorno:** `200 OK` / `404 Not Found`.

## 6. Instrucciones de Configuración y Ejecución
Para ejecutar este microservicio en un entorno local, siga los siguientes pasos:

1. **Preparación de la Base de Datos:**
   * Inicie su servidor MySQL (ej. a través de Laragon o XAMPP) en el puerto `3306`.
   * Cree un esquema de base de datos completamente vacío nombrado exactamente como: `db_ventas`.
2. **Configuración del Entorno:**
   * Verifique que en el archivo `src/main/resources/application.properties` se encuentre configurado el puerto exclusivo de este microservicio:
     ```properties
     server.port=8088
     spring.datasource.url=jdbc:mysql://localhost:3306/db_ventas?useSSL=false&serverTimezone=UTC
     spring.datasource.username=root
     spring.jpa.hibernate.ddl-auto=update
     ```
3. **Compilación y Ejecución:**
   * Posiciónese en el directorio raíz del proyecto mediante una terminal y ejecute el comando `mvn clean install` para limpiar el entorno y descargar las dependencias necesarias.
   * Ejecute la clase principal `VentasApplication.java` utilizando su entorno de desarrollo (IDE).
4. **Validación y Dependencias Externas:**
   * Utilice la herramienta Postman para enviar peticiones a la ruta base `http://localhost:8088/api/v1/ventas`.
   * **Nota de Integración:** Este microservicio está preparado para conectarse al puerto `8087` (Microservicio de Inventario) vía OpenFeign para la validación de stock.