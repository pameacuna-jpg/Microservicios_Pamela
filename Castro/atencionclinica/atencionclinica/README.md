# Microservicio de Atención Clínica - Clínica Veterinaria VetNova

## 1. Descripción del Proyecto

El presente proyecto corresponde al Microservicio de Atención Clínica, componente fundamental del ecosistema de arquitectura distribuida de la Clínica Veterinaria VetNova. Su objetivo principal es la administración centralizada y segura de las fichas clínicas de los pacientes, así como el registro y seguimiento de diagnósticos, tratamientos, indicaciones, emisión de recetas médicas y certificados.

Este desarrollo cumple con los estándares de diseño de APIs REST y los requerimientos establecidos para la evaluación técnica, garantizando alta disponibilidad, escalabilidad y un bajo nivel de acoplamiento.

## 2. Arquitectura y Patrones de Diseño

El microservicio ha sido estructurado estrictamente bajo el Patrón CSR (Controller - Service - Repository), lo cual asegura el principio de responsabilidad única en cada capa:

* 
**Controller**: Encargado exclusivamente de recibir las peticiones HTTP (REST), orquestar la llamada a los servicios y retornar el objeto ResponseEntity con el código de estado HTTP correspondiente (200 OK, 201 Created, etc.).


* **Service**: Contiene de forma aislada la lógica de negocio y validaciones del dominio clínico. Utiliza la anotación @Transactional para asegurar la integridad de las transacciones de base de datos.


* 
**Repository / Model**: La capa de modelo mapea las entidades relacionales utilizando JPA/Hibernate (@Entity, @Id, relaciones @OneToMany, @ManyToOne). El repositorio extiende de JpaRepository para la ejecución eficiente de consultas a la base de datos.



## 3. Stack Tecnológico Utilizado

* 
**Lenguaje**: Java 17.


* 
**Framework Principal**: Spring Boot 3.4.x.


* 
**Gestor de Dependencias**: Maven.


* 
**Persistencia y ORM**: Spring Data JPA, Hibernate.


* 
**Base de Datos**: MySQL (Puerto 3306).


* 
**Validaciones**: Jakarta Bean Validation (JSR 380).


* 
**Librerías de Soporte**: Lombok (Reducción de código repetitivo).



## 4. Requisitos Funcionales Implementados

Con base en las Historias de Usuario del caso VetNova, el microservicio cubre íntegramente las siguientes funcionalidades:

* 
**Gestión de Fichas Clínicas**: Creación y consulta de fichas.


* 
**Registro de Diagnóstico**: Creación de una atención médica vinculada a la ficha clínica y al veterinario responsable.


* 
**Registro de Tratamiento**: Actualización de la atención para incluir las indicaciones médicas.


* 
**Emisión de Receta**: Actualización del registro para incluir la receta médica asociada.


* 
**Emisión de Certificado**: Actualización del registro para emitir el certificado clínico de salud del paciente.



## 5. Endpoints de la API y Funcionamiento (Uso en Postman)

Todas las rutas base están versionadas bajo la nomenclatura /api/v1/ para asegurar compatibilidad futura.

### A. Gestión de Fichas Clínicas

* **Crear Ficha Clínica**
* Método: POST
* Ruta: /api/v1/fichas
* Retorno Exitoso: 201 Created 




* **Consultar Ficha Clínica por ID**
* Método: GET
* Ruta: /api/v1/fichas/{id}
* Retorno Exitoso: 200 OK 





### B. Gestión de Atenciones Médicas

* **Registrar Diagnóstico (Crear Atención)**
* Método: POST
* Ruta: /api/v1/atenciones
* Retorno Exitoso: 201 Created


* **Registrar Tratamiento**
* Método: PUT
* Ruta: /api/v1/atenciones/{id}/tratamiento
* Retorno Exitoso: 200 OK


* **Emitir Receta Médica**
* Método: PUT
* Ruta: /api/v1/atenciones/{id}/receta
* Retorno Exitoso: 200 OK


* **Emitir Certificado Médico**
* Método: PUT
* Ruta: /api/v1/atenciones/{id}/certificado
* Retorno Exitoso: 200 OK


* **Consultar Atención Clínica Completa**
* Método: GET
* Ruta: /api/v1/atenciones/id/{id}
* Retorno Exitoso: 200 OK



## 6. Manejo de Errores, Integridad y Validaciones

Para garantizar la solidez del backend, se han implementado las siguientes técnicas avanzadas:

* 
**Validaciones JSR 380**: Se implementan anotaciones como @NotBlank y @NotNull en las entidades del modelo. Los controladores procesan estas reglas a través de la anotación @Valid interceptando datos anómalos o campos vacíos.


* 
**Manejo Global de Excepciones**: Se ha implementado un componente @RestControllerAdvice y @ExceptionHandler. Ante la búsqueda de recursos inexistentes o fallos de validación, el servidor no colapsa, sino que captura la excepción y responde con un formato JSON estructurado, retornando estados HTTP explícitos (404 Not Found o 400 Bad Request).


* 
**Prevención de Recursividad Infinita**: Para mantener la integridad relacional de base de datos bidireccional sin generar ciclos infinitos al parsear los datos a formato JSON, se integró el uso estratégico de la anotación @JsonBackReference y la estrategia de carga FetchType.LAZY en el modelo.



## 7. Instrucciones de Configuración y Ejecución

Para ejecutar el microservicio en un entorno local, se deben seguir los siguientes pasos:

### Preparación de Entorno

* Instalar Java Development Kit (JDK) versión 17 o superior.


* Instalar y ejecutar un servidor MySQL escuchando en el puerto local 3306.



### Base de Datos

* Crear un esquema de base de datos vacío llamado `Castro_microservice`.



### Configuración de Propiedades

* Verificar el archivo `src/main/resources/application.properties` para asegurar la conexión al puerto 3306 con el usuario root.


* Nota: La propiedad `ddl-auto=update` instruye a Hibernate a mapear y crear automáticamente las tablas y columnas necesarias al iniciar el sistema.



### Ejecución

* 
**Mediante línea de comandos (Maven)**: Ejecutar `mvn spring-boot:run`.


* 
**Mediante IDE (IntelliJ / Eclipse / VS Code)**: Ejecutar la clase principal del proyecto.



### Validación

* Abrir Postman y realizar peticiones a la ruta base `http://localhost:8081/api/v1/fichas`.