# 🚀 MuniTour Backend — Spring Boot 3.4 & Java 21

Backend REST profesional para la plataforma **MuniTour**, diseñado para conectar la app móvil Android con base de datos PostgreSQL en **Supabase** y despliegue en **Render.com**.

---

## 📁 Estructura del Proyecto

```
munitour-backend/
├── pom.xml                               # Dependencias Maven y configuración del build
├── Dockerfile                            # Imagen optimizada Eclipse Temurin 21 para Render
├── supabase_schema.sql                   # Script DDL para ejecutar en el SQL Editor de Supabase
├── src/main/java/com/munitour/backend/
│   ├── MunitourBackendApplication.java   # Clase principal @SpringBootApplication
│   ├── config/
│   │   ├── CorsConfig.java               # Configuración CORS permisiva para Android y Swagger
│   │   ├── DataSeeder.java               # Verificador inicial de datos
│   │   └── SecurityConfig.java           # Spring Security 6, JWT, RestTemplate y rutas públicas
│   ├── controller/                       # Controladores REST (/api y /api/v1)
│   │   ├── AuthController.java           # /login, /register, /google (OAuth2)
│   │   ├── PlaceController.java          # Atractivos turísticos y gastronómicos
│   │   ├── DishController.java           # Sabores locales y café de especialidad
│   │   ├── EventController.java          # Agenda cultural y festivales
│   │   ├── RouteController.java          # Rutas culturales (audioguías) e intermunicipales
│   │   ├── ReviewController.java         # Reseñas y calificaciones
│   │   └── UserController.java           # Perfil del viajero (/me)
│   ├── dto/
│   │   ├── request/                      # DTOs de entrada con @JsonProperty en snake_case
│   │   │   ├── AuthRequestDto.java
│   │   │   ├── GoogleAuthRequestDto.java
│   │   │   ├── PlaceRequestDto.java
│   │   │   ├── OperativeDetailsRequestDto.java
│   │   │   ├── DishRequestDto.java
│   │   │   ├── EventRequestDto.java
│   │   │   ├── ReviewRequestDto.java
│   │   │   └── UserUpdateRequestDto.java
│   │   └── response/                     # DTOs de salida alineados al 100% con Android
│   │       ├── AuthResponseDto.java
│   │       ├── PlaceResponseDto.java
│   │       ├── OperativeDetailsResponseDto.java
│   │       ├── DishResponseDto.java
│   │       ├── EventResponseDto.java
│   │       ├── RouteResponseDto.java
│   │       ├── InterMunicipalRouteResponseDto.java
│   │       ├── ReviewResponseDto.java
│   │       └── UserResponseDto.java
│   ├── entity/                           # Entidades JPA con Lombok y columnas snake_case
│   │   ├── AppUser.java                  # Usuarios con soporte local + Google OAuth
│   │   ├── Place.java                    # Lugares con barismo y detalles operativos
│   │   ├── Dish.java                     # Platos gastronómicos
│   │   ├── CulturalEvent.java            # Eventos culturales
│   │   ├── CultureRoute.java             # Rutas con audioguía
│   │   ├── InterMunicipalRoute.java      # Rutas entre municipios (tren, senderos)
│   │   └── Review.java                   # Calificaciones de viajeros
│   ├── exception/
│   │   ├── BadRequestException.java
│   │   ├── ResourceNotFoundException.java
│   │   └── GlobalExceptionHandler.java   # Manejo centralizado de errores HTTP 400, 404, 500
│   ├── mapper/                           # Mappers estáticos Entidad <-> DTO
│   │   ├── PlaceMapper.java
│   │   ├── DishMapper.java
│   │   ├── EventMapper.java
│   │   ├── RouteMapper.java
│   │   ├── InterMunicipalRouteMapper.java
│   │   ├── ReviewMapper.java
│   │   └── UserMapper.java
│   ├── repository/                       # Repositorios Spring Data JPA
│   │   ├── UserRepository.java
│   │   ├── PlaceRepository.java
│   │   ├── DishRepository.java
│   │   ├── EventRepository.java
│   │   ├── CultureRouteRepository.java
│   │   ├── InterMunicipalRouteRepository.java
│   │   └── ReviewRepository.java
│   ├── security/
│   │   ├── CustomUserDetailsService.java
│   │   ├── JwtAuthenticationFilter.java  # Filtro OncePerRequest con Bearer token
│   │   └── JwtTokenProvider.java         # Generación y validación JJWT 0.12.6
│   └── service/                          # Lógica de negocio
│       ├── AuthService.java              # Registro, Login, Verificación Google ID Token
│       ├── PlaceService.java
│       ├── DishService.java
│       ├── EventService.java
│       ├── RouteService.java
│       ├── InterMunicipalRouteService.java
│       ├── ReviewService.java
│       └── UserService.java
└── src/main/resources/
    ├── application.properties            # Configuración para Supabase, Render, JWT y Swagger
    ├── application-dev.properties        # Perfil local de desarrollo
    └── data.sql                          # Datos semilla (Sabana Centro, Cajicá, Zipaquirá, Chía)
```

