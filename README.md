# Sabor Gourmet App

Aplicación web desarrollada en Java con Spring Boot para la gestión de mesas y reservas en un restaurante.

## Características principales

- Listado, creación y edición de mesas
- Listado, creación y edición de reservas
- Interfaz web con Bootstrap
- Arquitectura MVC (Model-View-Controller)

## Estructura del proyecto

- `src/main/java/cl/ipss/saborgourmet/` - Código fuente principal
  - `controllers/` - Controladores web
  - `models/` - Modelos de datos
  - `repositories/` - Repositorios JPA
  - `services/` - Lógica de negocio
- `src/main/resources/templates/` - Vistas HTML (Thymeleaf)
- `src/main/resources/static/` - Archivos estáticos (CSS, JS)
- `pom.xml` - Configuración de dependencias Maven

## Requisitos

- Java 17 o superior
- Maven

## Ejecución

1. Instala las dependencias:
   ```bash
   mvn install
   ```
2. Ejecuta la aplicación:
   ```bash
   mvn spring-boot:run
   ```
3. Accede a la app en [http://localhost:8080](http://localhost:8080)

## Estructura de carpetas

```
sabor-gourmet-app/
├── pom.xml
├── src/
│   ├── main/
│   │   ├── java/cl/ipss/saborgourmet/
│   │   │   ├── controllers/
│   │   │   ├── models/
│   │   │   ├── repositories/
│   │   │   └── services/
│   │   └── resources/
│   │       ├── static/
│   │       └── templates/
│   └── test/
└── ...
```

## Autor

- Desarrollado por Iván Santibáñez, Michael Bustamante, Jimmy Pino

## Licencia

Este proyecto es solo para fines educativos.
