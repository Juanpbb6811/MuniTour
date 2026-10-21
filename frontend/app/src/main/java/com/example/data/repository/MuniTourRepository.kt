package com.example.data.repository

import com.example.data.model.CulturalEvent
import com.example.data.model.CultureRoute
import com.example.data.model.Destination
import com.example.data.model.Dish
import com.example.data.model.InterMunicipalRoute
import com.example.data.model.OperativeDetailsService
import com.example.data.model.Place
import com.example.data.model.Review
import com.example.data.model.UserProfile
import com.example.data.api.ApiClient
import com.example.data.api.PlaceDto
import com.example.data.api.OperativeDetailsDto
import com.example.data.api.DishDto
import com.example.data.api.CulturalEventDto
import com.example.data.api.CultureRouteDto
import com.example.data.api.ReviewDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MuniTourRepository {

    // Municipalities of Sabana Centro & Regional network
    val destinations = listOf(
        Destination(
            id = "cajica",
            name = "Cajicá",
            region = "Sabana Centro, Cundinamarca",
            country = "Colombia",
            flag = "🇨🇴",
            currencySymbol = "$",
            currencyCode = "COP",
            tagLine = "Fortaleza de piedra, café de altura y Ciudad Lenta (Cittaslow)",
            description = "Municipio modelo de turismo sostenible, senderos en Montepincio, amasijos de Busongote y polo gastronómico de café de origen a 17 km de Bogotá."
        ),
        Destination(
            id = "zipaquira",
            name = "Zipaquirá",
            region = "Sabana Centro",
            country = "Colombia",
            flag = "🇨🇴",
            currencySymbol = "$",
            currencyCode = "COP",
            tagLine = "Capital de la Sal y arquitectura colonial comunera",
            description = "Hogar de la Primera Maravilla de Colombia (Catedral de Sal) y punto terminal del histórico Turistren."
        ),
        Destination(
            id = "chia",
            name = "Chía",
            region = "Sabana Centro",
            country = "Colombia",
            flag = "🇨🇴",
            currencySymbol = "$",
            currencyCode = "COP",
            tagLine = "Ciudad de la Luna, gastronomía y patrimonio de haciendas",
            description = "Destino emblemático de vida nocturna, asados, casonas coloniales y nodo de conexión de la Autopista Norte."
        ),
        Destination(
            id = "tabio",
            name = "Tabio",
            region = "Sabana Centro",
            country = "Colombia",
            flag = "🇨🇴",
            currencySymbol = "$",
            currencyCode = "COP",
            tagLine = "Aguas termales medicinales y la mística Peña de Juaica",
            description = "Pueblo tranquilo con arquitectura tradicional, baños termales El Zipa y senderos de montaña andina."
        ),
        Destination(
            id = "sopo",
            name = "Sopó",
            region = "Sabana Centro",
            country = "Colombia",
            flag = "🇨🇴",
            currencySymbol = "$",
            currencyCode = "COP",
            tagLine = "Parapente en Pionono y tradición láctea sabanera",
            description = "Paisajes de alta montaña, santuario de los Arcángeles y dulces tradicionales de leche fresca."
        )
    )

    // User Profile & Authentication State
    private val _currentUser = MutableStateFlow(
        UserProfile(
            id = "user_guest",
            name = "Juan Pablo Bejarano",
            email = "juanpablobejarano0@gmail.com",
            avatarEmoji = "🧭",
            originCity = "Cajicá / Bogotá",
            travelerLevel = "Explorador Cittaslow",
            completedRoutesCount = 4,
            visitedPlacesCount = 11,
            reviewsCount = 6,
            ecoPoints = 420,
            favoriteCoffeeMethod = "V60 Dripper",
            isLoggedIn = true
        )
    )
    val currentUser: StateFlow<UserProfile> = _currentUser.asStateFlow()

    fun login(email: String, name: String) {
        _currentUser.update {
            it.copy(
                email = email,
                name = name.ifBlank { "Viajero Sabanero" },
                isLoggedIn = true
            )
        }
    }

    fun logout() {
        _currentUser.update {
            it.copy(
                isLoggedIn = false
            )
        }
    }

    fun updateProfile(name: String, originCity: String, avatarEmoji: String, favoriteCoffee: String) {
        _currentUser.update {
            it.copy(
                name = name,
                originCity = originCity,
                avatarEmoji = avatarEmoji,
                favoriteCoffeeMethod = favoriteCoffee
            )
        }
    }

    private val _places = MutableStateFlow<List<Place>>(initialPlaces())
    val places: StateFlow<List<Place>> = _places.asStateFlow()

    private val _dishes = MutableStateFlow<List<Dish>>(initialDishes())
    val dishes: StateFlow<List<Dish>> = _dishes.asStateFlow()

    private val _events = MutableStateFlow<List<CulturalEvent>>(initialEvents())
    val events: StateFlow<List<CulturalEvent>> = _events.asStateFlow()

    private val _routes = MutableStateFlow<List<CultureRoute>>(initialRoutes())
    val routes: StateFlow<List<CultureRoute>> = _routes.asStateFlow()

    private val _interMunicipalRoutes = MutableStateFlow<List<InterMunicipalRoute>>(initialInterMunicipalRoutes())
    val interMunicipalRoutes: StateFlow<List<InterMunicipalRoute>> = _interMunicipalRoutes.asStateFlow()

    private val _reviews = MutableStateFlow<List<Review>>(initialReviews())
    val reviews: StateFlow<List<Review>> = _reviews.asStateFlow()

    private val _favorites = MutableStateFlow<Set<String>>(setOf("p:cafe-central", "p:helados-san-jeronimo", "d:helado-maracuya", "e:e-busongote"))
    val favorites: StateFlow<Set<String>> = _favorites.asStateFlow()

    private val _reminders = MutableStateFlow<Set<String>>(setOf("e-busongote", "e-tunas"))
    val reminders: StateFlow<Set<String>> = _reminders.asStateFlow()

    fun toggleFavorite(key: String): Boolean {
        var added = false
        _favorites.update { current ->
            if (current.contains(key)) {
                current - key
            } else {
                added = true
                current + key
            }
        }
        return added
    }

    fun toggleReminder(eventId: String): Boolean {
        var active = false
        _reminders.update { current ->
            if (current.contains(eventId)) {
                current - eventId
            } else {
                active = true
                current + eventId
            }
        }
        return active
    }

    fun addPlace(place: Place) {
        _places.update { it + place }
    }

    fun addDish(dish: Dish) {
        _dishes.update { it + dish }
    }

    fun addReview(review: Review) {
        _reviews.update { listOf(review) + it }
        _currentUser.update { it.copy(reviewsCount = it.reviewsCount + 1, ecoPoints = it.ecoPoints + 25) }
    }

    private fun initialPlaces(): List<Place> {
        return listOf(
            Place(
                id = "parque",
                destinationId = "cajica",
                municipality = "Cajicá",
                kind = "cultura",
                name = "Parque Principal de Cajicá",
                tag = "Ruta cultural, centro del municipio",
                emoji = "🏛️",
                stars = 5,
                reviewCount = 184,
                latitude = 4.9186,
                longitude = -74.0259,
                xRatio = 0.52f,
                yRatio = 0.30f,
                address = "Carrera 3 # 3-10, Cajicá",
                distanceKm = 0.1f,
                operative = OperativeDetailsService(
                    tableServiceDynamics = "Plaza peatonal histórica y punto de encuentro",
                    estimatedWaitMinutes = "Acceso libre",
                    businessHours = "Abierto 24 horas",
                    priceTier = "Gratis",
                    ownerStory = "Plaza de la Cultura, escenario de retretas dominicales y festivales tradicionales."
                )
            ),
            Place(
                id = "estacion-tren",
                destinationId = "cajica",
                municipality = "Cajicá",
                kind = "cultura",
                name = "Estación del Ferrocarril (Turistren & RegioTram)",
                tag = "Patrimonio férreo y punto de atención turística",
                emoji = "🚂",
                stars = 5,
                reviewCount = 126,
                latitude = 4.9165,
                longitude = -74.0260,
                xRatio = 0.46f,
                yRatio = 0.36f,
                address = "Calle 1A # 0-40, Piso 3",
                distanceKm = 0.3f,
                operative = OperativeDetailsService(
                    tableServiceDynamics = "Oficina de Turismo Municipal y parada del Turistren",
                    estimatedWaitMinutes = "Atención 9:00 a.m. - 4:00 p.m.",
                    businessHours = "Lun - Dom: 9:00 a.m. - 4:00 p.m.",
                    priceTier = "Gratis",
                    ownerStory = "Histórica estación de tren del siglo XX, sede de la Coordinación de Turismo y futura estación del RegioTram del Norte."
                )
            ),
            Place(
                id = "casa-cultura",
                destinationId = "cajica",
                municipality = "Cajicá",
                kind = "cultura",
                name = "Centro Cultural Fernando Botero",
                tag = "Mega Biblioteca, auditorios y exposiciones",
                emoji = "🎭",
                stars = 5,
                reviewCount = 152,
                latitude = 4.9198,
                longitude = -74.0272,
                xRatio = 0.38f,
                yRatio = 0.42f,
                address = "Calle 4 # 4-22, Cajicá",
                distanceKm = 0.4f,
                operative = OperativeDetailsService(
                    tableServiceDynamics = "Salas de arte, auditorio para 650 personas y talleres",
                    estimatedWaitMinutes = "Entrada libre",
                    businessHours = "Mar - Dom: 8:00 a.m. - 6:30 p.m.",
                    priceTier = "Gratis",
                    ownerStory = "Inaugurado en 2016, el complejo cultural más grande y moderno de Sabana Centro."
                )
            ),
            Place(
                id = "mirador-cumbre",
                destinationId = "cajica",
                municipality = "Cajicá",
                kind = "cultura",
                name = "Mirador La Cumbre (Cerro Montepincio)",
                tag = "Sendero ecológico (2.842 msnm) y Altos de Paito",
                emoji = "⛰️",
                stars = 5,
                reviewCount = 94,
                latitude = 4.9310,
                longitude = -74.0385,
                xRatio = 0.72f,
                yRatio = 0.40f,
                address = "Avenida Manas-Molino, Sector Altos de Paito",
                distanceKm = 2.4f,
                operative = OperativeDetailsService(
                    tableServiceDynamics = "Sendero ecológico de 11.6 km, zonas de picnic y primeros auxilios",
                    estimatedWaitMinutes = "Ascenso 1h 30min",
                    businessHours = "Fines de semana: 6:00 a.m. - 5:30 p.m.",
                    priceTier = "$ (Acceso parqueadero)",
                    ownerStory = "Cima emblemática con vista de 360 grados de toda la Sabana de Bogotá y páramos circundantes."
                )
            ),
            Place(
                id = "ruinas-busongote",
                destinationId = "cajica",
                municipality = "Cajicá",
                kind = "cultura",
                name = "Ruinas de Busongote / Fortaleza de Piedra",
                tag = "Asentamiento ancestral muisca y muros coloniales",
                emoji = "🏺",
                stars = 5,
                reviewCount = 67,
                latitude = 4.9350,
                longitude = -74.0320,
                xRatio = 0.65f,
                yRatio = 0.28f,
                address = "Sector Las Manas - Montepincio",
                distanceKm = 2.8f,
                operative = OperativeDetailsService(
                    tableServiceDynamics = "Circuito arqueológico interpretativo",
                    estimatedWaitMinutes = "Recorrido guiado",
                    businessHours = "Visitas con guías patrimoniales",
                    priceTier = "Gratis",
                    ownerStory = "El enclave prehispánico original de Cajicá, conocido en crónicas como la Fortaleza de Piedra de Busongote."
                )
            ),
            Place(
                id = "periland",
                destinationId = "cajica",
                municipality = "Cajicá",
                kind = "cultura",
                name = "Periland Eco Park (Cuenca Río Bogotá)",
                tag = "Santuario forestal, mirador El Rocinante y siembra",
                emoji = "🌲",
                stars = 5,
                reviewCount = 145,
                latitude = 4.9450,
                longitude = -74.0150,
                xRatio = 0.85f,
                yRatio = 0.22f,
                address = "Km 4.5 Vía Cajicá - Zipaquirá (Sector Las Manas)",
                distanceKm = 3.6f,
                operative = OperativeDetailsService(
                    tableServiceDynamics = "6 km de senderos, avistamiento de aves, camping y programa Apadrina un Árbol",
                    estimatedWaitMinutes = "Tarifa entrada $6.000 - $7.000",
                    businessHours = "Sábados, domingos y festivos: 10:00 a.m. - 5:30 p.m.",
                    priceTier = "$",
                    ownerStory = "13.4 hectáreas de bosque andino dedicado a la restauración ecológica y reforestación de alisos y cedros con la Fundación Bosques Verdes."
                )
            ),
            Place(
                id = "helados-san-jeronimo",
                destinationId = "cajica",
                municipality = "Cajicá",
                kind = "sabor",
                name = "Helados y Postres San Jerónimo",
                tag = "Institución familiar de casi 70 años de tradición",
                emoji = "🍨",
                stars = 5,
                reviewCount = 310,
                latitude = 4.9175,
                longitude = -74.0263,
                xRatio = 0.49f,
                yRatio = 0.54f,
                address = "Calle 3 # 3-24, Centro Cajicá",
                distanceKm = 0.2f,
                operative = OperativeDetailsService(
                    coffeeMethods = listOf("Café con leche espumoso", "Tinto campesino"),
                    coffeeOrigin = "Café de origen colombiano",
                    baristaSpecialty = "Afogattos con helado artesanal de vainilla y maracuyá",
                    tableServiceDynamics = "Atención en barra y salón de mesas tradicional",
                    estimatedWaitMinutes = "3 - 6 min",
                    businessHours = "Lun - Dom: 10:00 a.m. - 8:00 p.m.",
                    priceTier = "$",
                    ownerStory = "Fundado hace casi siete décadas, famoso en toda la sabana por sus postres de arroz con leche, milhojas de arequipe y helados naturales."
                )
            ),
            Place(
                id = "fundo-llanero",
                destinationId = "cajica",
                municipality = "Cajicá",
                kind = "sabor",
                name = "Restaurante Fundo Llanero Cajicá",
                tag = "Mamona al carbón, ternera a la llanera y ajiaco",
                emoji = "🥩",
                stars = 5,
                reviewCount = 280,
                latitude = 4.9120,
                longitude = -74.0290,
                xRatio = 0.38f,
                yRatio = 0.68f,
                address = "Km 2.5 Vía Cajicá - Chía (Sector Capellanía)",
                distanceKm = 1.2f,
                operative = OperativeDetailsService(
                    coffeeMethods = listOf("Café campesino de olla"),
                    coffeeOrigin = "Café tostado artesanal",
                    baristaSpecialty = "Bebidas calientes y jugos naturales de la llanura",
                    tableServiceDynamics = "Servicio campestre a la mesa para grupos y familias",
                    estimatedWaitMinutes = "12 - 18 min",
                    businessHours = "Jue - Dom: 11:30 a.m. - 6:30 p.m.",
                    priceTier = "$$",
                    ownerStory = "Especialistas en la auténtica culinaria de los llanos orientales, carnes asadas a fuego lento en vara de leña."
                )
            ),
            Place(
                id = "level-up",
                destinationId = "cajica",
                municipality = "Cajicá",
                kind = "sabor",
                name = "Level Up Gamer Bar & Restaurant",
                tag = "Burgers de autor, consolas retro y bolirana",
                emoji = "🎮",
                stars = 5,
                reviewCount = 168,
                latitude = 4.9180,
                longitude = -74.0280,
                xRatio = 0.42f,
                yRatio = 0.50f,
                address = "Carrera 3 # 2-15, Cajicá",
                distanceKm = 0.3f,
                operative = OperativeDetailsService(
                    coffeeMethods = listOf("Espresso", "Frappé especial Mario Bros"),
                    coffeeOrigin = "Café de tostión media sabanero",
                    baristaSpecialty = "Malteadas temáticas y cervezas artesanales",
                    tableServiceDynamics = "Mesas equipadas con consolas PS5, arcades clásicos y zona de bolirana",
                    estimatedWaitMinutes = "10 - 15 min",
                    businessHours = "Mar - Dom: 2:00 p.m. - 10:30 p.m.",
                    priceTier = "$$",
                    ownerStory = "Emprendimiento pionero en entretenimiento gastronómico donde convergen videojuegos, hamburguesas gourmet y juegos típicos colombianos."
                )
            ),
            Place(
                id = "cafe-central",
                destinationId = "cajica",
                municipality = "Cajicá",
                kind = "sabor",
                name = "Café Central de Origen",
                tag = "Café de especialidad, V60, Chemex y barismo",
                emoji = "☕",
                stars = 5,
                reviewCount = 210,
                latitude = 4.9190,
                longitude = -74.0250,
                xRatio = 0.58f,
                yRatio = 0.48f,
                address = "Carrera 4 # 2-45, Centro",
                distanceKm = 0.2f,
                operative = OperativeDetailsService(
                    coffeeMethods = listOf("V60 Dripper", "Chemex", "Prensa Francesa", "Espresso Barista", "Aeropress"),
                    coffeeOrigin = "Variedad Castillo y Geisha de fincas de Cundinamarca (1.950 m.s.n.m.)",
                    baristaSpecialty = "Barismo de precisión con tueste medio propio y notas a caramelo y frutas amarillas",
                    tableServiceDynamics = "Servicio completo a la mesa con explicación sensorial de cada método por el barista",
                    estimatedWaitMinutes = "6 - 10 min",
                    businessHours = "Lun - Sáb: 7:30 a.m. - 8:30 p.m. / Dom: 8:00 a.m. - 7:00 p.m.",
                    priceTier = "$$",
                    ownerStory = "Tostaduría y cafetería de especialidad fundada por jóvenes baristas de Cajicá para visibilizar el grano regional."
                )
            ),
            Place(
                id = "cafe-sabana",
                destinationId = "cajica",
                municipality = "Cajicá",
                kind = "sabor",
                name = "Café Sabana & Panadería",
                tag = "Café orgánico del Majuy y torta de mazorca",
                emoji = "☕",
                stars = 4,
                reviewCount = 115,
                latitude = 4.9168,
                longitude = -74.0278,
                xRatio = 0.32f,
                yRatio = 0.58f,
                address = "Calle 3 # 5-18, Cajicá",
                distanceKm = 0.5f,
                operative = OperativeDetailsService(
                    coffeeMethods = listOf("Espresso", "Prensa Francesa", "Moka Italiana"),
                    coffeeOrigin = "Café orgánico de la Serranía del Majuy",
                    baristaSpecialty = "Capuchinos artesanales con leche de hatos de la sabana",
                    tableServiceDynamics = "Atención ágil en mesa y terraza exterior pet friendly",
                    estimatedWaitMinutes = "5 - 8 min",
                    businessHours = "Lun - Dom: 6:30 a.m. - 8:00 p.m.",
                    priceTier = "$",
                    ownerStory = "Amasijos recién horneados y café de montaña frente al parque municipal."
                )
            ),
            Place(
                id = "fonda-fogon",
                destinationId = "cajica",
                municipality = "Cajicá",
                kind = "sabor",
                name = "Fonda El Fogón Sabanero",
                tag = "Caldos criollos, arepas de chócolo en tiesto",
                emoji = "🍲",
                stars = 5,
                reviewCount = 265,
                latitude = 4.9188,
                longitude = -74.0242,
                xRatio = 0.62f,
                yRatio = 0.62f,
                address = "Carrera 2 # 1-30, Cajicá",
                distanceKm = 0.4f,
                operative = OperativeDetailsService(
                    coffeeMethods = listOf("Café campesino de olla con panela y clavo"),
                    coffeeOrigin = "Café de finca con molienda tradicional",
                    baristaSpecialty = "Tinto campesino caliente en leña",
                    tableServiceDynamics = "Servicio a la mesa con platos de barro cocido",
                    estimatedWaitMinutes = "10 - 15 min",
                    businessHours = "Mié - Dom: 7:00 a.m. - 5:00 p.m.",
                    priceTier = "$$",
                    ownerStory = "Tres generaciones preparando el mejor caldo de costilla y arepas de maíz pelao en tiesto de barro."
                )
            ),
            Place(
                id = "tejidos-victoria",
                destinationId = "cajica",
                municipality = "Cajicá",
                kind = "cultura",
                name = "Tejidos Victoria (Maestro Pedro Manuel Sánchez)",
                tag = "Tejeduría ancestral en lana virgen de oveja",
                emoji = "🧶",
                stars = 5,
                reviewCount = 74,
                latitude = 4.9240,
                longitude = -74.0210,
                xRatio = 0.70f,
                yRatio = 0.20f,
                address = "Vereda Chuntame, Cajicá",
                distanceKm = 1.4f,
                operative = OperativeDetailsService(
                    tableServiceDynamics = "Taller demostrativo de telar de pedales y tejido en dos agujas",
                    estimatedWaitMinutes = "Atención personalizada",
                    businessHours = "Mar - Dom: 9:00 a.m. - 6:00 p.m.",
                    priceTier = "$$",
                    ownerStory = "Pedro Manuel Sánchez, maestro artesano reconocido en Colombia Artesanal por sus ruanas y cobijas de lana hilada a mano."
                )
            ),
            Place(
                id = "catedral-sal",
                destinationId = "zipaquira",
                municipality = "Zipaquirá",
                kind = "cultura",
                name = "Catedral de Sal de Zipaquirá",
                tag = "Primera Maravilla de Colombia a 180m bajo tierra",
                emoji = "⛪",
                stars = 5,
                reviewCount = 1420,
                latitude = 5.0192,
                longitude = -74.0041,
                xRatio = 0.88f,
                yRatio = 0.10f,
                address = "Parque de la Sal, Zipaquirá",
                distanceKm = 14.2f,
                operative = OperativeDetailsService(
                    tableServiceDynamics = "Recorridos subterráneos con audioguías multilingües",
                    estimatedWaitMinutes = "Tours cada 15 min",
                    businessHours = "Lun - Dom: 9:00 a.m. - 5:40 p.m.",
                    priceTier = "$$$",
                    ownerStory = "Monumento arquitectónico y religioso tallado en roca salina dentro de las antiguas minas."
                )
            ),
            Place(
                id = "hacienda-noviciado",
                destinationId = "chia",
                municipality = "Chía / Cota",
                kind = "cultura",
                name = "Hacienda El Noviciado (UniAndes)",
                tag = "Bien de Interés Cultural Nacional (siglo XVII)",
                emoji = "🏛️",
                stars = 5,
                reviewCount = 88,
                latitude = 4.8450,
                longitude = -74.0750,
                xRatio = 0.20f,
                yRatio = 0.88f,
                address = "Límite Chía - Cota, Cerro Majuy",
                distanceKm = 9.5f,
                operative = OperativeDetailsService(
                    tableServiceDynamics = "Visitas académicas y recorridos por el bosque nativo restaurado",
                    estimatedWaitMinutes = "Visitas con reserva previa",
                    businessHours = "Jueves y sábados con programación académica",
                    priceTier = "Gratis / Reserva",
                    ownerStory = "Histórica hacienda de jesuitas y clarisas construida en el siglo XVII, hoy laboratorio de restauración de 65 hectáreas de bosque nativo."
                )
            ),
            Place(
                id = "andres-chia",
                destinationId = "chia",
                municipality = "Chía",
                kind = "sabor",
                name = "Andrés Carne de Res Chía",
                tag = "Icono mundial de la gastronomía y rumba colombiana",
                emoji = "🥩",
                stars = 5,
                reviewCount = 2850,
                latitude = 4.8620,
                longitude = -74.0530,
                xRatio = 0.28f,
                yRatio = 0.82f,
                address = "Calle 3 # 11A-56, Chía",
                distanceKm = 7.8f,
                operative = OperativeDetailsService(
                    coffeeMethods = listOf("Espresso", "Café de la casa"),
                    coffeeOrigin = "Café colombiano premium",
                    baristaSpecialty = "Cócteles de autor y bebidas tradicionales",
                    tableServiceDynamics = "Servicio inmersivo teatral a la mesa con más de 20 ambientes",
                    estimatedWaitMinutes = "20 - 30 min fines de semana",
                    businessHours = "Mié - Dom: 12:00 m. - 3:00 a.m.",
                    priceTier = "$$$",
                    ownerStory = "El restaurante-bar más icónico de Colombia, fusionando arte popular, parrilla de alta calidad y fiesta tradicional."
                )
            )
        )
    }

    private fun initialDishes(): List<Dish> {
        return listOf(
            Dish(
                id = "helado-maracuya",
                destinationId = "cajica",
                name = "Helado Artesanal de Maracuyá & Frutos Rojos",
                category = "reposteria",
                placeId = "helados-san-jeronimo",
                placeName = "Helados y Postres San Jerónimo",
                description = "Elaborado con pulpa natural de maracuyá y mora silvestre, receta tradicional mantenida intacta desde 1957.",
                price = 8500,
                emoji = "🍨",
                isSignature = true,
                waitTimeMin = 3
            ),
            Dish(
                id = "milhoja-cajica",
                destinationId = "cajica",
                name = "Milhoja Sabanera con Arequipe Casero",
                category = "reposteria",
                placeId = "helados-san-jeronimo",
                placeName = "Helados y Postres San Jerónimo",
                description = "Hojaldre crocante de mantequilla relleno de crema pastelera fresca y abundante arequipe de paila de cobre.",
                price = 9000,
                emoji = "🍰",
                isSignature = true,
                waitTimeMin = 4
            ),
            Dish(
                id = "mamona-llanera",
                destinationId = "cajica",
                name = "Ternera a la Llanera (Mamona)",
                category = "parrilla",
                placeId = "fundo-llanero",
                placeName = "Restaurante Fundo Llanero Cajicá",
                description = "Cortes tiernos asados a fuego lento en vara de leña, acompañados de plátano maduro con queso y papa salada.",
                price = 39000,
                emoji = "🥩",
                isSignature = true,
                waitTimeMin = 15
            ),
            Dish(
                id = "mario-burger",
                destinationId = "cajica",
                name = "Mario Burger Gourmet con Papas Rústicas",
                category = "rapida",
                placeId = "level-up",
                placeName = "Level Up Gamer Bar & Restaurant",
                description = "Carne de res madurada 180g, tocineta crocante caramelizada, queso cheddar fundido y salsa de champiñones al vino.",
                price = 28000,
                emoji = "🍔",
                isSignature = true,
                waitTimeMin = 12
            ),
            Dish(
                id = "arepa-chocolo",
                destinationId = "cajica",
                name = "Arepa de Chócolo en Tiesto de Barro",
                category = "tradicional",
                placeId = "fonda-fogon",
                placeName = "Fonda El Fogón Sabanero",
                description = "Maíz tierno molido en casa, asada al carbón en tiesto de barro con queso campesino derretido y mantequilla de finca.",
                price = 12000,
                emoji = "🫓",
                isSignature = true,
                waitTimeMin = 8
            ),
            Dish(
                id = "caldo-costilla",
                destinationId = "cajica",
                name = "Caldo Criollo de Costilla",
                category = "tradicional",
                placeId = "fonda-fogon",
                placeName = "Fonda El Fogón Sabanero",
                description = "El clásico reconstituyente para las mañanas frías de la sabana, con papa pastusa seleccionada y cilantro fresco.",
                price = 15000,
                emoji = "🍲",
                isSignature = true,
                waitTimeMin = 10
            ),
            Dish(
                id = "cafe-v60",
                destinationId = "cajica",
                name = "Café Filtrado V60 Geisha Cundinamarca",
                category = "cafe",
                placeId = "cafe-central",
                placeName = "Café Central de Origen",
                description = "Grano cultivado a 1.950 msnm, extraído manualmente en mesa con notas a jazmín, miel de caña y frutos amarillos.",
                price = 9500,
                emoji = "☕",
                isSignature = true,
                waitTimeMin = 6
            ),
            Dish(
                id = "torta-mazorca",
                destinationId = "cajica",
                name = "Torta de Mazorca Tibia con Cuajada",
                category = "reposteria",
                placeId = "cafe-sabana",
                placeName = "Café Sabana & Panadería",
                description = "Receta campesina de maíz dulce con queso campesino tierno, servida caliente recién salida del horno.",
                price = 8500,
                emoji = "🥧",
                isSignature = false,
                waitTimeMin = 5
            )
        )
    }

    private fun initialEvents(): List<CulturalEvent> {
        return listOf(
            CulturalEvent(
                id = "e-busongote",
                destinationId = "cajica",
                day = 26,
                dayName = "Sáb",
                monthYear = "Septiembre 2026",
                title = "Festival de Amasijos y Dulces 'Busongote'",
                time = "10:00 a. m. - 6:00 p. m.",
                duration = "Jornada completa",
                place = "Parque Principal & Plazoleta Botero",
                isFree = true,
                emoji = "🫓",
                category = "Gastronomía",
                description = "El festival gastronómico cumbre de Cajicá, en honor al cacique Busongote. Concurso de almojábanas, mantecadas y cocineros tradicionales con jurados de talla nacional."
            ),
            CulturalEvent(
                id = "e-tunas",
                destinationId = "cajica",
                day = 27,
                dayName = "Dom",
                monthYear = "Septiembre 2026",
                title = "XXVII Festival Internacional de Tunas de Cajicá",
                time = "3:00 p. m. - 9:00 p. m.",
                duration = "6 horas",
                place = "Auditorio Centro Cultural Fernando Botero",
                isFree = true,
                emoji = "🎶",
                category = "Música",
                description = "Tradición ininterrumpida de música de capa y pandereta con tunas universitarias de Bogotá, Zipaquirá, Chía y delegación internacional invitada desde Lima, Perú."
            ),
            CulturalEvent(
                id = "e-retreta",
                destinationId = "cajica",
                day = 28,
                dayName = "Lun",
                monthYear = "Septiembre 2026",
                title = "Retreta Dominical: Banda Sinfónica Juvenil",
                time = "11:00 a. m.",
                duration = "1 h 30 min",
                place = "Kiosco del Parque Principal",
                isFree = true,
                emoji = "🎺",
                category = "Cultura",
                description = "Concierto abierto al aire libre por los jóvenes talentos formados en las escuelas de música del Centro Cultural."
            ),
            CulturalEvent(
                id = "e-periland",
                destinationId = "cajica",
                day = 29,
                dayName = "Mar",
                monthYear = "Septiembre 2026",
                title = "Jornada de Avistamiento y Siembra: Apadrina un Árbol",
                time = "8:30 a. m. - 1:00 p. m.",
                duration = "4 horas y media",
                place = "Periland Eco Park (Sector Las Manas)",
                isFree = false,
                emoji = "🌱",
                category = "Ecoturismo",
                description = "Siembra participativa de alisos y arrayanes en la cuenca alta del Río Bogotá de la mano de la Fundación Bosques Verdes."
            ),
            CulturalEvent(
                id = "e-mercado",
                destinationId = "cajica",
                day = 25,
                dayName = "Vie",
                monthYear = "Septiembre 2026",
                title = "Mercado Campesino Sostenible y Orgánico",
                time = "7:00 a. m. - 2:00 p. m.",
                duration = "7 horas",
                place = "Plaza de Mercado Municipal de Cajicá",
                isFree = true,
                emoji = "🥕",
                category = "Comunidad",
                description = "Comercio directo del agricultor a la mesa con hortalizas frescas, frutas de la sabana, miel y café tostado local."
            ),
            CulturalEvent(
                id = "e-montepincio",
                destinationId = "cajica",
                day = 24,
                dayName = "Jue",
                monthYear = "Septiembre 2026",
                title = "Caminata de Montaña al Mirador La Cumbre",
                time = "6:30 a. m.",
                duration = "3 horas",
                place = "Salida: Complejo Altos de Paito",
                isFree = false,
                emoji = "🥾",
                category = "Deporte",
                description = "Ascenso cardiovascular guiado de 11.6 km por los senderos de Montepincio con guías locales certificados y primeros auxilios."
            )
        )
    }

    private fun initialInterMunicipalRoutes(): List<InterMunicipalRoute> {
        return listOf(
            InterMunicipalRoute(
                id = "ruta-ferrea",
                title = "Ruta Férrea de la Sabana y RegioTram",
                subtitle = "Conectando la historia del tren del norte",
                theme = "Ferroviaria & Patrimonial",
                municipalities = listOf("Bogotá", "Chía", "Cajicá", "Zipaquirá"),
                totalDistanceKm = 49.5,
                estimatedDuration = "Jornada completa (1 día)",
                transportMode = "Tren Turistren / Futuro RegioTram",
                difficulty = "Fácil (Familiar)",
                keyStops = listOf(
                    "Estación Usaquén (Bogotá)",
                    "Puente del Común (Chía)",
                    "Estación del Ferrocarril de Cajicá (Km 28)",
                    "Catedral de Sal y Estación Zipaquirá"
                ),
                description = "Travesía nostálgica por el corredor férreo del norte donde operan las locomotoras de vapor del Turistren y se proyecta el tren eléctrico RegioTram del Norte hacia 2034.",
                highlightTip = "En Cajicá puedes descender en la Calle 1A para degustar amasijos y café antes de continuar hacia Zipaquirá.",
                emoji = "🚂"
            ),
            InterMunicipalRoute(
                id = "ruta-montana",
                title = "Circuito de Montaña: Cajicá a Tabio",
                subtitle = "De Montepincio a la mística Peña de Juaica",
                theme = "Montaña & Ecoturismo",
                municipalities = listOf("Cajicá", "Tabio"),
                totalDistanceKm = 24.8,
                estimatedDuration = "5 - 7 horas",
                transportMode = "Trekking / MTB",
                difficulty = "Moderada - Difícil",
                keyStops = listOf(
                    "Altos de Paito y Mirador La Cumbre (Cajicá)",
                    "Periland Eco Park (Las Manas)",
                    "Filo de la Serranía hacia Tabio",
                    "Termales El Zipa y Parque Principal de Tabio"
                ),
                description = "Recorrido de senderismo andino y miradores que cruza el cerro Montepincio hacia el valle de Tabio, finalizando en las aguas termales medicinales de El Zipa.",
                highlightTip = "Llevar ropa impermeable y registrarse en la caseta de guardabosques de Altos de Paito.",
                emoji = "⛰️"
            ),
            InterMunicipalRoute(
                id = "ruta-gastronomica",
                title = "Ruta de Sabores de Sabana Centro",
                subtitle = "Amasijos, carnes al carbón y café de altura",
                theme = "Gastronómica Sabanera",
                municipalities = listOf("Chía", "Cajicá", "Zipaquirá"),
                totalDistanceKm = 32.0,
                estimatedDuration = "Día completo",
                transportMode = "Vehículo / Bici recreativa",
                difficulty = "Fácil",
                keyStops = listOf(
                    "Piqueteaderos y Andrés Carne de Res (Chía)",
                    "Helados San Jerónimo y Café Central (Cajicá)",
                    "Fonda El Fogón y Amasijos de Busongote",
                    "Plaza de los Comuneros y Pan de Maíz (Zipaquirá)"
                ),
                description = "Circuito culinario impulsado por Asocentro y 'Sabana Centro se Enamora' que visibiliza la transición desde el maíz criollo hasta la alta cocina de parrilla y barismo.",
                highlightTip = "Prueba el método V60 en Café Central acompañado de una arepa de chócolo caliente.",
                emoji = "🍲"
            ),
            InterMunicipalRoute(
                id = "ruta-arqueologica",
                title = "Ruta Arqueológica y de Haciendas Coloniales",
                subtitle = "De la Fortaleza de Piedra a El Noviciado",
                theme = "Arqueológica Muisca",
                municipalities = listOf("Cota", "Chía", "Cajicá", "Facatativá"),
                totalDistanceKm = 54.0,
                estimatedDuration = "1 - 2 días",
                transportMode = "Vehículo / Rutas guiadas",
                difficulty = "Fácil",
                keyStops = listOf(
                    "Hacienda El Noviciado (Cota / Chía)",
                    "Ruinas de Busongote (Montepincio Cajicá)",
                    "Hallazgos Herrera en Hato Grande",
                    "Parque Arqueológico Piedras del Tunjo (Facatativá)"
                ),
                description = "Inmersión en más de 12.000 años de ocupación humana en la Sabana: pinturas rupestres, asentamientos prehispánicos de la cultura Herrera y haciendas jesuíticas del siglo XVII.",
                highlightTip = "Visita la sala arqueológica del Centro Cultural Fernando Botero en Cajicá.",
                emoji = "🏺"
            ),
            InterMunicipalRoute(
                id = "ruta-cicloturismo",
                title = "Circuito Cicloturístico Alto de las Margaritas y Canicas",
                subtitle = "El reto de los escaladores sabaneros",
                theme = "Cicloturismo STS",
                municipalities = listOf("Cajicá", "Subachoque", "Tabio", "Zipaquirá"),
                totalDistanceKm = 48.4,
                estimatedDuration = "3 - 5 horas",
                transportMode = "Bicicleta Ruta / MTB",
                difficulty = "Difícil (Desnivel +780m)",
                keyStops = listOf(
                    "Parque Principal de Cajicá (Salida)",
                    "Ascenso a La Valvanera",
                    "Paso por Subachoque y Alto de Canicas",
                    "Retorno por el valle del Río Frío"
                ),
                description = "Una de las rutas preferidas por el ciclismo colombiano de ruta y montaña, con tramos de placa huella y descensos panorámicos clasificados en escala Single Trail.",
                highlightTip = "Llevar kit de despinche e hidratación adecuada para las bajas temperaturas matutinas.",
                emoji = "🚴"
            )
        )
    }

    private fun initialRoutes(): List<CultureRoute> {
        return listOf(
            CultureRoute(
                id = "recorrido-dulce",
                destinationId = "cajica",
                title = "Recorrido Dulce Cajicá & Amasijos",
                subtitle = "Patrimonio gastronómico inmaterial de Busongote",
                durationText = "2 horas",
                distanceText = "1.6 km",
                difficulty = "Fácil (Urbano)",
                placesIncluded = listOf("Helados y Postres San Jerónimo", "Fonda El Fogón Sabanero", "Café Sabana & Panadería", "Café Central"),
                audioTitle = "Capítulo 1: El legado de los amasijos de maíz y el azúcar de caña",
                audioDurationSec = 165,
                audioTranscript = "Bienvenidos al Recorrido Dulce Cajicá. El aroma de las almojábanas calientes y el maíz tostado en tiestos de barro es el corazón de nuestra identidad de Ciudad Lenta Cittaslow..."
            ),
            CultureRoute(
                id = "recorrido-montepincio",
                destinationId = "cajica",
                title = "Sendero Patrimonial de Montepincio & Fortaleza",
                subtitle = "El enigma de Busongote y el Mirador La Cumbre",
                durationText = "3 h 30 min",
                distanceText = "11.6 km",
                difficulty = "Intermedia (4/5)",
                placesIncluded = listOf("Altos de Paito", "Ruinas de Busongote", "Mirador La Cumbre", "Periland Eco Park"),
                audioTitle = "Capítulo 2: La Fortaleza de Piedra y los antiguos resguardos indígenas",
                audioDurationSec = 215,
                audioTranscript = "Entre las piedras del cerro Montepincio y el sector de Las Manas reposan los muros de tierra pisada de Busongote, la fortaleza indígena que resistió la colonización en el siglo XVII..."
            )
        )
    }

    private fun initialReviews(): List<Review> {
        return listOf(
            Review(
                id = "r1",
                placeId = "helados-san-jeronimo",
                authorName = "Valentina Morales",
                rating = 5,
                date = "Hace 1 día",
                comment = "El helado de maracuyá y la milhoja son una parada obligatoria cada vez que venimos en el Turistren. El servicio familiar es inigualable.",
                serviceHighlight = "Atención cálida tradicional",
                coffeeRating = 5
            ),
            Review(
                id = "r2",
                placeId = "cafe-central",
                authorName = "Carlos Restrepo",
                rating = 5,
                date = "Hace 3 días",
                comment = "El método V60 preparado en mesa con grano Geisha de Cundinamarca fue una experiencia sensorial de primer nivel. Felicidades a los baristas.",
                serviceHighlight = "Barismo de precisión en mesa",
                coffeeRating = 5
            ),
            Review(
                id = "r3",
                placeId = "periland",
                authorName = "Luisa Fernanda Gómez",
                rating = 5,
                date = "Hace 5 días",
                comment = "Sembrar un aliso nativo con mis hijos en Periland fue lo más hermoso del fin de semana. El mirador El Rocinante tiene una vista espectacular.",
                serviceHighlight = "Guianza ecológica impecable",
                coffeeRating = 4
            ),
            Review(
                id = "r4",
                placeId = "level-up",
                authorName = "Felipe Arango",
                rating = 5,
                date = "Hace 1 semana",
                comment = "La Mario Burger está deliciosa y poder jugar en las consolas y bolirana con amigos hace de Level Up el lugar más divertido de Cajicá.",
                serviceHighlight = "Ambiente gamer y música genial",
                coffeeRating = 4
            )
        )
    }

    suspend fun syncWithRemoteBackend(): Result<String> {
        return runCatching {
            val remotePlaces = ApiClient.apiService.getPlaces()
            if (remotePlaces.isNotEmpty()) {
                _places.value = remotePlaces.map { it.toDomain() }
            }
            val remoteDishes = ApiClient.apiService.getDishes()
            if (remoteDishes.isNotEmpty()) {
                _dishes.value = remoteDishes.map { it.toDomain() }
            }
            val remoteEvents = ApiClient.apiService.getEvents()
            if (remoteEvents.isNotEmpty()) {
                _events.value = remoteEvents.map { it.toDomain() }
            }
            "Sincronizado exitosamente con el backend"
        }
    }
}

