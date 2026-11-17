# Tourist Update API (Spring Boot 3 + JWT)

Update a tourist record with token validation.

## Run
Requires Java 17+ and Maven (or import into IntelliJ / Eclipse / STS).

    mvn spring-boot:run

App starts on http://localhost:8080 with an in-memory H2 DB seeded with 2 tourists (ids 1 and 2).
H2 console: http://localhost:8080/h2-console  (JDBC URL: jdbc:h2:mem:touristdb, user: sa, no password)

Run the automated tests with `mvn test`.

## Steps implemented
1. **Validate token request** - `JwtInterceptor` checks `Authorization: Bearer <token>` on `/api/tourists/**`
   (missing / invalid / expired -> 401).
2. **Update API** - `PUT /api/tourists/{id}`: fetch existing record -> modify fields -> save back
   (unknown id -> 404, bad body -> 400).
3. **Test via Postman** - import `postman/Tourist-Update-API.postman_collection.json`.

## Endpoints
| Method | URL | Auth | Purpose |
|--------|-----|------|---------|
| POST | /api/auth/login | none | returns JWT |
| GET  | /api/tourists | Bearer | list |
| GET  | /api/tourists/{id} | Bearer | fetch one |
| POST | /api/tourists | Bearer | create |
| PUT  | /api/tourists/{id} | Bearer | **update** |

## Test in Postman manually
1. `POST http://localhost:8080/api/auth/login`  body (raw JSON):
   `{"username":"admin","password":"admin123"}`  -> copy `token`
2. `PUT http://localhost:8080/api/tourists/1`
   - Authorization tab -> Bearer Token -> paste token (or header `Authorization: Bearer <token>`)
   - Body (raw JSON):

       {
         "name": "Ravi Kumar Updated",
         "email": "ravi.updated@example.com",
         "phone": "9999999999",
         "country": "India",
         "passportNumber": "P7654321"
       }

3. Expect `200 OK` with the updated record. Remove or alter the token -> `401 Unauthorized`.

Credentials and JWT secret are in `src/main/resources/application.properties` - change them for real use.
