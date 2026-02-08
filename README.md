# tic-jwt-full-final

## What you get
- Register + upload profile/signature images (Multipart)
- Login with email OR nationalId (JWT)
- JWT includes user claims
- Response includes token + user object
- Uploaded files served via /uploads/**

## Setup
1) Create DB:
```sql
CREATE DATABASE IF NOT EXISTS sso;
```

2) Edit `src/main/resources/application.properties`:
- MySQL password
- `file.upload-dir` (create the folder)
- `security.jwt.secret` (>= 32 chars)

3) Run:
```bash
mvn clean spring-boot:run
```

## Postman
### Register (form-data)
POST http://localhost:8080/api/auth/register
Keys:
- firstName (text)
- lastName (text)
- email (text)
- password (text)
- phoneNumber (text optional)
- nationalId (text optional)
- profileImage (file optional)
- signatureImage (file optional)

### Login (JSON)
POST http://localhost:8080/api/auth/login
{
  "identifier": "awad@test.com",
  "password": "123456"
}

### Secure endpoint
GET http://localhost:8080/api/secure/ping
Header: Authorization: Bearer <token>

### Open uploaded image
http://localhost:8080/uploads/profile/<fileName>
