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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Destination
import com.example.data.model.Dish
import com.example.data.model.Place
import com.example.ui.components.CustomSearchField
import com.example.ui.components.formatMoney
import com.example.ui.theme.HtmlClay
import com.example.ui.theme.ThemeClayActive
import com.example.ui.theme.ThemeClayDishPrice
import com.example.ui.theme.ThemeClayGradientBottom
import com.example.ui.theme.ThemeClayGradientTop
import com.example.ui.theme.ThemeClaySheet

@Composable
fun SaboresScreen(
    destination: Destination,
    dishes: List<Dish>,
    places: List<Place>,
    selectedCategory: String?,
    onSelectCategory: (String?) -> Unit,
    searchQuery: String,
    onSearchChange: (String) -> Unit,
    filterPlaceId: String?,
    onClearPlaceFilter: () -> Unit,
    favorites: Set<String>,
    onToggleFavorite: (String) -> Unit,
    onInspectPlace: (Place) -> Unit,
    onOpenAddBusiness: () -> Unit,
    modifier: Modifier = Modifier
) {
    val categories = listOf(
        Triple("tradicional", "Tradicional", "🫓"),
        Triple("cafe", "Café", "☕"),
        Triple("parrilla", "Parrilla", "🍖"),
        Triple("rapida", "Rápida", "🥟"),
        Triple("reposteria", "Repostería", "🍰")
    )

    val activePlace = places.firstOrNull { it.id == filterPlaceId }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onOpenAddBusiness,
                containerColor = HtmlClay,
                contentColor = Color.White,
                shape = CircleShape,
                modifier = Modifier
                    .padding(bottom = 60.dp)
                    .testTag("sabores_add_business_fab")
            ) {
                Row(modifier = Modifier.padding(horizontal = 14.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Add, contentDescription = "Publicar negocio")
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Autogestión", style = MaterialTheme.typography.labelLarge)
                }
            }
        },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        LazyColumn(
            contentPadding = PaddingValues(
                top = innerPadding.calculateTopPadding(),
                bottom = innerPadding.calculateBottomPadding() + 90.dp
            ),
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(ThemeClayGradientTop, ThemeClayGradientBottom)
                    )
                )
                .testTag("sabores_screen_lazy_column")
        ) {
            // HTML Topbar & Categories
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = Color.Black.copy(alpha = 0.22f)
                        ) {
                            Text(
                                text = "${destination.name} ▾",
                                color = Color.White,
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 14.dp, vertical = 5.dp)
                            )
                        }

                        Text(
                            text = "Sabores Locales",
                            color = ThemeClayActive,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Filtros",
                        color = Color.White,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    // Circular category items (.cat matching HTML)
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(categories) { (key, label, emoji) ->
                            val isSelected = selectedCategory == key
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .clickable { onSelectCategory(if (isSelected) null else key) }
                                    .testTag("category_chip_$key")
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(52.dp)
                                        .clip(CircleShape)
                                        .background(if (isSelected) Color(0xFFFFF1D6) else Color(0x8C542612)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(text = emoji, fontSize = 24.sp)
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = label,
                                    color = if (isSelected) ThemeClayActive else Color.White,
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                )
                            }
                        }
                    }
                }
            }

            // HTML Sheet container (.sheet with background #FBF3E4)
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                        .background(ThemeClaySheet)
                        .padding(horizontal = 14.dp, vertical = 14.dp)
                ) {
                    // Small grab handle like in HTML
                    Box(
                        modifier = Modifier
                            .size(44.dp, 4.dp)
                            .clip(RoundedCornerShape(2.dp))
                            .background(Color(0xFFD9C8AD))
                            .align(Alignment.CenterHorizontally)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    CustomSearchField(
                        query = searchQuery,
                        onQueryChange = onSearchChange,
                        placeholder = "Buscar plato, café o restaurante...",
                        modifier = Modifier.fillMaxWidth()
                    )

                    if (activePlace != null) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = Color(0xFF2A1A10),
                            modifier = Modifier.clickable { onClearPlaceFilter() }
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = "Menú de: ${activePlace.name}",
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFFFFE2B8)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Icon(
                                    imageVector = Icons.Default.Clear,
                                    contentDescription = "Limpiar filtro",
                                    tint = Color(0xFFFFE2B8),
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                }
            }

            // Dishes Cards (.dish in HTML with #fff card background)
            if (dishes.isEmpty()) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(ThemeClaySheet)
                            .padding(32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "🍽️", fontSize = 42.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "No hay platos con este filtro",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF2A1A10)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Quita un filtro o cambia la búsqueda.",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF6B5645)
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        OutlinedButton(onClick = {
                            onSelectCategory(null)
                            onClearPlaceFilter()
                            onSearchChange("")
                        }) {
                            Text("Limpiar filtros", color = Color(0xFFA85B37))
                        }
                    }
                }
            } else {
                items(dishes, key = { it.id }) { dish ->
                    val isFav = favorites.contains("d:${dish.id}")
                    val parentPlace = places.firstOrNull { it.id == dish.placeId }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(ThemeClaySheet)
                            .padding(horizontal = 14.dp, vertical = 5.dp)
                    ) {
                        Card(
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    parentPlace?.let { onInspectPlace(it) }
                                }
                                .testTag("dish_card_${dish.id}")
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(66.dp)
                                        .clip(RoundedCornerShape(9.dp))
                                        .background(Color(0xFFF2E2C7)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(text = dish.emoji, fontSize = 32.sp)
                                }

                                Spacer(modifier = Modifier.width(10.dp))

                                Column(modifier = Modifier.weight(1f)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.Top
                                    ) {
                                        Text(
                                            text = dish.name,
                                            style = MaterialTheme.typography.titleSmall,
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFF2A1A10)
                                        )

                                        IconButton(
                                            onClick = { onToggleFavorite("d:${dish.id}") },
                                            modifier = Modifier.size(26.dp)
                                        ) {
                                            Icon(
                                                imageVector = if (isFav) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                                contentDescription = "Favorito",
                                                tint = if (isFav) Color(0xFFD1352B) else Color(0xFFB8A48E),
                                                modifier = Modifier.size(20.dp)
                                            )
                                        }
                                    }

                                    Text(
                                        text = dish.placeName,
                                        style = MaterialTheme.typography.labelSmall,
                                        color = Color(0xFF9B5B36),
                                        fontWeight = FontWeight.SemiBold
                                    )

                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = dish.description,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = Color(0xFF5B4636),
                                        maxLines = 2
                                    )

                                    Spacer(modifier = Modifier.height(4.dp))
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = formatMoney(dish.price, destination.currencySymbol),
                                            style = MaterialTheme.typography.titleMedium,
                                            fontWeight = FontWeight.ExtraBold,
                                            color = ThemeClayDishPrice
                                        )

                                        if (dish.isSignature) {
                                            Surface(
                                                shape = RoundedCornerShape(4.dp),
                                                color = Color(0xFFFFF1D6)
                                            ) {
                                                Text(
                                                    text = "★ Especialidad",
                                                    style = MaterialTheme.typography.labelSmall,
                                                    color = Color(0xFF9B5B36),
                                                    fontWeight = FontWeight.Bold,
                                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
