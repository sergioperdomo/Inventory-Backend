# Inventory Backend — Spring Boot

API REST para el Sistema de Gestión de Inventario.

## Requisitos previos

- Java 21+
- Maven 3.9+
- Docker y Docker Compose

## Levantar el proyecto

### 1. Iniciar la base de datos con Docker

```bash
# Desde la raíz del proyecto
docker compose up -d

# Verificar que MySQL está corriendo
docker compose ps
```

phpMyAdmin estará disponible en: http://localhost:8081

### 2. Iniciar el backend

```bash
./mvnw spring-boot:run
```

La API estará disponible en: http://localhost:8080

### 3. Abrir Swagger UI

http://localhost:8080/swagger-ui.html

Ahí puedes probar todos los endpoints directamente desde el navegador.

---

## Endpoints disponibles

| Recurso            | Método | URL                                      | Descripción                    |
|--------------------|--------|------------------------------------------|--------------------------------|
| Categorías         | GET    | /api/categories                          | Listar todas                   |
| Categorías         | GET    | /api/categories/{id}                     | Obtener por ID                 |
| Categorías         | POST   | /api/categories                          | Crear nueva                    |
| Categorías         | PUT    | /api/categories/{id}                     | Actualizar                     |
| Categorías         | DELETE | /api/categories/{id}                     | Eliminar                       |
| Productos          | GET    | /api/products                            | Listar todos                   |
| Productos          | GET    | /api/products/{id}                       | Obtener por ID                 |
| Productos          | GET    | /api/products/low-stock                  | ⚠️ Alertas de stock bajo       |
| Productos          | GET    | /api/products/search?name=               | Buscar por nombre              |
| Productos          | GET    | /api/products/by-category/{categoryId}   | Filtrar por categoría          |
| Productos          | POST   | /api/products                            | Crear nuevo                    |
| Productos          | PUT    | /api/products/{id}                       | Actualizar                     |
| Productos          | DELETE | /api/products/{id}                       | Eliminar                       |
| Proveedores        | GET    | /api/suppliers                           | Listar todos                   |
| Proveedores        | POST   | /api/suppliers                           | Crear nuevo                    |
| Proveedores        | PUT    | /api/suppliers/{id}                      | Actualizar                     |
| Proveedores        | DELETE | /api/suppliers/{id}                      | Eliminar                       |
| Mov. de stock      | GET    | /api/stock-movements                     | Listar todos                   |
| Mov. de stock      | GET    | /api/stock-movements/by-product/{id}     | Historial de un producto       |
| Mov. de stock      | POST   | /api/stock-movements                     | Registrar entrada/salida       |

---

## Estructura del proyecto

```
src/main/java/com/inventory/
├── InventoryBackendApplication.java   ← Punto de entrada
├── config/
│   └── DataSeeder.java                ← Datos de prueba al arrancar
├── controller/                        ← Endpoints REST
├── dto/                               ← Request y Response (records Java)
├── entity/                            ← Entidades JPA (tablas)
├── enums/                             ← Role, MovementType
├── exception/                         ← Manejo global de errores
├── repository/                        ← Acceso a datos (Spring Data)
└── service/                           ← Lógica de negocio
```

---

## Datos de prueba

Al arrancar, el `DataSeeder` inserta automáticamente:
- 2 categorías (Electrónica, Papelería)
- 2 proveedores (TechCorp, OfficeMax)
- 3 productos (con 1 en alerta de stock bajo)
- Historial de movimientos de stock
- 2 usuarios (admin / empleado1)

---

## Próximos pasos

- **Semana 3-4**: Frontend Angular 18+
- **Semana 5**: Autenticación JWT con Spring Security
- **Semana 6**: Dockerizar el backend
- **Semana 7**: Despliegue en Kubernetes (minikube)