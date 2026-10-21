package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AltRoute
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Navigation
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Destination
import com.example.data.model.Place
import com.example.ui.components.CustomSearchField
import com.example.ui.components.OsmMapView
import com.example.ui.components.StarsRating
import com.example.ui.theme.BandColor1
import com.example.ui.theme.HtmlBgDark
import com.example.ui.theme.HtmlClay
import com.example.ui.theme.HtmlLineDark
import com.example.ui.theme.HtmlTeal
import com.example.ui.theme.HtmlTealDark

@Composable
fun MapScreen(
    destination: Destination,
    places: List<Place>,
    selectedPlaceId: String,
    selectedPlace: Place?,
    onSelectPlaceId: (String) -> Unit,
    activeLayer: String,
    onSetLayer: (String) -> Unit,
    searchQuery: String,
    onSearchChange: (String) -> Unit,
    isFavorite: Boolean,
    onToggleFavorite: (String) -> Unit,
    onInspectPlace: (Place) -> Unit,
    onDirectionsClick: (Place) -> Unit,
    onOpenInterMunicipalRoutes: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(HtmlBgDark)
            .testTag("map_screen_root")
    ) {
        // 1. Native Interactive Map using OSMDroid
        OsmMapView(
            places = places,
            selectedPlaceId = selectedPlaceId,
            onSelectPlace = onSelectPlaceId,
            modifier = Modifier.fillMaxSize()
        )

        // 2. Top Controls Overlay: Search + Layer Chips (exact match to HTML .maptop & .chips)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 10.dp)
        ) {
            CustomSearchField(
                query = searchQuery,
                onQueryChange = onSearchChange,
                placeholder = "Buscar en el mapa de ${destination.name}...",
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                listOf(
                    Pair("todo", "Todo"),
                    Pair("cultura", "Cultura"),
                    Pair("sabor", "Sabores")
                ).forEach { (key, label) ->
                    val isSelected = activeLayer == key
                    FilterChip(
                        selected = isSelected,
                        onClick = { onSetLayer(key) },
                        label = { Text(label, style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = Color.White,
                            selectedLabelColor = HtmlBgDark,
                            containerColor = HtmlBgDark.copy(alpha = 0.88f),
                            labelColor = Color(0xFFDDFFFF)
                        ),
                        shape = RoundedCornerShape(20.dp)
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                // Rutas intermunicipales button on map
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = BandColor1,
                    onClick = onOpenInterMunicipalRoutes,
                    modifier = Modifier.testTag("map_inter_routes_button")
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.AltRoute, contentDescription = null, tint = Color.White, modifier = Modifier.size(15.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Rutas Sabana", color = Color.White, style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        // 3. Floating Bottom Place Card (.mcard matching HTML prototype)
        AnimatedVisibility(
            visible = selectedPlace != null,
            enter = slideInVertically(initialOffsetY = { it }),
            exit = slideOutVertically(targetOffsetY = { it }),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(horizontal = 10.dp, vertical = 10.dp)
        ) {
            selectedPlace?.let { place ->
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = HtmlBgDark),
                    border = androidx.compose.foundation.BorderStroke(1.dp, HtmlLineDark),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("map_place_floating_card")
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(62.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(if (place.kind == "sabor") HtmlClay else Color(0xFF1B5A63)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = place.emoji, fontSize = 30.sp)
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = place.name,
                                    color = Color.White,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                                if (place.kind == "sabor") {
                                    StarsRating(rating = place.stars, starSize = 13)
                                }
                                Text(
                                    text = place.tag,
                                    color = Color(0xFFB7D0D3),
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }

                            IconButton(
                                onClick = { onToggleFavorite("p:${place.id}") },
                                modifier = Modifier.testTag("map_card_favorite_button")
                            ) {
                                Icon(
                                    imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                    contentDescription = "Favorito",
                                    tint = if (isFavorite) Color(0xFFFF6B7C) else Color(0xFF8FDDE5)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        // Action Buttons (.mbtns matching HTML)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Button(
                                onClick = { onInspectPlace(place) },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E7A85)),
                                shape = RoundedCornerShape(20.dp),
                                modifier = Modifier.weight(1f).testTag("map_card_details_button")
                            ) {
                                Text(
                                    text = if (place.kind == "sabor") "Ver Menú" else "Ver Ruta",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp
                                )
                            }

                            OutlinedButton(
                                onClick = { onDirectionsClick(place) },
                                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFF8FDDE5)),
                                border = androidx.compose.foundation.BorderStroke(1.5.dp, Color(0xFF1E7A85)),
                                shape = RoundedCornerShape(20.dp),
                                modifier = Modifier.weight(1f).testTag("map_card_directions_button")
                            ) {
                                Icon(Icons.Default.Navigation, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Cómo llegar", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}