// Extension Mappers for Backend DTOs <-> Domain Models
fun PlaceDto.toDomain(): Place {
    return Place(
        id = this.id,
        destinationId = this.destinationId,
        municipality = this.municipality,
        kind = this.kind,
        name = this.name,
        tag = this.tag,
        emoji = this.emoji,
        stars = this.stars,
        reviewCount = this.reviewCount,
        latitude = this.latitude,
        longitude = this.longitude,
        xRatio = this.xRatio,
        yRatio = this.yRatio,
        address = this.address,
        distanceKm = this.distanceKm,
        operative = OperativeDetailsService(
            coffeeMethods = this.operative.coffeeMethods,
            coffeeOrigin = this.operative.coffeeOrigin,
            baristaSpecialty = this.operative.baristaSpecialty,
            tableServiceDynamics = this.operative.tableServiceDynamics,
            estimatedWaitMinutes = this.operative.estimatedWaitMinutes,
            businessHours = this.operative.businessHours,
            priceTier = this.operative.priceTier,
            paymentMethods = this.operative.paymentMethods,
            hasWifi = this.operative.hasWifi,
            isPetFriendly = this.operative.isPetFriendly,
            outdoorTerrace = this.operative.outdoorTerrace,
            ownerStory = this.operative.ownerStory
        )
    )
}

