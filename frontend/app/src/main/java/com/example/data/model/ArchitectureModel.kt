package com.example.data.model

data class ApiEndpointInfo(
    val method: String,
    val path: String,
    val description: String
)

data class DatabaseTableInfo(
    val tableName: String,
    val primaryKey: String,
    val fields: List<String>,
    val description: String
)

data class ArchitectureSpec(
    val backendStack: String = "Java 21 + Spring Boot 3.3 (RESTful API)",
    val databaseStack: String = "PostgreSQL 16 Relacional + PostGIS Spatial",
    val frontendStack: String = "HTML5, Vanilla JS, Tailwind CSS / Android Jetpack Compose M3",
    val versionControl: String = "Git + Repositorios GitHub (Feature Branches)",
    val endpoints: List<ApiEndpointInfo> = listOf(
        ApiEndpointInfo("GET", "/api/v1/destinations/{id}", "Obtiene locación global y metadatos turísticos"),
        ApiEndpointInfo("GET", "/api/v1/places?layer={kind}", "Directorio geolocalizado de puntos de interés y comercios"),
        ApiEndpointInfo("POST", "/api/v1/places/self-manage", "Autogestión para que dueños registren sus comercios"),
        ApiEndpointInfo("GET", "/api/v1/routes/{id}/tour", "Recorrido digital integrado con paradas y audioguía"),
        ApiEndpointInfo("POST", "/api/v1/reviews", "Publicación de reseñas y calificación de servicio a la mesa")
    ),
    val tables: List<DatabaseTableInfo> = listOf(
        DatabaseTableInfo("destinations", "id (UUID)", listOf("name", "country", "lat", "lng", "currency"), "Destinos internacionales y municipios"),
        DatabaseTableInfo("businesses", "id (UUID)", listOf("dest_id", "name", "kind", "wait_time", "service_type", "coffee_methods"), "Comercios locales y puntos patrimoniales"),
        DatabaseTableInfo("dishes", "id (UUID)", listOf("biz_id", "name", "price", "category", "is_signature"), "Catálogo de platos y cafés de origen"),
        DatabaseTableInfo("reviews", "id (UUID)", listOf("user_id", "biz_id", "rating", "comment", "service_score"), "Retroalimentación comunitaria de clientes"),
        DatabaseTableInfo("events", "id (UUID)", listOf("dest_id", "title", "event_date", "is_free", "capacity"), "Agenda de eventos culturales y ferias")
    )
)
