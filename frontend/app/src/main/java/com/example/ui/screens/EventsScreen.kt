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
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Headphones
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import com.example.data.model.CulturalEvent
import com.example.data.model.CultureRoute
import com.example.data.model.Destination
import com.example.ui.theme.ThemeCherryActive
import com.example.ui.theme.ThemeCherryBg
import com.example.ui.theme.ThemeCherryHead

@Composable
fun EventsScreen(
    destination: Destination,
    events: List<CulturalEvent>,
    allEvents: List<CulturalEvent>,
    routes: List<CultureRoute>,
    selectedDay: Int,
    onSelectDay: (Int) -> Unit,
    favorites: Set<String>,
    onToggleFavorite: (String) -> Unit,
    reminders: Set<String>,
    onToggleReminder: (String) -> Unit,
    onInspectRoute: (CultureRoute) -> Unit,
    modifier: Modifier = Modifier
) {
    val days = listOf(
        Pair("Mié", 23),
        Pair("Jue", 24),
        Pair("Vie", 25),
        Pair("Sáb", 26),
        Pair("Dom", 27),
        Pair("Lun", 28),
        Pair("Mar", 29)
    )

    LazyColumn(
        contentPadding = PaddingValues(bottom = 90.dp),
        modifier = modifier
            .fillMaxSize()
            .background(ThemeCherryBg)
            .testTag("events_screen_lazy_column")
    ) {
        // Red Cultural Header (.ehead matching HTML)
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(ThemeCherryHead)
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Calendario cultural de ${destination.name}",
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Septiembre 2026",
                    color = Color.White.copy(alpha = 0.85f),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }

        // Horizontal Days Row (.days matching HTML)
        item {
            Surface(
                color = Color.White,
                shadowElevation = 1.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(days) { (dayName, dayNum) ->
                        val isSelected = selectedDay == dayNum
                        val hasEvents = allEvents.any { it.day == dayNum && (it.destinationId == destination.id || destination.id == "cajica") }

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .width(42.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (isSelected) ThemeCherryHead else Color(0xFFEEEEEE))
                                .clickable { onSelectDay(dayNum) }
                                .padding(vertical = 5.dp)
                                .testTag("day_picker_$dayNum")
                        ) {
                            Text(
                                text = dayName,
                                color = if (isSelected) Color.White else Color(0xFF444444),
                                style = MaterialTheme.typography.labelSmall
                            )
                            Text(
                                text = "$dayNum",
                                color = if (isSelected) Color.White else Color(0xFF111111),
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            if (hasEvents) {
                                Box(
                                    modifier = Modifier
                                        .size(4.dp)
                                        .clip(CircleShape)
                                        .background(if (isSelected) Color.White else ThemeCherryHead)
                                )
                            } else {
                                Spacer(modifier = Modifier.height(4.dp))
                            }
                        }
                    }
                }
            }
        }

        // Section Title: Eventos del día
        item {
            Text(
                text = "Eventos del Día ($selectedDay de Septiembre)",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1C1C1C),
                modifier = Modifier.padding(start = 14.dp, end = 14.dp, top = 14.dp, bottom = 4.dp)
            )
        }

        // Events List (.ev cards matching HTML)
        if (events.isEmpty()) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("📅", fontSize = 42.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "No hay eventos este día",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF222222)
                    )
                    Text(
                        text = "Elige un día con punto rojo para ver la agenda cultural y ferias de la sabana.",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF666666)
                    )
                }
            }
        } else {
            items(events, key = { it.id }) { event ->
                val isFav = favorites.contains("e:${event.id}")
                val isReminderActive = reminders.contains(event.id)

                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 5.dp)
                        .testTag("event_card_${event.id}")
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.Top
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(70.dp)
                                    .clip(RoundedCornerShape(9.dp))
                                    .background(Color(0xFFF3D6DA)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = event.emoji, fontSize = 32.sp)
                            }

                            Spacer(modifier = Modifier.width(10.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = event.title,
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = ThemeCherryHead
                                )
                                Spacer(modifier = Modifier.height(3.dp))
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.AccessTime,
                                        contentDescription = "Hora",
                                        modifier = Modifier.size(14.dp),
                                        tint = ThemeCherryHead
                                    )
                                    Spacer(modifier = Modifier.width(5.dp))
                                    Text(
                                        text = "${event.time}, ${event.duration}",
                                        style = MaterialTheme.typography.bodySmall,
                                        fontWeight = FontWeight.Medium,
                                        color = Color(0xFF1E293B)
                                    )
                                }
                                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 3.dp)) {
                                    Icon(
                                        imageVector = Icons.Default.LocationOn,
                                        contentDescription = "Lugar",
                                        modifier = Modifier.size(14.dp),
                                        tint = ThemeCherryHead
                                    )
                                    Spacer(modifier = Modifier.width(5.dp))
                                    Text(
                                        text = event.place,
                                        style = MaterialTheme.typography.bodySmall,
                                        fontWeight = FontWeight.Medium,
                                        color = Color(0xFF1E293B)
                                    )
                                }
                            }

                            IconButton(
                                onClick = { onToggleFavorite("e:${event.id}") },
                                modifier = Modifier.size(28.dp)
                            ) {
                                Icon(
                                    imageVector = if (isFav) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                    contentDescription = "Favorito",
                                    tint = if (isFav) ThemeCherryHead else ThemeCherryHead.copy(alpha = 0.5f),
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }

                        if (event.description.isNotBlank()) {
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = event.description,
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFF334155)
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Badge .badge2 (GRATIS vs Cupos Limitados)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            Surface(
                                shape = RoundedCornerShape(5.dp),
                                color = if (event.isFree) ThemeCherryHead else Color.White,
                                border = if (!event.isFree) androidx.compose.foundation.BorderStroke(1.5.dp, ThemeCherryHead) else null
                            ) {
                                Text(
                                    text = if (event.isFree) "GRATIS" else "Cupos limitados",
                                    color = if (event.isFree) Color.White else ThemeCherryHead,
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.ExtraBold,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Reminder button (.rem matching HTML)
                        Button(
                            onClick = { onToggleReminder(event.id) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isReminderActive) Color.White else ThemeCherryHead,
                                contentColor = if (isReminderActive) ThemeCherryHead else Color.White
                            ),
                            border = if (isReminderActive) androidx.compose.foundation.BorderStroke(2.dp, ThemeCherryHead) else null,
                            shape = RoundedCornerShape(20.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(40.dp)
                                .testTag("event_reminder_btn_${event.id}")
                        ) {
                            Text(
                                text = if (isReminderActive) "Recordatorio activo" else "Agendar recordatorio",
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp
                            )
                        }
                    }
                }
            }
        }

        // Section: Rutas Culturales con Audioguía
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 14.dp, end = 14.dp, top = 18.dp)
            ) {
                Text(
                    text = "Rutas Patrimoniales con Audioguía",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1C1C1C)
                )
                Text(
                    text = "Recorridos oficiales del portafolio turístico de Cajicá",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF666666)
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        items(routes) { route ->
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 5.dp)
                    .clickable { onInspectRoute(route) }
                    .testTag("route_card_${route.id}")
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .clip(CircleShape)
                            .background(ThemeCherryHead.copy(alpha = 0.12f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Headphones,
                            contentDescription = null,
                            tint = ThemeCherryHead,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(route.title, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleSmall, color = Color(0xFF1C1C1C))
                        Text(route.subtitle, style = MaterialTheme.typography.bodySmall, color = Color(0xFF555555))
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "⏱️ ${route.durationText} • ${route.distanceText}",
                            style = MaterialTheme.typography.labelSmall,
                            color = ThemeCherryHead,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    OutlinedButton(
                        onClick = { onInspectRoute(route) },
                        border = androidx.compose.foundation.BorderStroke(1.dp, ThemeCherryHead),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = ThemeCherryHead)
                    ) {
                        Text("Escuchar", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}
