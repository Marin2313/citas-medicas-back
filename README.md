# README - Backend (API) | Citas Médicas

API REST para gestión de **Doctores**, **Pacientes** y **Agenda**, con autenticación por **JWT**.

---

## Requisitos

* Java 21
* Maven

---

## Ejecución

1. Entrar a la carpeta del backend:

   ```bash
   cd backend
   ```
2. Ejecutar con Maven:

   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

(Opcional) generar jar:

```bash
mvn clean package
java -jar target/*.jar
```

---

## Configuración

Revisar:

* `application.yml` o `application.properties`

Asegurar:

* Puerto configurado (ej. 8080)
* Base path / context-path (si aplica)
* CORS habilitado para `http://localhost:4200`

---

## JWT (cómo se usa en el backend)

* El login genera un token JWT para usuarios registrados.
* Los endpoints protegidos deben recibir el header:

  * `Authorization: Bearer <token>`

---

## Endpoints (referencia)

> Ajustar según los paths reales del proyecto si cambian.

* Auth:

  * `POST /api/auth/login`
* Doctores:

  * `GET /api/doctors`
  * `POST /api/doctors`
  * `PUT /api/doctors/{id}`
  * `DELETE /api/doctors/{id}`
* Pacientes:

  * `GET /api/patients`
  * `POST /api/patients`
  * `PUT /api/patients/{id}`
  * `DELETE /api/patients/{id}`
* Agenda:

  * `GET /api/doctors/{id}/agenda` (o endpoint equivalente)

---

## Códigos de error esperados

* `400` Validación (campos requeridos, formatos inválidos)
* `401/403` No autorizado (sin token / token inválido)
* `404` Recurso no encontrado
* `500` Error interno del servidor

---

## Prueba rápida con cURL (ejemplo)

### Login

```bash
curl -X POST "http://localhost:8080/api/auth/login" \
  -H "Content-Type: application/json" \
  -d '{"username":"USUARIO","password":"PASSWORD"}'
```

### Consumir endpoint con JWT

```bash
curl -X GET "http://localhost:8080/api/doctors" \
  -H "Authorization: Bearer TU_TOKEN_AQUI"
```
