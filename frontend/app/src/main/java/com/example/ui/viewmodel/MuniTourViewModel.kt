package com.example.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.CulturalEvent
import com.example.data.model.CultureRoute
import com.example.data.model.Destination
import com.example.data.model.Dish
import com.example.data.model.InterMunicipalRoute
import com.example.data.model.OperativeDetailsService
import com.example.data.model.Place
import com.example.data.model.Review
import com.example.data.model.UserProfile
import com.example.data.repository.MuniTourRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

enum class AppTab(val title: String) {
    INICIO("Inicio"),
    MAPA("Mapa"),
    SABORES("Sabores"),
    EVENTOS("Eventos"),
    FAVORITOS("Favoritos")
}

data class UiToast(
    val message: String,
    val timestamp: Long = System.currentTimeMillis()
)

class MuniTourViewModel(
    private val repository: MuniTourRepository = MuniTourRepository()
) : ViewModel() {

    val destinations = repository.destinations

    val currentUser = repository.currentUser

    private val _selectedDestination = MutableStateFlow(destinations.first())
    val selectedDestination: StateFlow<Destination> = _selectedDestination.asStateFlow()

    private val _currentTab = MutableStateFlow(AppTab.INICIO)
    val currentTab: StateFlow<AppTab> = _currentTab.asStateFlow()

    // Search & Filters
    private val _homeSearchQuery = MutableStateFlow("")
    val homeSearchQuery: StateFlow<String> = _homeSearchQuery.asStateFlow()

    private val _saboresSearchQuery = MutableStateFlow("")
    val saboresSearchQuery: StateFlow<String> = _saboresSearchQuery.asStateFlow()

    private val _selectedDishCategory = MutableStateFlow<String?>(null)
    val selectedDishCategory: StateFlow<String?> = _selectedDishCategory.asStateFlow()

    private val _filterPlaceId = MutableStateFlow<String?>(null)
    val filterPlaceId: StateFlow<String?> = _filterPlaceId.asStateFlow()

    // Map State
    private val _mapLayer = MutableStateFlow("todo") // "todo", "cultura", "sabor"
    val mapLayer: StateFlow<String> = _mapLayer.asStateFlow()

    private val _mapSearchQuery = MutableStateFlow("")
    val mapSearchQuery: StateFlow<String> = _mapSearchQuery.asStateFlow()

    private val _selectedPlaceId = MutableStateFlow("cafe-central")
    val selectedPlaceId: StateFlow<String> = _selectedPlaceId.asStateFlow()

    // Events State
    private val _selectedDay = MutableStateFlow(java.util.Calendar.getInstance().get(java.util.Calendar.DAY_OF_MONTH))
    val selectedDay: StateFlow<Int> = _selectedDay.asStateFlow()

    // Interactive Dialogs
    private val _inspectingPlace = MutableStateFlow<Place?>(null)
    val inspectingPlace: StateFlow<Place?> = _inspectingPlace.asStateFlow()

    private val _inspectingRoute = MutableStateFlow<CultureRoute?>(null)
    val inspectingRoute: StateFlow<CultureRoute?> = _inspectingRoute.asStateFlow()

    private val _isPlayingAudio = MutableStateFlow(false)
    val isPlayingAudio: StateFlow<Boolean> = _isPlayingAudio.asStateFlow()

    private val _audioProgressSec = MutableStateFlow(24)
    val audioProgressSec: StateFlow<Int> = _audioProgressSec.asStateFlow()

    private val _directionsPlace = MutableStateFlow<Place?>(null)
    val directionsPlace: StateFlow<Place?> = _directionsPlace.asStateFlow()

    private val _reviewingPlace = MutableStateFlow<Place?>(null)
    val reviewingPlace: StateFlow<Place?> = _reviewingPlace.asStateFlow()

    private val _showAddBusinessDialog = MutableStateFlow(false)
    val showAddBusinessDialog: StateFlow<Boolean> = _showAddBusinessDialog.asStateFlow()

    // Auth & Profile dialogs
    private val _showAuthDialog = MutableStateFlow(false)
    val showAuthDialog: StateFlow<Boolean> = _showAuthDialog.asStateFlow()

    private val _isAuthLoading = MutableStateFlow(false)
    val isAuthLoading: StateFlow<Boolean> = _isAuthLoading.asStateFlow()

    private val _authError = MutableStateFlow<String?>(null)
    val authError: StateFlow<String?> = _authError.asStateFlow()

    private val _showProfileDialog = MutableStateFlow(false)
    val showProfileDialog: StateFlow<Boolean> = _showProfileDialog.asStateFlow()

    private val _showInterMunicipalDialog = MutableStateFlow(false)
    val showInterMunicipalDialog: StateFlow<Boolean> = _showInterMunicipalDialog.asStateFlow()

    private val _toast = MutableStateFlow<UiToast?>(null)
    val toast: StateFlow<UiToast?> = _toast.asStateFlow()

    val favorites = repository.favorites
    val reminders = repository.reminders
    val allPlaces = repository.places
    val allDishes = repository.dishes
    val allEvents = repository.events
    val allRoutes = repository.routes
    val allReviews = repository.reviews
    val interMunicipalRoutes = repository.interMunicipalRoutes

    private var audioJob: Job? = null

    // Filtered lists
    val filteredPlaces: StateFlow<List<Place>> = combine(
        allPlaces,
        selectedDestination,
        mapLayer,
        mapSearchQuery
    ) { places, dest, layer, query ->
        places.filter { place ->
            (place.destinationId == dest.id || dest.id == "cajica") &&
            (layer == "todo" || place.kind == layer) &&
            (query.isBlank() || place.name.contains(query, ignoreCase = true) || place.tag.contains(query, ignoreCase = true))
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val filteredDishes: StateFlow<List<Dish>> = combine(
        allDishes,
        selectedDestination,
        selectedDishCategory,
        filterPlaceId,
        saboresSearchQuery
    ) { dishes, dest, cat, placeId, query ->
        val candidateDishes = if (placeId != null) {
            val matching = dishes.filter { it.placeId == placeId }
            if (matching.isNotEmpty()) {
                matching
            } else {
                val targetPlace = allPlaces.value.firstOrNull { it.id == placeId }
                if (targetPlace != null) repository.getDishesForPlace(targetPlace) else emptyList()
            }
        } else {
            dishes
        }
        candidateDishes.filter { dish ->
            (dish.destinationId == dest.id || dest.id == "cajica") &&
            (cat == null || dish.category == cat) &&
            (query.isBlank() || dish.name.contains(query, ignoreCase = true) || dish.description.contains(query, ignoreCase = true) || dish.placeName.contains(query, ignoreCase = true))
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val currentDayEvents: StateFlow<List<CulturalEvent>> = combine(
        allEvents,
        selectedDestination,
        selectedDay
    ) { events, dest, day ->
        events.filter { (it.destinationId == dest.id || dest.id == "cajica") && it.day == day }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val selectedPlace: StateFlow<Place?> = combine(
        allPlaces,
        selectedPlaceId
    ) { places, id ->
        places.firstOrNull { it.id == id } ?: places.firstOrNull()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    fun setTab(tab: AppTab) {
        _currentTab.value = tab
    }

    fun selectDestination(dest: Destination) {
        _selectedDestination.value = dest
        showToast("Explorando ${dest.name}, ${dest.country} ${dest.flag}")
    }

    fun setHomeSearch(query: String) {
        _homeSearchQuery.value = query
    }

    fun submitHomeSearch(query: String) {
        _saboresSearchQuery.value = query
        _currentTab.value = AppTab.SABORES
    }

    fun setSaboresSearch(query: String) {
        _saboresSearchQuery.value = query
    }

    fun selectDishCategory(cat: String?) {
        _selectedDishCategory.value = if (_selectedDishCategory.value == cat) null else cat
    }

    fun setFilterPlaceId(placeId: String?) {
        _filterPlaceId.value = placeId
    }

    fun setMapLayer(layer: String) {
        _mapLayer.value = layer
    }

    fun setMapSearch(query: String) {
        _mapSearchQuery.value = query
    }

    fun selectPlace(id: String) {
        _selectedPlaceId.value = id
    }

    fun selectDay(day: Int) {
        _selectedDay.value = day
    }

    fun inspectPlace(place: Place) {
        _inspectingPlace.value = place
    }

    fun closeInspectPlace() {
        _inspectingPlace.value = null
    }

    fun inspectRoute(route: CultureRoute) {
        _inspectingRoute.value = route
        _isPlayingAudio.value = false
        _audioProgressSec.value = 0
    }

    fun closeInspectRoute() {
        _inspectingRoute.value = null
        stopAudio()
    }

    fun toggleAudio() {
        val willPlay = !_isPlayingAudio.value
        _isPlayingAudio.value = willPlay
        if (willPlay) {
            audioJob?.cancel()
            audioJob = viewModelScope.launch {
                val max = _inspectingRoute.value?.audioDurationSec ?: 180
                while (_isPlayingAudio.value && _audioProgressSec.value < max) {
                    delay(1000)
                    _audioProgressSec.update { (it + 1).coerceAtMost(max) }
                }
                if (_audioProgressSec.value >= max) {
                    _isPlayingAudio.value = false
                }
            }
            showToast("Reproduciendo audioguía patrimonial...")
        } else {
            audioJob?.cancel()
        }
    }

    private fun stopAudio() {
        _isPlayingAudio.value = false
        audioJob?.cancel()
    }

    fun showDirections(place: Place) {
        _directionsPlace.value = place
    }

    fun closeDirections() {
        _directionsPlace.value = null
    }

    fun startReview(place: Place) {
        _reviewingPlace.value = place
    }

    fun closeReview() {
        _reviewingPlace.value = null
    }

    fun submitReview(placeId: String, author: String, rating: Int, comment: String, serviceHighlight: String) {
        val review = Review(
            id = "r_${System.currentTimeMillis()}",
            placeId = placeId,
            authorName = author.ifBlank { "Viajero Gourmet" },
            rating = rating,
            date = "Hoy",
            comment = comment,
            serviceHighlight = serviceHighlight
        )
        repository.addReview(review)
        closeReview()
        showToast("¡Gracias por tu reseña! Aporte verificado a la comunidad.")
    }

    fun openAddBusinessDialog() {
        _showAddBusinessDialog.value = true
    }

    fun closeAddBusinessDialog() {
        _showAddBusinessDialog.value = false
    }

    fun registerBusiness(
        name: String,
        kind: String,
        tag: String,
        address: String,
        coffeeMethods: List<String>,
        coffeeOrigin: String,
        baristaSpecialty: String,
        tableServiceDynamics: String,
        waitTime: String,
        hours: String,
        priceTier: String,
        ownerStory: String
    ) {
        val newPlace = Place(
            id = "biz_${System.currentTimeMillis()}",
            destinationId = _selectedDestination.value.id,
            municipality = _selectedDestination.value.name,
            kind = kind,
            name = name,
            tag = tag,
            emoji = if (kind == "sabor") "☕" else "🏛️",
            stars = 5,
            reviewCount = 1,
            latitude = 4.9186 + (Math.random() - 0.5) * 0.015,
            longitude = -74.0259 + (Math.random() - 0.5) * 0.015,
            xRatio = (0.25f + Math.random().toFloat() * 0.5f),
            yRatio = (0.35f + Math.random().toFloat() * 0.4f),
            address = address,
            distanceKm = 0.4f,
            operative = OperativeDetailsService(
                coffeeMethods = coffeeMethods,
                coffeeOrigin = coffeeOrigin,
                baristaSpecialty = baristaSpecialty,
                tableServiceDynamics = tableServiceDynamics,
                estimatedWaitMinutes = waitTime,
                businessHours = hours,
                priceTier = priceTier,
                ownerStory = ownerStory
            )
        )
        repository.addPlace(newPlace)
        closeAddBusinessDialog()
        _selectedPlaceId.value = newPlace.id
        showToast("¡Negocio registrado con éxito en MuniTour!")
    }

    // Auth & Profile methods
    fun openAuthDialog() {
        _showAuthDialog.value = true
    }

    fun closeAuthDialog() {
        _showAuthDialog.value = false
        _authError.value = null
    }

    fun clearAuthError() {
        _authError.value = null
    }

    fun loginWithEmail(email: String, pass: String) {
        viewModelScope.launch {
            _isAuthLoading.value = true
            _authError.value = null
            val result = repository.loginWithEmail(email, pass)
            _isAuthLoading.value = false
            result.onSuccess { user ->
                closeAuthDialog()
                showToast("¡Bienvenido, ${user.name}! Sesión iniciada con éxito.")
            }.onFailure { err ->
                _authError.value = err.message ?: "Error al iniciar sesión. Verifica tus credenciales."
            }
        }
    }

    fun registerWithEmail(name: String, email: String, pass: String) {
        viewModelScope.launch {
            _isAuthLoading.value = true
            _authError.value = null
            val result = repository.registerWithEmail(name, email, pass)
            _isAuthLoading.value = false
            result.onSuccess { user ->
                closeAuthDialog()
                showToast("¡Cuenta creada exitosamente! Bienvenido, ${user.name}.")
            }.onFailure { err ->
                _authError.value = err.message ?: "Error al registrar la cuenta. Intenta de nuevo."
            }
        }
    }

    fun loginWithGoogle(idTokenOrEmail: String, fallbackName: String? = null) {
        viewModelScope.launch {
            _isAuthLoading.value = true
            _authError.value = null
            val result = repository.loginWithGoogle(idTokenOrEmail, fallbackName)
            _isAuthLoading.value = false
            result.onSuccess { user ->
                closeAuthDialog()
                showToast("¡Autenticación con Google exitosa! Hola, ${user.name}.")
            }.onFailure { err ->
                _authError.value = err.message ?: "Error en autenticación con Google."
            }
        }
    }

    fun loginUser(email: String, name: String) {
        repository.login(email, name)
        closeAuthDialog()
        showToast("¡Bienvenido a MuniTour, $name! Sesión iniciada.")
    }

    fun logoutUser() {
        repository.logout()
        closeProfileDialog()
        showToast("Sesión cerrada. Modo explorador.")
    }

    fun openProfileDialog() {
        _showProfileDialog.value = true
    }

    fun closeProfileDialog() {
        _showProfileDialog.value = false
    }

    fun updateProfile(name: String, originCity: String, avatar: String, coffee: String) {
        repository.updateProfile(name, originCity, avatar, coffee)
        showToast("Perfil de viajero actualizado.")
    }

    fun saveFullProfile(
        name: String,
        originCity: String,
        avatar: String,
        coffee: String,
        phone: String,
        bio: String,
        preferredTransport: String
    ) {
        viewModelScope.launch {
            val result = repository.saveFullProfile(
                name = name,
                originCity = originCity,
                avatarEmoji = avatar,
                favoriteCoffee = coffee,
                phone = phone,
                bio = bio,
                preferredTransport = preferredTransport
            )
            result.onSuccess {
                showToast("Perfil de viajero guardado y sincronizado con el servidor.")
            }.onFailure {
                showToast("Perfil guardado localmente (modo sin conexión).")
            }
        }
    }

    fun syncData() {
        viewModelScope.launch {
            showToast("Sincronizando información con el servidor...")
            val result = repository.syncWithRemoteBackend()
            result.onSuccess { msg ->
                showToast(msg)
            }.onFailure {
                showToast("No se pudo conectar al servidor. Usando datos locales.")
            }
        }
    }

    fun importOsmPlaces(municipality: String = "Cajicá") {
        viewModelScope.launch {
            showToast("Consultando restaurantes de $municipality en OpenStreetMap...")
            val result = repository.importOsmPlaces(municipality)
            result.onSuccess { msg ->
                showToast(msg)
            }.onFailure { err ->
                showToast("Aviso al consultar OSM: ${err.message}")
            }
        }
    }

    // Inter-municipal dialogs
    fun openInterMunicipalDialog() {
        _showInterMunicipalDialog.value = true
    }

    fun closeInterMunicipalDialog() {
        _showInterMunicipalDialog.value = false
    }

    fun selectInterMunicipalRoute(route: InterMunicipalRoute) {
        closeInterMunicipalDialog()
        _mapLayer.value = "todo"
        _currentTab.value = AppTab.MAPA
        showToast("Cargando ${route.title} en el mapa...")
    }

    fun toggleFavorite(key: String) {
        val added = repository.toggleFavorite(key)
        showToast(if (added) "Guardado en tus favoritos ❤️" else "Eliminado de tus favoritos")
    }

    fun toggleReminder(eventId: String) {
        val active = repository.toggleReminder(eventId)
        showToast(if (active) "Recordatorio programado (1 hora antes) 🔔" else "Recordatorio cancelado")
    }

    fun getDishesForPlace(place: Place): List<Dish> {
        return repository.getDishesForPlace(place)
    }

    fun navigateToPlaceMenu(placeId: String) {
        _filterPlaceId.value = placeId
        _selectedDishCategory.value = null
        _saboresSearchQuery.value = ""
        _currentTab.value = AppTab.SABORES
    }

    fun navigateToCultureRoute() {
        val route = allRoutes.value.firstOrNull()
        if (route != null) {
            inspectRoute(route)
        } else {
            showToast("Recorrido cultural guardado en tu plan")
        }
    }

    fun showToast(message: String) {
        _toast.value = UiToast(message)
    }

    fun clearToast() {
        _toast.value = null
    }
}
