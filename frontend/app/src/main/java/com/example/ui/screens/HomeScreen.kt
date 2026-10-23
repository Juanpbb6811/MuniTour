package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddBusiness
import androidx.compose.material.icons.filled.AltRoute
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.isSystemInDarkTheme
import com.example.data.model.Destination
import com.example.data.model.InterMunicipalRoute
import com.example.data.model.Place
import com.example.data.model.UserProfile
import com.example.ui.components.BandBar
import com.example.ui.components.CustomSearchField
import com.example.ui.components.DestinationSelector
import com.example.ui.components.HeroBannerCard
import com.example.ui.components.StarsRating
import com.example.ui.theme.HtmlBgLight
import com.example.ui.theme.HtmlClay
import com.example.ui.theme.HtmlFgLight
import com.example.ui.theme.HtmlTeal
import com.example.ui.theme.ThemeTealBg

@Composable
fun HomeScreen(
    destination: Destination,
    destinations: List<Destination>,
    onSelectDestination: (Destination) -> Unit,
    searchQuery: String,
    onSearchChange: (String) -> Unit,
    onSearchSubmit: (String) -> Unit,
    onNavigateToCulturalMap: () -> Unit,
    onNavigateToGastronomy: () -> Unit,
    onNavigateToEvents: () -> Unit,
    onSelectPlace: (Place) -> Unit,
    onOpenAddBusiness: () -> Unit,
    onOpenProfile: () -> Unit,
    onOpenInterMunicipalRoutes: () -> Unit,
    interMunicipalRoutes: List<InterMunicipalRoute>,
    places: List<Place>,
    user: UserProfile,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .testTag("home_screen_lazy_column"),
        contentPadding = PaddingValues(bottom = 90.dp)
    ) {
        // Top Location Bar + Brand & Profile Avatar
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "MuniTour",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        DestinationSelector(
                            destinations = destinations,
                            selected = destination,
                            onSelect = onSelectDestination
                        )
                    }

                    // Profile / Login Button
                    Surface(
                        shape = CircleShape,
                        color = HtmlTeal.copy(alpha = 0.15f),
                        shadowElevation = 2.dp,
                        modifier = Modifier
                            .clickable { onOpenProfile() }
                            .testTag("home_profile_avatar_button")
                    ) {
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .clip(CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = user.avatarEmoji, fontSize = 22.sp)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Iconic 4-Color Band Bar from HTML
                BandBar(modifier = Modifier.fillMaxWidth(), height = 6.dp)

                Spacer(modifier = Modifier.height(12.dp))

                // Search field
                CustomSearchField(
                    query = searchQuery,
                    onQueryChange = onSearchChange,
                    placeholder = "Buscar en ${destination.name}: restaurantes, cafés, rutas...",
                    onSearchSubmit = { onSearchSubmit(searchQuery) }
                )
            }
        }

        // Hero Cards matching HTML styling
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                HeroBannerCard(
                    title = "Rutas Culturales",
                    subtitle = "Descubre lo local, cerro Montepincio y audioguías",
                    actionLabel = "Ver en Mapa",
                    emoji = "🏛️",
                    gradientColors = listOf(Color(0xFF0F6C77), Color(0xFF1B5A63)),
                    onClick = onNavigateToCulturalMap
                )

                HeroBannerCard(
                    title = "Gastronomía Municipal",
                    subtitle = "Helados tradicionales, café de altura y amasijos",
                    actionLabel = "Apoya Emprendedores",
                    emoji = "🍲",
                    gradientColors = listOf(Color(0xFFA2502B), Color(0xFFC4693F)),
                    onClick = onNavigateToGastronomy
                )
            }
        }

        // Section: Rutas Intermunicipales (entre varios municipios)
        item {
            Column(modifier = Modifier.fillMaxWidth().padding(top = 22.dp)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.AltRoute, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Rutas Entre Municipios",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }
                        Text(
                            text = "Circuitos turísticos que conectan la Sabana Centro",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    TextButton(onClick = onOpenInterMunicipalRoutes) {
                        Text("Ver todas", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                    }
                }

                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    items(interMunicipalRoutes) { route ->
                        Card(
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.25f)),
                            modifier = Modifier
                                .width(260.dp)
                                .clickable { onOpenInterMunicipalRoutes() }
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Surface(
                                        shape = RoundedCornerShape(6.dp),
                                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
                                    ) {
                                        Text(
                                            text = "${route.emoji} ${route.theme}",
                                            color = MaterialTheme.colorScheme.primary,
                                            style = MaterialTheme.typography.labelSmall,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                        )
                                    }

                                    Text(
                                        text = "${route.totalDistanceKm} km",
                                        fontWeight = FontWeight.Bold,
                                        style = MaterialTheme.typography.labelMedium,
                                        color = MaterialTheme.colorScheme.secondary
                                    )
                                }

                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = route.title,
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = route.municipalities.joinToString(" ➔ "),
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.primary,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = route.description,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    maxLines = 2
                                )
                            }
                        }
                    }
                }
            }
        }

        // Section: Restaurantes Reales y Comercios Locales
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 22.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Restaurantes & Cafés en Cajicá",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Text(
                            text = "Comercios reales de la sabana verificados en mapa",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    TextButton(onClick = onNavigateToGastronomy) {
                        Text("Ver menú", color = MaterialTheme.colorScheme.secondary, fontWeight = FontWeight.Bold)
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                places.filter { it.kind == "sabor" }.take(4).forEach { place ->
                    Card(
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.25f)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 5.dp)
                            .clickable { onSelectPlace(place) }
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(56.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(HtmlClay.copy(alpha = 0.15f)),
                                contentAlignment = Alignment.Center
                            ) {
                                if (!place.imageUrl.isNullOrBlank()) {
                                    AsyncImage(
                                        model = place.imageUrl,
                                        contentDescription = place.name,
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier.fillMaxSize()
                                    )
                                } else {
                                    Text(text = place.emoji, fontSize = 28.sp)
                                }
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(place.name, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleSmall)
                                Text(place.tag, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Spacer(modifier = Modifier.height(4.dp))
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    StarsRating(rating = place.stars, starSize = 12)
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("⏱️ ${place.operative.estimatedWaitMinutes}", style = MaterialTheme.typography.labelSmall, color = HtmlTeal)
                                }
                            }
                        }
                    }
                }
            }
        }

        // Autogestión Callout Banner for Local Owners
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = if (isSystemInDarkTheme()) Color(0xFF15404A) else ThemeTealBg),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 20.dp)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = "🌱 ¿Tienes un emprendimiento local en la Sabana?",
                        color = Color.White,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Publica gratis tu restaurante, métodos de café, tiempos de espera y menú en la plataforma MuniTour.",
                        color = Color.White.copy(alpha = 0.9f),
                        style = MaterialTheme.typography.bodySmall
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(
                        onClick = onOpenAddBusiness,
                        colors = ButtonDefaults.buttonColors(containerColor = HtmlClay),
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier.testTag("home_add_business_button")
                    ) {
                        Icon(Icons.Default.AddBusiness, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Registrar mi Negocio", color = Color.White, style = MaterialTheme.typography.labelMedium)
                    }
                }
            }
        }
    }
}
