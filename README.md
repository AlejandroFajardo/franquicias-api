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
- ✔️ Actualización de nombre de :
  - Franquicia
  - Sucursal
  - Producto
- ✔️ Programación reactiva con WebFlux (Mono / Flux)

---

## 🔥 Endpoint destacado

Obtiene el producto con mayor stock por cada sucursal de una franquicia:

```
GET /franquicias/{id}/top-productos

```
📌 Respuesta ejemplo

```
[
    {
        "productoId": "p1",
        "nombre": "Papas",
        "stock": 99,
        "sucursalId": "s1",
        "sucursalNombre": "Centro"
    }
]
```
---

## ▶️ Ejecución local

### 🔧 Requisitos
- Java 17
- Maven
- MongoDB (opcional si se usa Docker)
- Docker (recomendado)

### Uso con WSL (Windows)

Si utilizas WSL, se recomienda instalar Docker Desktop en Windows y habilitar la integración con WSL:

1. Instalar Docker Desktop
2. Ir a Settings → Resources → WSL Integration
3. Activar la distribución (Ubuntu, etc.)

Luego podrás usar Docker desde WSL normalmente.


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
Esto levantará:

- API en puerto 8080
- MongoDB en puerto 27017
---

## 🧪 Pruebas

Ejecutar pruebas:

```
mvn test
```
### 📌 Cobertura

- Tests de integración con WebTestClient
- Tests reactivos con StepVerifier
- Mock de repositorio con Mockito

se validan

- Creación de franquicias
- Eliminación de productos
- Actualización de stock
- Productos con mayor stock por sucursal
- Actualización de nombres
---

## 📦 Base de datos

Se utiliza **MongoDB reactivo** para persistencia de datos.

Configuración por defecto:

```
mongodb://localhost:27017/franquicias_db
```

---

## 📌 Endpoints principales

| Método | Endpoint                                                      | Descripción                     |
|--------|---------------------------------------------------------------|---------------------------------|
| POST   | /franquicias                                                  | Crear franquicia                |
| POST   | /franquicias/{id}/sucursales                                  | Agregar sucursal                |
| POST   | /franquicias/{franquiciaId}/sucursales/{sucursalId}/productos | Agregar producto                |
| DELETE | /franquicias/productos/{productoId}                           | 	Eliminar producto            |
| PUT    | /franquicias/productos/{productoId}/stock                     | 	Actualizar stock             |
| GET	   | /franquicias/{id}/top-productos                               | 	Top productos por sucursal   |
| PUT	   | /franquicias/{id}/nombre                                      | 	Actualizar nombre franquicia |
| PUT	   | /franquicias/{id}/sucursales/{sucursalId}/nombre              | 	Actualizar nombre sucursal   |
| PUT	   | /franquicias/productos/{productoId}/nombre	                   | Actualizar nombre producto      |

---
## 📥 Ejemplo de uso
### Crear franquicia
```

POST /franquicias
```
Body:
```
{ 
    "nombre": "Franquicia Demo" 
}
```
---
## 🔄 Flujo de negocio
1. Crear franquicia
2. Agregar sucursales
3. Agregar productos
4. Gestionar stock
5. Consultar producto con mayor stock por sucursal
---

---
## ⚠️ Manejo de errores

Se utilizan códigos HTTP estándar:

- 200 OK
- 400 Bad Request
- 404 Not Found
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

Alejandro Fajardo

Backend Developer