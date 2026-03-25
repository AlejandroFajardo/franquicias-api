# 🚀 Franquicias API - Backend Challenge

API desarrollada como solución a prueba técnica para Backend Developer, implementando arquitectura limpia, programación reactiva y buenas prácticas.

---

## 🧠 Tecnologías utilizadas

- Java 17
- Spring Boot (WebFlux)
- MongoDB (Reactive)
- Maven
- Docker

---

## 🏗️ Arquitectura

El proyecto sigue principios de **Clean Architecture**, separando responsabilidades en capas:

```
domain/
 ├── model/
 └── usecase/

application/
 └── service/

infrastructure/
 ├── repository/
 └── config/

interfaces/
 ├── controller/
 └── dto/
```

---

## ⚙️ Características implementadas

- ✔️ Creación de franquicias
- ✔️ Agregado de sucursales a una franquicia
- ✔️ Gestión de productos por sucursal
- ✔️ Eliminación de productos
- ✔️ Actualización de stock
- ✔️ Consulta del producto con mayor stock por sucursal
- ✔️ Programación reactiva con WebFlux (Mono / Flux)

---

## 🔥 Endpoint destacado

Obtiene el producto con mayor stock por cada sucursal de una franquicia:

```
GET /franquicias/{id}/top-productos
```

---

## ▶️ Ejecución local

### 🔧 Requisitos
- Java 17
- Maven

### 🚀 Ejecutar aplicación

```
mvn clean install
mvn spring-boot:run
```

La aplicación estará disponible en:

```
http://localhost:8080
```

---

## 🐳 Ejecución con Docker

### Construir y levantar servicios

```
docker-compose up --build
```

---

## 🧪 Pruebas

Ejecutar pruebas unitarias:

```
mvn test
```

Incluye pruebas utilizando:
- JUnit 5
- Mockito
- StepVerifier (reactivo)

---

## 📦 Base de datos

Se utiliza **MongoDB reactivo** para persistencia de datos.

Configuración por defecto:

```
mongodb://localhost:27017/test
```

---

## 📌 Endpoints principales

| Método | Endpoint | Descripción |
|--------|---------|------------|
| POST | /franquicias | Crear franquicia |
| POST | /franquicias/{id}/sucursales | Agregar sucursal |
| POST | /sucursales/{id}/productos | Agregar producto |
| DELETE | /productos/{id} | Eliminar producto |
| PUT | /productos/{id}/stock | Actualizar stock |
| GET | /franquicias/{id}/top-productos | Producto con mayor stock |

---

## 📄 Buenas prácticas aplicadas

- Clean Architecture
- Programación reactiva
- Separación de responsabilidades
- Código legible y mantenible
- Uso de DTOs
- Manejo de errores

---

## ☁️ Contenerización

La aplicación puede ejecutarse en contenedores Docker para facilitar su despliegue en cualquier entorno.

---

## 👨‍💻 Autor

Desarrollado como prueba técnica backend por Alejandro Fajardo.