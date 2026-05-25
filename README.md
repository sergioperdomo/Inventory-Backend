# 📦 Inventory Backend — Spring Boot

API REST para el Sistema de Gestión de Inventario.

---

## 🛠️ Stack

| Capa | Tecnología |
|---|---|
| Lenguaje | Java 21 |
| Framework | Spring Boot 3.2.5 |
| Base de datos | MySQL 8.0 |
| ORM | Hibernate / Spring Data JPA |
| Documentación | Springdoc OpenAPI 2.8.17 (Swagger UI) |
| Contenedor BD | Docker + Docker Compose |

---

## ⚙️ Requisitos previos

- Java 21+
- Maven 3.9+
- Docker y Docker Compose
- MySQL Workbench (opcional)

---

## 🚀 Levantar el proyecto

### 1. Levantar MySQL con Docker

```bash
cd inventory-backend
docker compose up -d
```

Verifica que el contenedor esté corriendo:

```bash
docker compose ps
```

Debes ver:
```
NAME                STATUS
inventory-mysql     Up (healthy)
```

### 2. Iniciar el backend

```bash
./mvnw spring-boot:run
```

- API disponible en: `http://localhost:8080`
- Swagger UI en: `http://localhost:8080/swagger-ui.html`

---

## 🗄️ Conexión MySQL Workbench

| Campo | Valor |
|---|---|
| Connection Name | inventory-local |
| Hostname | 127.0.0.1 |
| Port | 3306 |
| Username | root |
| Default Schema | inventory_db |

---

## 📐 Estructura del proyecto

```
src/main/java/com/inventory/inventory_backend/
├── InventoryBackendApplication.java
├── config/
│   ├── CorsConfig.java              ← Permite peticiones desde Angular
│   └── DataSeeder.java              ← Datos de prueba al arrancar
├── controller/
│   ├── CategoryController.java
│   ├── ProductController.java
│   ├── SupplierController.java
│   └── StockMovementController.java
├── dto/
│   ├── CategoryRequest.java
│   ├── CategoryResponse.java
│   ├── ProductRequest.java
│   ├── ProductResponse.java
│   ├── SupplierRequest.java
│   ├── SupplierResponse.java
│   ├── StockMovementRequest.java
│   └── StockMovementResponse.java
├── entity/
│   ├── Category.java
│   ├── Product.java
│   ├── Supplier.java
│   ├── StockMovement.java
│   └── User.java
├── enums/
│   ├── Role.java                    ← ADMIN, EMPLOYEE
│   └── MovementType.java            ← ENTRADA, SALIDA
├── exception/
│   ├── GlobalExceptionHandler.java
│   ├── ResourceNotFoundException.java
│   └── BusinessException.java
├── repository/
│   ├── CategoryRepository.java
│   ├── ProductRepository.java
│   ├── SupplierRepository.java
│   ├── StockMovementRepository.java
│   └── UserRepository.java
└── service/
    ├── CategoryService.java
    ├── ProductService.java
    ├── SupplierService.java
    └── StockMovementService.java
```

---

## 🌐 Endpoints de la API

| Recurso | Método | URL | Descripción |
|---|---|---|---|
| Categorías | GET | /api/categories | Listar todas |
| Categorías | GET | /api/categories/{id} | Obtener por ID |
| Categorías | POST | /api/categories | Crear nueva |
| Categorías | PUT | /api/categories/{id} | Actualizar |
| Categorías | DELETE | /api/categories/{id} | Eliminar |
| Productos | GET | /api/products | Listar todos |
| Productos | GET | /api/products/{id} | Obtener por ID |
| Productos | GET | /api/products/low-stock | ⚠️ Alertas stock bajo |
| Productos | GET | /api/products/search?name= | Buscar por nombre |
| Productos | GET | /api/products/by-category/{id} | Filtrar por categoría |
| Productos | POST | /api/products | Crear nuevo |
| Productos | PUT | /api/products/{id} | Actualizar |
| Productos | DELETE | /api/products/{id} | Eliminar |
| Proveedores | GET | /api/suppliers | Listar todos |
| Proveedores | GET | /api/suppliers/{id} | Obtener por ID |
| Proveedores | POST | /api/suppliers | Crear nuevo |
| Proveedores | PUT | /api/suppliers/{id} | Actualizar |
| Proveedores | DELETE | /api/suppliers/{id} | Eliminar |
| Mov. stock | GET | /api/stock-movements | Listar todos |
| Mov. stock | GET | /api/stock-movements/by-product/{id} | Historial de un producto |
| Mov. stock | POST | /api/stock-movements | Registrar entrada/salida |

---

## 🧪 Datos de prueba

Al arrancar, el `DataSeeder` inserta automáticamente:

- 2 categorías: Electrónica, Papelería
- 2 proveedores: TechCorp S.A., OfficeMax Colombia
- 3 productos (1 con alerta de stock bajo)
- Historial de movimientos de stock
- 2 usuarios: `admin` (ADMIN) y `empleado1` (EMPLOYEE)

---

## ✅ Buenas prácticas aplicadas

- DTOs con Java Records (inmutables)
- `@Transactional(readOnly = true)` por defecto en servicios
- Manejo global de excepciones con `@RestControllerAdvice`
- Validaciones con `@Valid` y Bean Validation
- Separación clara Controller → Service → Repository
- CORS configurado para permitir solo `localhost:4200`

---

## 🗺️ Próximos pasos

- ⏳ Semana 5 → Autenticación JWT con Spring Security
- ⏳ Semana 6 → Dockerfile y docker-compose completo
- ⏳ Semana 7 → Despliegue en Kubernetes (minikube)