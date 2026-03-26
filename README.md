# 🚀 Franquicias API - Backend Challenge

API desarrollada como solución a prueba técnica para Backend Developer, implementando arquitectura limpia, programación reactiva y buenas prácticas.

---

## 📘 Documentación de la API (Swagger)

La API cuenta con documentación interactiva generada con OpenAPI (Swagger), la cual permite explorar y probar todos los endpoints directamente desde el navegador.

### 🔗 Acceso local

Una vez levantada la aplicación:

http://localhost:8080/swagger-ui.html

### 🚀 ¿Qué incluye?

- Descripción de todos los endpoints
- Parámetros documentados
- Ejemplos de request
- Respuestas esperadas (status codes)
- Modelos de datos explicados

### 🧪 Uso

1. Ingresar a la URL de Swagger
2. Seleccionar el endpoint deseado
3. Hacer clic en "Try it out"
4. Ejecutar la petición

---

## 🐳 Ejecución con Docker

```bash
mvn clean package -DskipTests
docker compose up --build

---

## 🧠 Tecnologías utilizadas

- Java 17
- Spring Boot (WebFlux)
- MongoDB (Reactive)
- Maven
- Docker
- OpenAPI (Swagger)

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
## 🐳 Ejecución con Docker (RECOMENDADO)

### 🔧 Requisitos

- Docker
- Docker Compose v2

### 🚀 Levantar aplicación

```bash
mvn clean package
docker compose up --build
```

### 📍 Servicios

- API → http://localhost:8080
- MongoDB → puerto 27017
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
## 🧪 Pruebas de la API (Postman / REST Client)

A continuación se describe el flujo completo para probar todos los endpoints de la API.

🔗 Base URL:
```
http://localhost:8080
```

⚠️ Nota: Los IDs son definidos manualmente.

---

## 1. Crear Franquicia

**POST** `/franquicias`

### Body
```json
{
  "id": "f1",
  "nombre": "Franquicia Demo",
  "sucursales": []
}
```

### Resultado esperado
- 200 OK
- Retorna la franquicia creada
---

## 2. Agregar Sucursal

**POST** `/franquicias/f1/sucursales`

### Body
```json
{
  "id": "s1",
  "nombre": "Sucursal Centro",
  "productos": []
}
```

### Resultado esperado
- 200 OK
- La sucursal queda asociada a la franquicia
---
## 3. Agregar Productos

**POST** `/franquicias/f1/sucursales/s1/productos`

### Body
#### Producto 1
```json
{
  "id": "p1",
  "nombre": "Hamburguesa",
  "stock": 50
}
```
#### Producto 2
```json
{
  "id": "p2",
  "nombre": "Papas",
  "stock": 99
}
```

### Resultado esperado
- 200 OK
- Productos agregados a la sucursal
---

## 4. Actualizar stock

**PUT** `/franquicias/productos/p1/stock`

### Body
```json
100
```

### Resultado esperado
- 200 OK
- Stock actualizado correctamente
---

## 5. Eliminar producto

**DELETE** `/franquicias/productos/p2`

### Resultado esperado
- 200 OK
- Producto eliminado de la sucursal
---

## 6. Obtener top Productos

**GET** `/franquicias/f1/top-productos`



### Resultado esperado
- 200 OK
- Producto de mayor stock por sucursal
```json
[
  {
    "productoId": "p1",
    "nombre": "Hamburguesa",
    "stock": 100,
    "sucursalId": "s1",
    "sucursalNombre": "Sucursal Centro"
  }
]
```
---

## 7. Actualizar nombre de la franquicia

**PUT** `/franquicias/f1/nombre`

### Body
```json
Franquicia nueva
```

### Resultado esperado
- 200 OK
- Nombre actualizado
---
## 8. Actualizar nombre de la sucursal

**PUT** `/franquicias/f1/sucursales/s1/nombre

### Body
```json
Sucursal nueva
```

### Resultado esperado
- 200 OK
- Nombre actualizado
---
## 9. Actualizar nombre de producto

**PUT** `/franquicias/productos/p1/nombre`

### Body
```json
Hamburguesa doble
```

### Resultado esperado
- 200 OK
- Nombre actualizado

---
## 🔄 Flujo recomendado

### Se recomienda usar Postman o herramientas similares para ejecutar las pruebas de manera ordenada siguiendo el flujo definido.

1. Crear franquicia
2. Agregar sucursales
3. Agregar productos
4. Gestionar stock
5. Consultar producto con mayor stock por sucursal
6. Eliminar producto
7. Actualizar nombres
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

Entorno Docker:

```
mongodb://localhost:27017/franquicias_db
```
Entorno local (test):

```
mongodb://localhost:27017/franquicias_test_db
```

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