---

## 🛠️ Cómo abrirlo en IntelliJ IDEA

1. Abre **IntelliJ IDEA**.
2. Haz clic en **File > Open** (o **Open** en la pantalla de bienvenida).
3. Selecciona la carpeta:
   ```
   C:\Users\HP\.gemini\antigravity\scratch\munitour-backend
   ```
4. IntelliJ detectará automáticamente el archivo `pom.xml` como proyecto Maven.
5. Permite que IntelliJ descargue los índices y configure el SDK en **Java 21**.
6. Para ejecutarlo desde IntelliJ: abre `MunitourBackendApplication.java` y presiona el botón verde de **Run**.

---

## 🗄️ Configuración de Supabase (Base de Datos)

1. Ingresa a tu panel en [Supabase](https://supabase.com).
2. Crea un nuevo proyecto (por ejemplo `munitour-db`).
3. Ve a la sección **SQL Editor**.
4. Abre el archivo `supabase_schema.sql` ubicado en la raíz de este proyecto, copia todo su contenido y ejecútalo (**Run**) en Supabase.
5. Ve a **Project Settings > Database > Connection string > URI** (o Connection parameters) y copia los datos:
   - Host: `db.xxxxxxxxxxxx.supabase.co`
   - Puerto: `5432` o `6543` (pooler)
   - Usuario: `postgres`
   - Contraseña: Tu contraseña asignada al crear la BD
6. En `application.properties` (o como variable de entorno), reemplaza:
   ```properties
   spring.datasource.url=jdbc:postgresql://db.xxxxxxxxxxxx.supabase.co:5432/postgres?sslmode=require
   spring.datasource.username=postgres
   spring.datasource.password=TU_PASSWORD_DE_SUPABASE
   ```

---

## 🔑 Login con Google (OAuth2)

El endpoint `POST /api/auth/google` recibe:
```json
{
  "id_token": "TU_GOOGLE_ID_TOKEN"
}
```
1. El backend contacta el endpoint oficial de Google (`https://oauth2.googleapis.com/tokeninfo?id_token=...`).
2. Verifica la firma, extrae el correo, nombre y el `sub` (Google ID único).
3. Si el usuario ya existe, inicia sesión; si no, lo registra automáticamente con nivel "Explorador Cittaslow".
4. Devuelve un JWT firmado listo para ser usado en el header `Authorization: Bearer <TOKEN>`.

---

## 🌐 Documentación Interactiva (Swagger / OpenAPI)

Una vez que el backend esté en ejecución, puedes probar todos los endpoints desde el navegador en:
```
http://localhost:8080/swagger-ui.html
```
O ver la especificación OpenAPI JSON en:
```
http://localhost:8080/api-docs
```

---

## ☁️ Despliegue en Render.com

1. Sube este proyecto a tu repositorio de **GitHub**.
2. En [Render.com](https://render.com), haz clic en **New + > Web Service**.
3. Conecta tu repositorio `munitour-backend`.
4. Render detectará automáticamente el `Dockerfile` o Maven:
   - **Runtime:** Docker (usará el `Dockerfile` optimizado)
   - O si eliges **Java/Native**:
     - **Build Command:** `mvn clean package -DskipTests`
     - **Start Command:** `java -jar target/munitour-backend-0.0.1-SNAPSHOT.jar`
5. En **Environment Variables**, agrega:
   - `SPRING_DATASOURCE_URL`: `jdbc:postgresql://<HOST>:5432/postgres?sslmode=require`
   - `SPRING_DATASOURCE_USERNAME`: `postgres`
   - `SPRING_DATASOURCE_PASSWORD`: `<TU_PASSWORD>`
   - `JWT_SECRET`: `<UNA_CLAVE_LARGA_Y_SEGURA>`
   - `PORT`: `8080`
6. ¡Listo! Render te generará una URL pública (ejemplo: `https://munitour-api.onrender.com`).

---

## 📱 Conexión con el Frontend Android

En tu aplicación Android (`c:\Users\HP\Downloads\munitour-20-oct-2026-10-53-26-p-m`):
- `ApiClient.kt` ya tiene configurado:
  ```kotlin
  "https://munitour-api.onrender.com/api/v1/"
  ```
- Para probar en el **emulador local** de Android Studio apuntando a tu backend en IntelliJ:
  ```kotlin
  ApiClient.updateBaseUrl("http://10.0.2.2:8080/")
  ```
- Todos los DTOs, nombres de campos en snake_case (`destination_id`, `price_tier`, `coffee_methods`, `review_count`, etc.) y rutas están sincronizados para compatibilidad inmediata.
