package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Navigation
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Dish
import com.example.data.model.Place
import com.example.data.model.Review
import com.example.ui.theme.BrandClay
import com.example.ui.theme.BrandTealDark
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.foundation.isSystemInDarkTheme
import com.example.ui.theme.HtmlClayDark
import com.example.ui.theme.ThemeClaySheet
import com.example.ui.theme.BrandTealPrimary
import com.example.ui.theme.StarGold

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun OperativeDetailsSheet(
    place: Place,
    reviews: List<Review>,
    dishes: List<Dish> = emptyList(),
    isFavorite: Boolean,
    onToggleFavorite: () -> Unit,
    onDismiss: () -> Unit,
    onDirectionsClick: () -> Unit,
    onViewMenuClick: () -> Unit,
    onAddReviewClick: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    
    val isDark = isSystemInDarkTheme()
    val isSabor = place.kind == "sabor"
    
    val containerColor = if (isSabor) {
        if (isDark) Color(0xFF2A1A10) else ThemeClaySheet
    } else {
        MaterialTheme.colorScheme.surface
    }
    
    val onSurfaceColor = if (isSabor) {
        if (isDark) Color(0xFFFFE2B8) else Color(0xFF2A1A10)
    } else {
        MaterialTheme.colorScheme.onSurface
    }
    
    val onSurfaceVariantColor = if (isSabor) {
        if (isDark) Color(0xFFE58F63) else Color(0xFF6B5645)
    } else {
        MaterialTheme.colorScheme.onSurfaceVariant
    }

    val primaryColor = if (isSabor) {
        if (isDark) HtmlClayDark else BrandClay
    } else {
        MaterialTheme.colorScheme.primary
    }
    
    val surfaceVariantColor = if (isSabor) {
        if (isDark) Color(0xFF3E2723) else Color.White
    } else {
        MaterialTheme.colorScheme.surfaceVariant
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        containerColor = containerColor,
        modifier = Modifier.testTag("operative_details_bottom_sheet")
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Restaurant / Venue Hero Photo Banner
            if (!place.imageUrl.isNullOrBlank()) {
                AsyncImage(
                    model = place.imageUrl,
                    contentDescription = place.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .clip(RoundedCornerShape(16.dp))
                )
                Spacer(modifier = Modifier.height(14.dp))
            }

            // Header with badge, title and close
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(54.dp)
                            .clip(RoundedCornerShape(14.dp))
                            .background(if (place.kind == "sabor") BrandClay.copy(alpha = 0.2f) else BrandTealPrimary.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = place.emoji, fontSize = 28.sp)
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = place.name,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = onSurfaceColor
                        )
                        Text(
                            text = place.tag,
                            style = MaterialTheme.typography.bodyMedium,
                            color = onSurfaceVariantColor
                        )
                    }
                }

                IconButton(
                    onClick = onToggleFavorite,
                    modifier = Modifier.testTag("operative_sheet_favorite_button")
                ) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Favorito",
                        tint = if (isFavorite) Color(0xFFD1352B) else onSurfaceVariantColor
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Operational highlights pill row (Wait time, Table Service, Hours)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = if (isSabor) (if (isDark) Color(0xFF3E2723) else Color.White) else MaterialTheme.colorScheme.primaryContainer,
                    modifier = Modifier.weight(1f)
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Schedule,
                                contentDescription = null,
                                tint = primaryColor,
                                modifier = Modifier.size(15.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Espera est.",
                                style = MaterialTheme.typography.labelSmall,
                                color = primaryColor
                            )
                        }
                        Text(
                            text = place.operative.estimatedWaitMinutes,
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Bold,
                            color = onSurfaceColor
                        )
                    }
                }

                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = if (isSabor) (if (isDark) Color(0xFF3E2723) else Color.White) else MaterialTheme.colorScheme.secondaryContainer,
                    modifier = Modifier.weight(1f)
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = null,
                                tint = StarGold,
                                modifier = Modifier.size(15.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Reputación",
                                style = MaterialTheme.typography.labelSmall,
                                color = if (isSabor) onSurfaceVariantColor else MaterialTheme.colorScheme.onSecondaryContainer
                            )
                        }
                        Text(
                            text = "${place.stars}.0 (${place.reviewCount} reseñas)",
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Bold,
                            color = onSurfaceColor
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Divider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
            Spacer(modifier = Modifier.height(14.dp))

            // Section 1: Coffee Brewing Methods & Barista Specialties (for eateries)
            if (place.operative.coffeeMethods.isNotEmpty()) {
                Text(
                    text = "☕ Métodos de Preparación de Café",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = onSurfaceColor
                )
                Spacer(modifier = Modifier.height(6.dp))
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    place.operative.coffeeMethods.forEach { method ->
                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = BrandClay.copy(alpha = 0.12f),
                            border = androidx.compose.foundation.BorderStroke(1.dp, BrandClay.copy(alpha = 0.35f))
                        ) {
                            Text(
                                text = method,
                                style = MaterialTheme.typography.labelMedium,
                                color = BrandClay,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))
                if (place.operative.coffeeOrigin.isNotBlank()) {
                    Text(
                        text = "Origen del grano: ${place.operative.coffeeOrigin}",
                        style = MaterialTheme.typography.bodySmall,
                        color = onSurfaceVariantColor
                    )
                }
                if (place.operative.baristaSpecialty.isNotBlank()) {
                    Text(
                        text = "Especialidad: ${place.operative.baristaSpecialty}",
                        style = MaterialTheme.typography.bodySmall,
                        color = onSurfaceVariantColor
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))
            }

            // Section 2: Table Service Dynamics & Operation
            Text(
                text = "🍽️ Dinámica del Servicio a la Mesa",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = onSurfaceColor
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = place.operative.tableServiceDynamics,
                style = MaterialTheme.typography.bodyMedium,
                color = onSurfaceColor
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Horarios & Ubicación
            Text(
                text = "🕒 Horario y Atención",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = onSurfaceColor
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = place.operative.businessHours,
                style = MaterialTheme.typography.bodyMedium,
                color = onSurfaceVariantColor
            )
            if (place.address.isNotBlank()) {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 4.dp)) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = primaryColor,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = place.address,
                        style = MaterialTheme.typography.bodySmall,
                        color = onSurfaceVariantColor
                    )
                }
            }

            // Story of the local business
            if (place.operative.ownerStory.isNotBlank()) {
                Spacer(modifier = Modifier.height(12.dp))
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = surfaceVariantColor)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            text = "🌱 Historia del Emprendimiento",
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Bold,
                            color = onSurfaceColor
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = place.operative.ownerStory,
                            style = MaterialTheme.typography.bodySmall,
                            color = onSurfaceColor
                        )
                    }
                }
            }

            // In-Sheet Culinary Menu & Specialties
            if (isSabor && dishes.isNotEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "🍽️ Menú & Especialidades",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = onSurfaceColor
                        )
                        Text(
                            text = "${dishes.size} platos recomendados de la casa",
                            style = MaterialTheme.typography.labelSmall,
                            color = onSurfaceVariantColor
                        )
                    }

                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = primaryColor.copy(alpha = 0.15f),
                        onClick = onViewMenuClick
                    ) {
                        Text(
                            text = "Explorar ➔",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = primaryColor,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                dishes.forEach { dish ->
                    Card(
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = surfaceVariantColor),
                        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.12f)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(46.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(primaryColor.copy(alpha = 0.15f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(dish.emoji, fontSize = 22.sp)
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = dish.name,
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = onSurfaceColor,
                                        modifier = Modifier.weight(1f, fill = false)
                                    )
                                    Text(
                                        text = formatMoney(dish.price),
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = primaryColor
                                    )
                                }
                                if (dish.description.isNotBlank()) {
                                    Spacer(modifier = Modifier.height(3.dp))
                                    Text(
                                        text = dish.description,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = onSurfaceVariantColor,
                                        maxLines = 2
                                    )
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "⏱️ ${dish.waitTimeMin} min",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = onSurfaceVariantColor
                                    )
                                    if (dish.isSignature) {
                                        Surface(
                                            shape = RoundedCornerShape(6.dp),
                                            color = StarGold.copy(alpha = 0.2f)
                                        ) {
                                            Text(
                                                text = "⭐ Especialidad",
                                                style = MaterialTheme.typography.labelSmall,
                                                fontWeight = FontWeight.Bold,
                                                color = if (isDark) StarGold else Color(0xFFB7791F),
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

            Spacer(modifier = Modifier.height(16.dp))

            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Button(
                    onClick = onViewMenuClick,
                    modifier = Modifier.weight(1f).testTag("operative_sheet_menu_button"),
                    colors = ButtonDefaults.buttonColors(containerColor = primaryColor)
                ) {
                    Text(if (place.kind == "sabor") "Ver Menú Completo" else "Ver Ruta Cultural", color = Color.White)
                }

                OutlinedButton(
                    onClick = onDirectionsClick,
                    modifier = Modifier.weight(1f).testTag("operative_sheet_directions_button"),
                    border = androidx.compose.foundation.BorderStroke(1.dp, primaryColor),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = primaryColor)
                ) {
                    Icon(imageVector = Icons.Default.Navigation, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Cómo llegar")
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Community Feedback & Reviews
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Reseñas Comunitarias (${reviews.size})",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = onSurfaceColor
                )
                Button(
                    onClick = onAddReviewClick,
                    colors = ButtonDefaults.buttonColors(containerColor = primaryColor),
                    modifier = Modifier.testTag("operative_sheet_add_review_button")
                ) {
                    Text("Calificar", style = MaterialTheme.typography.labelMedium, color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            reviews.take(3).forEach { review ->
                Card(
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(containerColor = surfaceVariantColor),
                    border = if (!isSabor) androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)) else null,
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(review.authorName, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyMedium, color = onSurfaceColor)
                            StarsRating(rating = review.rating)
                        }
                        Text(
                            text = review.serviceHighlight,
                            style = MaterialTheme.typography.labelSmall,
                            color = primaryColor
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(review.comment, style = MaterialTheme.typography.bodySmall, color = onSurfaceColor)
                    }
                }
            }

            Spacer(modifier = Modifier.height(28.dp))
        }
    }
}