fun Place.toDto(): PlaceDto {
    return PlaceDto(
        id = this.id,
        destinationId = this.destinationId,
        municipality = this.municipality,
        kind = this.kind,
        name = this.name,
        tag = this.tag,
        emoji = this.emoji,
        stars = this.stars,
        reviewCount = this.reviewCount,
        latitude = this.latitude,
        longitude = this.longitude,
        xRatio = this.xRatio,
        yRatio = this.yRatio,
        address = this.address,
        distanceKm = this.distanceKm,
        operative = OperativeDetailsDto(
            coffeeMethods = this.operative.coffeeMethods,
            coffeeOrigin = this.operative.coffeeOrigin,
            baristaSpecialty = this.operative.baristaSpecialty,
            tableServiceDynamics = this.operative.tableServiceDynamics,
            estimatedWaitMinutes = this.operative.estimatedWaitMinutes,
            businessHours = this.operative.businessHours,
            priceTier = this.operative.priceTier,
            paymentMethods = this.operative.paymentMethods,
            hasWifi = this.operative.hasWifi,
            isPetFriendly = this.operative.isPetFriendly,
            outdoorTerrace = this.operative.outdoorTerrace,
            ownerStory = this.operative.ownerStory
        )
    )
}

fun DishDto.toDomain(): Dish {
    return Dish(
        id = this.id,
        destinationId = this.destinationId,
        name = this.name,
        category = this.category,
        placeId = this.placeId,
        placeName = this.placeName,
        description = this.description,
        price = this.price,
        emoji = this.emoji,
        isSignature = this.isSignature,
        waitTimeMin = this.waitTimeMin
    )
}

fun CulturalEventDto.toDomain(): CulturalEvent {
    return CulturalEvent(
        id = this.id,
        destinationId = this.destinationId,
        day = this.day,
        dayName = this.dayName,
        monthYear = this.monthYear,
        title = this.title,
        time = this.time,
        duration = this.duration,
        place = this.place,
        isFree = this.isFree,
        emoji = this.emoji,
        category = this.category,
        description = this.description
    )
}
