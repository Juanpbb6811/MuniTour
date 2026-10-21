package com.example.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.ui.theme.BrandClay

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun BusinessRegistrationDialog(
    onDismiss: () -> Unit,
    onRegister: (
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
    ) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var kind by remember { mutableStateOf("sabor") }
    var tag by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var coffeeOrigin by remember { mutableStateOf("") }
    var baristaSpecialty by remember { mutableStateOf("") }
    var tableServiceDynamics by remember { mutableStateOf("Servicio completo a la mesa con barista dedicado") }
    var waitTime by remember { mutableStateOf("8 - 12 min") }
    var hours by remember { mutableStateOf("Mar - Dom: 8:00 a.m. - 7:30 p.m.") }
    var priceTier by remember { mutableStateOf("$$") }
    var ownerStory by remember { mutableStateOf("") }

    val availableMethods = listOf("V60 Dripper", "Chemex", "Prensa Francesa", "Espresso", "Aeropress", "Café de Olla Campesino", "Sifón")
    val selectedMethods = remember { mutableStateListOf("V60 Dripper", "Espresso") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Column {
                Text(
                    text = "🌱 Autogestión de Emprendimiento",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Posiciona tu negocio local frente a grandes cadenas de forma gratuita y sin intermediarios.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Tipo de comercio
                Text("Tipo de Establecimiento", style = MaterialTheme.typography.labelLarge)
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    FilterChip(
                        selected = kind == "sabor",
                        onClick = { kind = "sabor" },
                        label = { Text("🍽️ Gastronomía / Café") },
                        colors = FilterChipDefaults.filterChipColors(selectedContainerColor = BrandClay, selectedLabelColor = androidx.compose.ui.graphics.Color.White)
                    )
                    FilterChip(
                        selected = kind == "cultura",
                        onClick = { kind = "cultura" },
                        label = { Text("🏛️ Punto Cultural") }
                    )
                }

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Nombre del Local *") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth().testTag("add_business_name_field")
                )

                OutlinedTextField(
                    value = tag,
                    onValueChange = { tag = it },
                    label = { Text("Especialidad o Enfoque * (Ej: Café de especialidad y tortas)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth().testTag("add_business_tag_field")
                )

                OutlinedTextField(
                    value = address,
                    onValueChange = { address = it },
                    label = { Text("Dirección local (Ej: Cra 3 # 4-12)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                if (kind == "sabor") {
                    Text("Métodos de Preparación de Café:", style = MaterialTheme.typography.labelLarge)
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        availableMethods.forEach { method ->
                            val isSelected = selectedMethods.contains(method)
                            FilterChip(
                                selected = isSelected,
                                onClick = {
                                    if (isSelected) selectedMethods.remove(method)
                                    else selectedMethods.add(method)
                                },
                                label = { Text(method, style = MaterialTheme.typography.labelSmall) }
                            )
                        }
                    }

                    OutlinedTextField(
                        value = coffeeOrigin,
                        onValueChange = { coffeeOrigin = it },
                        label = { Text("Origen del café o ingrediente estrella") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = baristaSpecialty,
                        onValueChange = { baristaSpecialty = it },
                        label = { Text("Especialidad de Barismo / Receta propia") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                OutlinedTextField(
                    value = tableServiceDynamics,
                    onValueChange = { tableServiceDynamics = it },
                    label = { Text("Dinámica del servicio a la mesa") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = waitTime,
                        onValueChange = { waitTime = it },
                        label = { Text("Espera estimada") },
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = priceTier,
                        onValueChange = { priceTier = it },
                        label = { Text("Precio ($, $$, $$$)") },
                        modifier = Modifier.weight(1f)
                    )
                }

                OutlinedTextField(
                    value = hours,
                    onValueChange = { hours = it },
                    label = { Text("Horarios de atención") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = ownerStory,
                    onValueChange = { ownerStory = it },
                    label = { Text("Historia o motivación del emprendimiento") },
                    maxLines = 3,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (name.isNotBlank()) {
                        onRegister(
                            name,
                            kind,
                            tag.ifBlank { "Comercio local independiente" },
                            address.ifBlank { "Centro histórico" },
                            selectedMethods.toList(),
                            coffeeOrigin.ifBlank { "Granos de fincas regionales" },
                            baristaSpecialty.ifBlank { "Preparación artesanal fresca" },
                            tableServiceDynamics,
                            waitTime,
                            hours,
                            priceTier,
                            ownerStory
                        )
                    }
                },
                enabled = name.isNotBlank(),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                modifier = Modifier.testTag("submit_business_registration_button")
            ) {
                Text("Publicar Negocio")
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        },
        shape = RoundedCornerShape(20.dp)
    )
}
