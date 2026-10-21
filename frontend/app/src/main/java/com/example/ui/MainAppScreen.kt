package com.example.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.RestaurantMenu
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material.icons.outlined.RestaurantMenu
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.components.AudioGuideDialog
import com.example.ui.components.AuthDialog
import com.example.ui.components.BusinessRegistrationDialog
import com.example.ui.components.DirectionsDialog
import com.example.ui.components.InterMunicipalRoutesDialog
import com.example.ui.components.OperativeDetailsSheet
import com.example.ui.components.ProfileDialog
import com.example.ui.components.ReviewDialog
import com.example.ui.components.ToastNotification
import com.example.ui.screens.EventsScreen
import com.example.ui.screens.FavoritesScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.MapScreen
import com.example.ui.screens.SaboresScreen
import com.example.ui.theme.ThemeCherryActive
import com.example.ui.theme.ThemeCherryTabbar
import com.example.ui.theme.ThemeClayActive
import com.example.ui.theme.ThemeClayTabbar
import com.example.ui.theme.ThemeTealActive
import com.example.ui.theme.ThemeTealTabbar
import com.example.ui.viewmodel.AppTab
import com.example.ui.viewmodel.MuniTourViewModel
import kotlinx.coroutines.delay

@Composable
fun MainAppScreen(
    viewModel: MuniTourViewModel = viewModel()
) {
    val currentTab by viewModel.currentTab.collectAsState()
    val selectedDestination by viewModel.selectedDestination.collectAsState()
    val homeSearchQuery by viewModel.homeSearchQuery.collectAsState()
    val saboresSearchQuery by viewModel.saboresSearchQuery.collectAsState()
    val selectedDishCategory by viewModel.selectedDishCategory.collectAsState()
    val filterPlaceId by viewModel.filterPlaceId.collectAsState()
    val mapLayer by viewModel.mapLayer.collectAsState()
    val mapSearchQuery by viewModel.mapSearchQuery.collectAsState()
    val selectedPlaceId by viewModel.selectedPlaceId.collectAsState()
    val selectedDay by viewModel.selectedDay.collectAsState()

    val filteredPlaces by viewModel.filteredPlaces.collectAsState()
    val filteredDishes by viewModel.filteredDishes.collectAsState()
    val currentDayEvents by viewModel.currentDayEvents.collectAsState()
    val selectedPlace by viewModel.selectedPlace.collectAsState()

    val allPlaces by viewModel.allPlaces.collectAsState()
    val allDishes by viewModel.allDishes.collectAsState()
    val allEvents by viewModel.allEvents.collectAsState()
    val allRoutes by viewModel.allRoutes.collectAsState()
    val allReviews by viewModel.allReviews.collectAsState()
    val favorites by viewModel.favorites.collectAsState()
    val reminders by viewModel.reminders.collectAsState()
    val interMunicipalRoutes by viewModel.interMunicipalRoutes.collectAsState()
    val currentUser by viewModel.currentUser.collectAsState()

    // Dialog states
    val inspectingPlace by viewModel.inspectingPlace.collectAsState()
    val inspectingRoute by viewModel.inspectingRoute.collectAsState()
    val isPlayingAudio by viewModel.isPlayingAudio.collectAsState()
    val audioProgressSec by viewModel.audioProgressSec.collectAsState()
    val directionsPlace by viewModel.directionsPlace.collectAsState()
    val reviewingPlace by viewModel.reviewingPlace.collectAsState()
    val showAddBusiness by viewModel.showAddBusinessDialog.collectAsState()
    val showAuthDialog by viewModel.showAuthDialog.collectAsState()
    val showProfileDialog by viewModel.showProfileDialog.collectAsState()
    val showInterMunicipalDialog by viewModel.showInterMunicipalDialog.collectAsState()
    val toast by viewModel.toast.collectAsState()

    // Auto-dismiss toast
    LaunchedEffect(toast) {
        if (toast != null) {
            delay(2400)
            viewModel.clearToast()
        }
    }

    // Dynamic NavigationBar Colors strictly matching HTML .tabbar themes
    val (navContainerColor, navContentColor, navIndicatorColor) = when (currentTab) {
        AppTab.SABORES -> Triple(ThemeClayTabbar, ThemeClayActive, ThemeClayActive.copy(alpha = 0.25f))
        AppTab.EVENTOS -> Triple(ThemeCherryTabbar, ThemeCherryActive, ThemeCherryActive.copy(alpha = 0.15f))
        else -> Triple(ThemeTealTabbar, ThemeTealActive, ThemeTealActive.copy(alpha = 0.25f))
    }

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = navContainerColor,
                contentColor = navContentColor,
                tonalElevation = 8.dp,
                modifier = Modifier
                    .windowInsetsPadding(WindowInsets.navigationBars)
                    .testTag("main_bottom_nav_bar")
            ) {
                // Tab 1: Inicio
                NavigationBarItem(
                    selected = currentTab == AppTab.INICIO,
                    onClick = { viewModel.setTab(AppTab.INICIO) },
                    icon = {
                        Icon(
                            imageVector = if (currentTab == AppTab.INICIO) Icons.Default.Home else Icons.Outlined.Home,
                            contentDescription = "Inicio"
                        )
                    },
                    label = { Text("Inicio") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White,
                        selectedTextColor = navContentColor,
                        unselectedIconColor = Color.White.copy(alpha = 0.7f),
                        unselectedTextColor = Color.White.copy(alpha = 0.7f),
                        indicatorColor = navIndicatorColor
                    ),
                    modifier = Modifier.testTag("nav_tab_inicio")
                )

                // Tab 2: Mapa
                NavigationBarItem(
                    selected = currentTab == AppTab.MAPA,
                    onClick = { viewModel.setTab(AppTab.MAPA) },
                    icon = {
                        Icon(
                            imageVector = if (currentTab == AppTab.MAPA) Icons.Default.Map else Icons.Outlined.Map,
                            contentDescription = "Mapa"
                        )
                    },
                    label = { Text("Mapa") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White,
                        selectedTextColor = navContentColor,
                        unselectedIconColor = Color.White.copy(alpha = 0.7f),
                        unselectedTextColor = Color.White.copy(alpha = 0.7f),
                        indicatorColor = navIndicatorColor
                    ),
                    modifier = Modifier.testTag("nav_tab_mapa")
                )

                // Tab 3: Sabores
                NavigationBarItem(
                    selected = currentTab == AppTab.SABORES,
                    onClick = { viewModel.setTab(AppTab.SABORES) },
                    icon = {
                        Icon(
                            imageVector = if (currentTab == AppTab.SABORES) Icons.Default.RestaurantMenu else Icons.Outlined.RestaurantMenu,
                            contentDescription = "Sabores"
                        )
                    },
                    label = { Text("Sabores") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White,
                        selectedTextColor = navContentColor,
                        unselectedIconColor = Color.White.copy(alpha = 0.7f),
                        unselectedTextColor = Color.White.copy(alpha = 0.7f),
                        indicatorColor = navIndicatorColor
                    ),
                    modifier = Modifier.testTag("nav_tab_sabores")
                )

                // Tab 4: Eventos
                NavigationBarItem(
                    selected = currentTab == AppTab.EVENTOS,
                    onClick = { viewModel.setTab(AppTab.EVENTOS) },
                    icon = {
                        BadgedBox(
                            badge = {
                                if (reminders.isNotEmpty()) {
                                    Badge(containerColor = Color(0xFFC8102E)) {
                                        Text(reminders.size.toString())
                                    }
                                }
                            }
                        ) {
                            Icon(
                                imageVector = if (currentTab == AppTab.EVENTOS) Icons.Default.CalendarMonth else Icons.Outlined.CalendarMonth,
                                contentDescription = "Eventos"
                            )
                        }
                    },
                    label = { Text("Eventos") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White,
                        selectedTextColor = navContentColor,
                        unselectedIconColor = Color.White.copy(alpha = 0.7f),
                        unselectedTextColor = Color.White.copy(alpha = 0.7f),
                        indicatorColor = navIndicatorColor
                    ),
                    modifier = Modifier.testTag("nav_tab_eventos")
                )

                // Tab 5: Favoritos
                NavigationBarItem(
                    selected = currentTab == AppTab.FAVORITOS,
                    onClick = { viewModel.setTab(AppTab.FAVORITOS) },
                    icon = {
                        Icon(
                            imageVector = if (currentTab == AppTab.FAVORITOS) Icons.Default.Favorite else Icons.Outlined.FavoriteBorder,
                            contentDescription = "Favoritos"
                        )
                    },
                    label = { Text("Favoritos") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White,
                        selectedTextColor = navContentColor,
                        unselectedIconColor = Color.White.copy(alpha = 0.7f),
                        unselectedTextColor = Color.White.copy(alpha = 0.7f),
                        indicatorColor = navIndicatorColor
                    ),
                    modifier = Modifier.testTag("nav_tab_favoritos")
                )
            }
        },
        contentWindowInsets = WindowInsets.safeDrawing,
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (currentTab) {
                AppTab.INICIO -> {
                    HomeScreen(
                        destination = selectedDestination,
                        destinations = viewModel.destinations,
                        onSelectDestination = { viewModel.selectDestination(it) },
                        searchQuery = homeSearchQuery,
                        onSearchChange = { viewModel.setHomeSearch(it) },
                        onSearchSubmit = { viewModel.submitHomeSearch(it) },
                        onNavigateToCulturalMap = {
                            viewModel.setMapLayer("cultura")
                            viewModel.setTab(AppTab.MAPA)
                        },
                        onNavigateToGastronomy = {
                            viewModel.setFilterPlaceId(null)
                            viewModel.setTab(AppTab.SABORES)
                        },
                        onNavigateToEvents = { viewModel.setTab(AppTab.EVENTOS) },
                        onSelectPlace = { viewModel.inspectPlace(it) },
                        onOpenAddBusiness = { viewModel.openAddBusinessDialog() },
                        onOpenProfile = { viewModel.openProfileDialog() },
                        onOpenInterMunicipalRoutes = { viewModel.openInterMunicipalDialog() },
                        interMunicipalRoutes = interMunicipalRoutes,
                        places = filteredPlaces,
                        user = currentUser
                    )
                }

                AppTab.MAPA -> {
                    MapScreen(
                        destination = selectedDestination,
                        places = filteredPlaces,
                        selectedPlaceId = selectedPlaceId,
                        selectedPlace = selectedPlace,
                        onSelectPlaceId = { viewModel.selectPlace(it) },
                        activeLayer = mapLayer,
                        onSetLayer = { viewModel.setMapLayer(it) },
                        searchQuery = mapSearchQuery,
                        onSearchChange = { viewModel.setMapSearch(it) },
                        isFavorite = selectedPlace?.let { favorites.contains("p:${it.id}") } == true,
                        onToggleFavorite = { viewModel.toggleFavorite(it) },
                        onInspectPlace = { viewModel.inspectPlace(it) },
                        onDirectionsClick = { viewModel.showDirections(it) },
                        onOpenInterMunicipalRoutes = { viewModel.openInterMunicipalDialog() }
                    )
                }

                AppTab.SABORES -> {
                    SaboresScreen(
                        destination = selectedDestination,
                        dishes = filteredDishes,
                        places = allPlaces,
                        selectedCategory = selectedDishCategory,
                        onSelectCategory = { viewModel.selectDishCategory(it) },
                        searchQuery = saboresSearchQuery,
                        onSearchChange = { viewModel.setSaboresSearch(it) },
                        filterPlaceId = filterPlaceId,
                        onClearPlaceFilter = { viewModel.setFilterPlaceId(null) },
                        favorites = favorites,
                        onToggleFavorite = { viewModel.toggleFavorite(it) },
                        onInspectPlace = { viewModel.inspectPlace(it) },
                        onOpenAddBusiness = { viewModel.openAddBusinessDialog() }
                    )
                }

                AppTab.EVENTOS -> {
                    EventsScreen(
                        destination = selectedDestination,
                        events = currentDayEvents,
                        allEvents = allEvents,
                        routes = allRoutes,
                        selectedDay = selectedDay,
                        onSelectDay = { viewModel.selectDay(it) },
                        favorites = favorites,
                        onToggleFavorite = { viewModel.toggleFavorite(it) },
                        reminders = reminders,
                        onToggleReminder = { viewModel.toggleReminder(it) },
                        onInspectRoute = { viewModel.inspectRoute(it) }
                    )
                }

                AppTab.FAVORITOS -> {
                    FavoritesScreen(
                        favorites = favorites,
                        allPlaces = allPlaces,
                        allDishes = allDishes,
                        allEvents = allEvents,
                        allReviews = allReviews,
                        onToggleFavorite = { viewModel.toggleFavorite(it) },
                        onInspectPlace = { viewModel.inspectPlace(it) }
                    )
                }
            }

            // Toast Floating Notification
            ToastNotification(
                message = toast?.message,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 70.dp)
            )
        }
    }

    // Modal Sheet: Detalle Operativo del Servicio
    inspectingPlace?.let { place ->
        val placeReviews = allReviews.filter { it.placeId == place.id }
        OperativeDetailsSheet(
            place = place,
            reviews = placeReviews,
            isFavorite = favorites.contains("p:${place.id}"),
            onToggleFavorite = { viewModel.toggleFavorite("p:${place.id}") },
            onDismiss = { viewModel.closeInspectPlace() },
            onDirectionsClick = {
                viewModel.closeInspectPlace()
                viewModel.showDirections(place)
            },
            onViewMenuClick = {
                viewModel.closeInspectPlace()
                if (place.kind == "sabor") {
                    viewModel.navigateToPlaceMenu(place.id)
                } else {
                    viewModel.navigateToCultureRoute()
                }
            },
            onAddReviewClick = {
                viewModel.startReview(place)
            }
        )
    }

    // Dialog: Audioguía Inmersiva
    inspectingRoute?.let { route ->
        AudioGuideDialog(
            route = route,
            isPlaying = isPlayingAudio,
            progressSec = audioProgressSec,
            onTogglePlay = { viewModel.toggleAudio() },
            onDismiss = { viewModel.closeInspectRoute() }
        )
    }

    // Dialog: Cómo llegar / Navegación
    directionsPlace?.let { place ->
        DirectionsDialog(
            place = place,
            onDismiss = { viewModel.closeDirections() },
            onStartNavigation = {
                viewModel.closeDirections()
                viewModel.showToast("Iniciando guía GPS hacia ${place.name}")
            }
        )
    }

    // Dialog: Reseña y Calificación de Servicio
    reviewingPlace?.let { place ->
        ReviewDialog(
            place = place,
            onDismiss = { viewModel.closeReview() },
            onSubmit = { author, rating, comment, serviceHighlight ->
                viewModel.submitReview(place.id, author, rating, comment, serviceHighlight)
            }
        )
    }

    // Dialog: Autogestión de Emprendimiento
    if (showAddBusiness) {
        BusinessRegistrationDialog(
            onDismiss = { viewModel.closeAddBusinessDialog() },
            onRegister = { name, kind, tag, address, coffeeMethods, coffeeOrigin, baristaSpecialty, tableServiceDynamics, waitTime, hours, priceTier, ownerStory ->
                viewModel.registerBusiness(
                    name,
                    kind,
                    tag,
                    address,
                    coffeeMethods,
                    coffeeOrigin,
                    baristaSpecialty,
                    tableServiceDynamics,
                    waitTime,
                    hours,
                    priceTier,
                    ownerStory
                )
            }
        )
    }

    // Dialog: Rutas Intermunicipales
    if (showInterMunicipalDialog) {
        InterMunicipalRoutesDialog(
            routes = interMunicipalRoutes,
            onDismiss = { viewModel.closeInterMunicipalDialog() },
            onSelectRouteOnMap = { route ->
                viewModel.selectInterMunicipalRoute(route)
            }
        )
    }

    // Dialog: Perfil del Viajero
    if (showProfileDialog) {
        ProfileDialog(
            user = currentUser,
            onDismiss = { viewModel.closeProfileDialog() },
            onSaveProfile = { name, originCity, avatar, coffee ->
                viewModel.updateProfile(name, originCity, avatar, coffee)
            },
            onLogout = { viewModel.logoutUser() },
            onOpenAuth = {
                viewModel.closeProfileDialog()
                viewModel.openAuthDialog()
            }
        )
    }

    // Dialog: Inicio de Sesión / Registro
    if (showAuthDialog) {
        AuthDialog(
            onDismiss = { viewModel.closeAuthDialog() },
            onLoginSuccess = { email, name ->
                viewModel.loginUser(email, name)
            },
            onContinueAsGuest = {
                viewModel.closeAuthDialog()
                viewModel.showToast("Modo explorador activado")
            }
        )
    }
}
