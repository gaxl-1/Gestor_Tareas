# 🚀 Gestor de Tareas Moderno (Recuperado)

Este es un proyecto de gestión de tareas reconstruido y modernizado. Originalmente un proyecto abandonado, ha sido transformado en una aplicación robusta con **Spring Boot 3.2**, **Java 21** y una interfaz de usuario premium.

## ✨ Características Principales

-   **🏢 Gestión Multicuenta**: Registro de empresas/cuentas con usuarios asociados.
-   **📝 CRUD de Tareas**: Creación, edición, eliminación y listado de tareas con estados dinámicos.
-   **💾 Doble Persistencia**:
    -   **Base de Datos SQL**: Uso de H2 persistente para el funcionamiento interno.
    -   **Sincronización JSON**: Exportación automática en tiempo real a `data/tasks.json` tras cada cambio.
-   **🔐 Seguridad Avanzada**: 
    -   Autenticación mediante formularios para usuarios finales.
    -   Soporte para **Basic Auth** para pruebas técnicas (Postman).
    -   Protección CSRF configurada (actualmente deshabilitada para facilitar pruebas de desarrollo).
-   **🎨 Interfaz Premium**: Diseño oscuro (Dark Mode), responsivo y moderno basado en Vanilla CSS y Thymeleaf.

## 🛠️ Tecnologías Utilizadas

-   **Backend**: Java 21, Spring Boot 3.2
-   **Seguridad**: Spring Security 6
-   **Persistencia**: Spring Data JPA, H2 Database, Jackson (JSON)
-   **Frontend**: Thymeleaf, CSS3 Moderno, HTML5
-   **Construcción**: Maven 3.6+

## 🚀 Instalación y Ejecución

### Requisitos Previos
-   Java 21 instalado.
-   Maven instalado.

### Pasos para ejecutar
1.  Clona o descarga el proyecto.
2.  Abre una terminal en la raíz del proyecto.
3.  Ejecuta el siguiente comando:
    ```bash
    mvn spring-boot:run
    ```
4.  Accede a la aplicación en: `http://localhost:8080`

## 🧪 Pruebas con Postman

Para probar los endpoints técnicos, utiliza la autenticación **Basic Auth** con las credenciales de un administrador registrado.

### Endpoints Principales:
-   `GET /tareas`: Lista todas las tareas de la cuenta autenticada.
-   `POST /tareas/guardar`: Crea o actualiza una tarea.
-   `GET /buscar/tarea?titulo=...`: Busca tareas por coincidencia en el título.
-   `POST /registro`: Crea una nueva cuenta y administrador.

## 📂 Estructura del Proyecto

```text
src/main/java/com/proyecto/
├── controlador/    # Manejo de peticiones HTTP (Web y API)
├── modelo/         # Entidades JPA (Tarea, Usuario, Cuenta)
├── repositorio/   # Interfaces de acceso a datos
├── seguridad/     # Configuración de Spring Security
└── servicio/      # Lógica de negocio y exportación JSON
```

---
*Proyecto reconstruido con ❤️ por GaelDev*
