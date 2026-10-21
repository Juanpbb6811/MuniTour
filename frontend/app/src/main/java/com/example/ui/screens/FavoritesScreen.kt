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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.CulturalEvent
import com.example.data.model.Dish
import com.example.data.model.Place
import com.example.data.model.Review
import com.example.ui.components.StarsRating
import com.example.ui.components.formatMoney
import com.example.ui.theme.HtmlBgDark
import com.example.ui.theme.HtmlTeal
import com.example.ui.theme.HtmlTealDark
import com.example.ui.theme.ThemeTealTabbar

@Composable
fun FavoritesScreen(
    favorites: Set<String>,
    allPlaces: List<Place>,
    allDishes: List<Dish>,
    allEvents: List<CulturalEvent>,
    allReviews: List<Review>,
    onToggleFavorite: (String) -> Unit,
    onInspectPlace: (Place) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedSection by remember { mutableIntStateOf(0) }
    val sectionTitles = listOf("Tus Favoritos (${favorites.size})", "Comunidad & Reseñas")

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(HtmlBgDark)
            .testTag("favorites_screen_root")
    ) {
        // Tab Row with HTML styling
        TabRow(
            selectedTabIndex = selectedSection,
            containerColor = ThemeTealTabbar,
            contentColor = Color.White,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[selectedSection]),
                    color = HtmlTealDark
                )
            }
        ) {
            sectionTitles.forEachIndexed { index, title ->
                Tab(
                    selected = selectedSection == index,
                    onClick = { selectedSection = index },
                    text = {
                        Text(
                            text = title,
                            fontWeight = FontWeight.Bold,
                            color = if (selectedSection == index) HtmlTealDark else Color.White.copy(alpha = 0.7f)
                        )
                    }
                )
            }
        }

        if (selectedSection == 0) {
            // Favoritos List (.fav matching HTML)
            if (favorites.isEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(40.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("❤️", fontSize = 48.sp)
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Aún no tienes favoritos",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Toca el corazón en un plato, un lugar o un evento para guardarlo aquí.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFFB7D0D3)
                    )
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(favorites.toList()) { key ->
                        val prefix = key.take(2)
                        val id = key.drop(2)

                        when (prefix) {
                            "p:" -> {
                                val place = allPlaces.firstOrNull { it.id == id }
                                if (place != null) {
                                    FavoriteItemCard(
                                        title = place.name,
                                        subtitle = if (place.kind == "sabor") "Lugar para comer" else "Ruta cultural",
                                        emoji = place.emoji,
                                        onRemove = { onToggleFavorite(key) },
                                        onClick = { onInspectPlace(place) }
                                    )
                                }
                            }
                            "d:" -> {
                                val dish = allDishes.firstOrNull { it.id == id }
                                if (dish != null) {
                                    FavoriteItemCard(
                                        title = dish.name,
                                        subtitle = "Plato, ${dish.placeName} (${formatMoney(dish.price)})",
                                        emoji = dish.emoji,
                                        onRemove = { onToggleFavorite(key) },
                                        onClick = {
                                            allPlaces.firstOrNull { it.id == dish.placeId }?.let { onInspectPlace(it) }
                                        }
                                    )
                                }
                            }
                            "e:" -> {
                                val event = allEvents.firstOrNull { it.id == id }
                                if (event != null) {
                                    FavoriteItemCard(
                                        title = event.title,
                                        subtitle = "Evento, ${event.dayName} ${event.day}, ${event.time}",
                                        emoji = event.emoji,
                                        onRemove = { onToggleFavorite(key) },
                                        onClick = {}
                                    )
                                }
                            }
                        }
                    }
                }
            }
        } else {
            // Comunidad y Reseñas
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                item {
                    Text(
                        text = "Experiencias de Viajeros en Cajicá",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "Reseñas reales verificadas sobre atención, café y gastronomía.",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFFA9C0C2)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                }

                items(allReviews) { review ->
                    val place = allPlaces.firstOrNull { it.id == review.placeId }

                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = ThemeTealTabbar),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF25525B)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(review.authorName, fontWeight = FontWeight.Bold, color = Color.White, style = MaterialTheme.typography.titleSmall)
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("(${review.date})", style = MaterialTheme.typography.labelSmall, color = Color(0xFFA9C0C2))
                                }
                                StarsRating(rating = review.rating)
                            }

                            if (place != null) {
                                Text(
                                    text = "En: ${place.name}",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = HtmlTealDark,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }

                            Spacer(modifier = Modifier.height(6.dp))
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = Color(0xFF15404A)
                            ) {
                                Text(
                                    text = "💡 ${review.serviceHighlight}",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = HtmlTealDark,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                )
                            }

                            Spacer(modifier = Modifier.height(6.dp))
                            Text(review.comment, color = Color(0xFFF4EBDD), style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun FavoriteItemCard(
    title: String,
    subtitle: String,
    emoji: String,
    onRemove: () -> Unit,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = ThemeTealTabbar),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF25525B)),
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFF1B5A63)),
                contentAlignment = Alignment.Center
            ) {
                Text(text = emoji, fontSize = 22.sp)
            }

            Spacer(modifier = Modifier.width(10.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(title, fontWeight = FontWeight.Bold, color = Color.White, style = MaterialTheme.typography.titleSmall)
                Text(subtitle, style = MaterialTheme.typography.bodySmall, color = Color(0xFF9FC1C5))
            }

            IconButton(onClick = onRemove) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Quitar de favoritos",
                    tint = Color(0xFFFF6B7C)
                )
            }
        }
    }
}
