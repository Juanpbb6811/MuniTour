# 🧭 MuniTour — Plataforma de Turismo Intermunicipal y Gastronomía

Plataforma integral para la promoción del turismo sostenible, la gastronomía típica, el café de especialidad y la agenda cultural de los municipios de **Sabana Centro** (Cajicá, Zipaquirá, Chía, Tabio, Sopó).

---

## 🏛️ Arquitectura del Repositorio (Monorepo)

```
MuniTour/
├── frontend/             # Aplicación Móvil Android (Kotlin + Jetpack Compose)
│   ├── app/              # Código fuente de la app Android
│   ├── gradle/           # Wrapper y catálogo de dependencias
│   ├── build.gradle.kts  # Configuración Gradle
│   └── README.md
│
├── backend/              # API REST Backend (Java 21 + Spring Boot 3.4)
│   ├── src/              # Controladores, Servicios, Entidades JPA, DTOs, Mappers
│   ├── pom.xml           # Configuración Maven
│   ├── Dockerfile        # Imagen Docker optimizada para Render.com
│   ├── supabase_schema.sql # Esquema DDL para PostgreSQL en Supabase
│   └── README.md
│
├── .gitignore            # Reglas de exclusión para Android, Java, IDEs y secretos
└── README.md             # Esta documentación
```

---

## 📱 1. Frontend — Aplicación Android

Desarrollada en **Kotlin** con **Jetpack Compose**, Material 3, ViewModel y Retrofit/Moshi.

### Características Principales:
* **Mapa interactivo:** Visualización de atractivos turísticos y gastronómicos en Sabana Centro.
* **Sabores locales:** Catálogo de cafés de especialidad, restaurantes tradicionales y platos típicos.
* **Agenda cultural:** Festivales, eventos municipales y recordatorios con un toque.
* **Rutas intermunicipales:** Recorridos temáticos (Ruta Férrea del Tren de la Sabana, Travesías ecológicas por el Cerro Majuy).
* **Perfil del viajero:** Estadísticas, eco-puntos y modo explorador.

### Cómo abrirlo y ejecutarlo:
1. Abre **Android Studio**.
2. Selecciona **Open** y elige la carpeta `frontend/`.
3. Deja que Gradle sincronice las dependencias.
4. Conecta un dispositivo físico o inicia un emulador y presiona **Run (Shift + F10)**.

---

## ⚙️ 2. Backend — API REST en Spring Boot

Desarrollado en **Java 21** con **Spring Boot 3.4**, Spring Data JPA, Spring Security, JWT y Swagger.

### Características Principales:
* **Endpoints REST completos:** `/api/places`, `/api/dishes`, `/api/events`, `/api/routes`, `/api/reviews`, `/api/users`.
* **Autenticación dual:** Registro/login tradicional con contraseña encriptada (BCrypt) + Login con Google OAuth2 (`POST /api/auth/google`).
* **Base de datos:** Compatible con PostgreSQL en la nube a través de **Supabase**.
* **Documentación interactiva:** Swagger UI en `/swagger-ui.html`.
* **Despliegue:** Optimizado para **Render.com** mediante `Dockerfile`.

### Cómo abrirlo y ejecutarlo en IntelliJ IDEA:
1. Abre **IntelliJ IDEA**.
2. Selecciona **File > Open** y elige la carpeta `backend/`.
3. IntelliJ detectará automáticamente el archivo `pom.xml` como proyecto Maven.
4. Abre `src/main/java/com/munitour/backend/MunitourBackendApplication.java` y haz clic en **Run ▶**.

---

## 🗄️ 3. Base de Datos en Supabase

1. Crea un proyecto en [Supabase](https://supabase.com).
2. Ve al **SQL Editor**, copia el contenido de `backend/supabase_schema.sql` y ejecútalo (**Run**).
3. En **Project Settings > Database**, obtén la cadena de conexión JDBC y configúrala en `backend/src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:postgresql://<TU-HOST-SUPABASE>:5432/postgres?sslmode=require
   spring.datasource.username=postgres
   spring.datasource.password=<TU-PASSWORD>
   ```

---

## ☁️ 4. Despliegue en Render.com

1. Conecta este repositorio en [Render.com](https://render.com) como **Web Service**.
2. Configura:
   * **Root Directory:** `backend`
   * **Runtime:** Docker (usará el `Dockerfile` de la carpeta backend)
3. Agrega las variables de entorno:
   * `SPRING_DATASOURCE_URL`: Tu URL de Supabase con `?sslmode=require`
   * `SPRING_DATASOURCE_USERNAME`: `postgres`
   * `SPRING_DATASOURCE_PASSWORD`: Tu contraseña de Supabase
   * `JWT_SECRET`: Clave secreta para firmar tokens JWT
   * `PORT`: `8080`

---

## 👥 Equipo y Autores
* **Juan Pablo Bejarano** ([@Juanpbb6811](https://github.com/Juanpbb6811))
