package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CloudSync
import androidx.compose.material.icons.filled.Coffee
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
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
import com.example.data.model.UserProfile
import com.example.ui.theme.HtmlClay
import com.example.ui.theme.HtmlTeal

@Composable
fun ProfileDialog(
    user: UserProfile,
    onDismiss: () -> Unit,
    onSaveProfile: (
        name: String,
        originCity: String,
        avatar: String,
        coffee: String,
        phone: String,
        bio: String,
        preferredTransport: String
    ) -> Unit,
    onLogout: () -> Unit,
    onOpenAuth: () -> Unit,
    onSyncWithBackend: () -> Unit = {}
) {
    var isEditing by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf(user.name) }
    var originCity by remember { mutableStateOf(user.originCity) }
    var avatarEmoji by remember { mutableStateOf(user.avatarEmoji) }
    var favoriteCoffee by remember { mutableStateOf(user.favoriteCoffeeMethod) }
    var phone by remember { mutableStateOf(user.phone) }
    var bio by remember { mutableStateOf(user.bio) }
    var preferredTransport by remember { mutableStateOf(user.preferredTransport) }

    val avatars = listOf("🧭", "☕", "🥾", "🚴", "🍽️", "🎨", "🌿", "🏛️")
    val transportOptions = listOf("Tren / RegioTram", "Bicicleta MTB", "Caminata / Senderismo", "Vehículo Particular", "Transporte Público")

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Perfil del Viajero",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                if (user.isLoggedIn) {
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = HtmlTeal.copy(alpha = 0.15f)
                    ) {
                        Text(
                            text = "Sesión Activa",
                            color = HtmlTeal,
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
            }
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // Avatar & User Overview Header
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(68.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primaryContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = avatarEmoji, fontSize = 36.sp)
                    }

                    Spacer(modifier = Modifier.width(14.dp))

                    Column {
                        Text(
                            text = if (isEditing) "Editando perfil" else user.name,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = user.email,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = HtmlClay.copy(alpha = 0.15f),
                            modifier = Modifier.padding(top = 4.dp)
                        ) {
                            Text(
                                text = "🏆 ${user.travelerLevel}",
                                style = MaterialTheme.typography.labelSmall,
                                color = HtmlClay,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }
                }

                if (isEditing) {
                    // Avatar Picker
                    Text("Selecciona tu icono de viajero:", style = MaterialTheme.typography.labelMedium)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        avatars.forEach { av ->
                            Surface(
                                shape = CircleShape,
                                color = if (avatarEmoji == av) HtmlTeal.copy(alpha = 0.3f) else Color.Transparent,
                                modifier = Modifier
                                    .size(36.dp)
                                    .clickable { avatarEmoji = av }
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text(av, fontSize = 20.sp)
                                }
                            }
                        }
                    }

                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Nombre completo") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth().testTag("profile_edit_name")
                    )

                    OutlinedTextField(
                        value = phone,
                        onValueChange = { phone = it },
                        label = { Text("Teléfono / WhatsApp") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth().testTag("profile_edit_phone")
                    )

                    OutlinedTextField(
                        value = originCity,
                        onValueChange = { originCity = it },
                        label = { Text("Ciudad de residencia") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth().testTag("profile_edit_city")
                    )

                    OutlinedTextField(
                        value = bio,
                        onValueChange = { bio = it },
                        label = { Text("Biografía / Presentación de viajero") },
                        maxLines = 3,
                        modifier = Modifier.fillMaxWidth().testTag("profile_edit_bio")
                    )

                    OutlinedTextField(
                        value = favoriteCoffee,
                        onValueChange = { favoriteCoffee = it },
                        label = { Text("Método de café preferido") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth().testTag("profile_edit_coffee")
                    )

                    Text("Transporte preferido en la Sabana:", style = MaterialTheme.typography.labelSmall)
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        transportOptions.forEach { transport ->
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = if (preferredTransport == transport) HtmlTeal.copy(alpha = 0.2f) else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { preferredTransport = transport }
                            ) {
                                Text(
                                    text = transport,
                                    style = MaterialTheme.typography.bodySmall,
                                    fontWeight = if (preferredTransport == transport) FontWeight.Bold else FontWeight.Normal,
                                    color = if (preferredTransport == transport) HtmlTeal else MaterialTheme.colorScheme.onSurface,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                                )
                            }
                        }
                    }
                } else {
                    // Metric stats cards
                    Card(
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceAround
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text("${user.visitedPlacesCount}", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                                    Text("Lugares", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                }
                                Divider(modifier = Modifier.height(30.dp).width(1.dp))
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text("${user.completedRoutesCount}", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                                    Text("Rutas", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                }
                                Divider(modifier = Modifier.height(30.dp).width(1.dp))
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text("${user.reviewsCount}", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                                    Text("Reseñas", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))
                            Divider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
                            Spacer(modifier = Modifier.height(8.dp))

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.Eco, contentDescription = null, tint = HtmlTeal, modifier = Modifier.size(18.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("Impacto Cittaslow Sostenible:", style = MaterialTheme.typography.labelSmall)
                                }
                                Text("${user.ecoPoints} pts", fontWeight = FontWeight.Bold, color = HtmlTeal, style = MaterialTheme.typography.labelLarge)
                            }
                        }
                    }

                    // Preferences and Traveler Details
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Phone, contentDescription = null, modifier = Modifier.size(16.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Teléfono: ${user.phone}", style = MaterialTheme.typography.bodySmall)
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.LocationCity, contentDescription = null, modifier = Modifier.size(16.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Ciudad de origen: ${user.originCity}", style = MaterialTheme.typography.bodySmall)
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.DirectionsBus, contentDescription = null, modifier = Modifier.size(16.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Transporte favorito: ${user.preferredTransport}", style = MaterialTheme.typography.bodySmall)
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Coffee, contentDescription = null, modifier = Modifier.size(16.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Método de café: ${user.favoriteCoffeeMethod}", style = MaterialTheme.typography.bodySmall)
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.CalendarMonth, contentDescription = null, modifier = Modifier.size(16.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Miembro desde: ${user.memberSince}", style = MaterialTheme.typography.bodySmall)
                        }

                        if (user.bio.isNotBlank()) {
                            Card(
                                shape = RoundedCornerShape(10.dp),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)),
                                modifier = Modifier.fillMaxWidth().padding(top = 4.dp)
                            ) {
                                Row(modifier = Modifier.padding(10.dp), verticalAlignment = Alignment.Top) {
                                    Icon(Icons.Default.Info, contentDescription = null, modifier = Modifier.size(16.dp), tint = HtmlTeal)
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(text = user.bio, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                }
                            }
                        }
                    }
                }

                // Action buttons
                if (!isEditing) {
                    Button(
                        onClick = { isEditing = true },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primaryContainer, contentColor = MaterialTheme.colorScheme.onPrimaryContainer),
                        modifier = Modifier.fillMaxWidth().testTag("profile_edit_toggle")
                    ) {
                        Icon(Icons.Default.Edit, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Editar Perfil Completo")
                    }

                    // Sincronizar con el Servidor
                    OutlinedButton(
                        onClick = onSyncWithBackend,
                        modifier = Modifier.fillMaxWidth().testTag("profile_sync_button")
                    ) {
                        Icon(Icons.Default.CloudSync, contentDescription = null, modifier = Modifier.size(18.dp), tint = HtmlTeal)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Sincronizar con el Servidor", color = HtmlTeal)
                    }

                    if (user.isLoggedIn) {
                        OutlinedButton(
                            onClick = onLogout,
                            modifier = Modifier.fillMaxWidth().testTag("profile_logout_button")
                        ) {
                            Icon(Icons.Default.ExitToApp, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Cerrar Sesión")
                        }
                    } else {
                        Button(
                            onClick = onOpenAuth,
                            colors = ButtonDefaults.buttonColors(containerColor = HtmlTeal),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Iniciar Sesión / Registrarse")
                        }
                    }
                } else {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        OutlinedButton(
                            onClick = { isEditing = false },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Cancelar")
                        }
                        Button(
                            onClick = {
                                onSaveProfile(name, originCity, avatarEmoji, favoriteCoffee, phone, bio, preferredTransport)
                                isEditing = false
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = HtmlTeal),
                            modifier = Modifier.weight(1f).testTag("profile_save_button")
                        ) {
                            Text("Guardar")
                        }
                    }
                }
            }
        },
        confirmButton = {
            if (!isEditing) {
                Button(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(containerColor = HtmlTeal)
                ) {
                    Text("Listo")
                }
            }
        },
        shape = RoundedCornerShape(20.dp)
    )
}
