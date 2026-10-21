package com.example.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import com.example.data.model.Place
import com.example.ui.theme.BrandClay
import com.example.ui.theme.BrandTealDark
import com.example.ui.theme.BrandTealLight
import com.example.ui.theme.RibbonColor1

@Composable
fun InteractiveMapCanvas(
    places: List<Place>,
    selectedPlaceId: String,
    onSelectPlace: (String) -> Unit,
    destinationName: String,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.35f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseScale"
    )

    Box(modifier = modifier.fillMaxSize().testTag("interactive_map_canvas")) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(places) {
                    detectTapGestures { tapOffset ->
                        val w = size.width
                        val h = size.height
                        // Find closest place within touch radius (40dp approx)
                        var closestPlace: Place? = null
                        var minDistance = Float.MAX_VALUE
                        for (p in places) {
                            val px = p.xRatio * w
                            val py = p.yRatio * h
                            val dist = kotlin.math.hypot(tapOffset.x - px, tapOffset.y - py)
                            if (dist < 80f && dist < minDistance) {
                                minDistance = dist
                                closestPlace = p
                            }
                        }
                        closestPlace?.let { onSelectPlace(it.id) }
                    }
                }
        ) {
            val w = size.width
            val h = size.height

            // 1. Base Map Background
            drawRect(color = Color(0xFF0F2F36))

            // 2. Stylized Urban Blocks / Parks
            val blockColor = Color(0xFF143C45).copy(alpha = 0.65f)
            drawRect(
                color = blockColor,
                topLeft = Offset(w * 0.08f, h * 0.12f),
                size = Size(w * 0.38f, h * 0.15f)
            )
            drawRect(
                color = blockColor,
                topLeft = Offset(w * 0.54f, h * 0.10f),
                size = Size(w * 0.38f, h * 0.18f)
            )
            drawRect(
                color = blockColor,
                topLeft = Offset(w * 0.12f, h * 0.38f),
                size = Size(w * 0.32f, h * 0.22f)
            )
            drawRect(
                color = blockColor,
                topLeft = Offset(w * 0.52f, h * 0.36f),
                size = Size(w * 0.40f, h * 0.24f)
            )
            drawRect(
                color = blockColor,
                topLeft = Offset(w * 0.15f, h * 0.68f),
                size = Size(w * 0.70f, h * 0.18f)
            )

            // Green recreational park zone
            drawRoundRect(
                color = Color(0xFF1B5A63).copy(alpha = 0.4f),
                topLeft = Offset(w * 0.40f, h * 0.22f),
                size = Size(w * 0.25f, h * 0.16f),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(16f, 16f)
            )

            // 3. Grid Street Network (Thick Primary Arteries)
            val avenueColor = Color(0xFF26606A)
            drawLine(
                color = avenueColor,
                start = Offset(0f, h * 0.32f),
                end = Offset(w, h * 0.30f),
                strokeWidth = 6f
            )
            drawLine(
                color = avenueColor,
                start = Offset(0f, h * 0.64f),
                end = Offset(w, h * 0.62f),
                strokeWidth = 6f
            )
            drawLine(
                color = avenueColor,
                start = Offset(w * 0.48f, 0f),
                end = Offset(w * 0.48f, h),
                strokeWidth = 7f
            )

            // Secondary Streets
            val streetColor = Color(0xFF1B4A53)
            drawLine(
                color = streetColor,
                start = Offset(0f, h * 0.18f),
                end = Offset(w, h * 0.18f),
                strokeWidth = 3f
            )
            drawLine(
                color = streetColor,
                start = Offset(0f, h * 0.48f),
                end = Offset(w, h * 0.48f),
                strokeWidth = 3f
            )
            drawLine(
                color = streetColor,
                start = Offset(0f, h * 0.82f),
                end = Offset(w, h * 0.82f),
                strokeWidth = 3f
            )
            drawLine(
                color = streetColor,
                start = Offset(w * 0.22f, 0f),
                end = Offset(w * 0.22f, h),
                strokeWidth = 3.5f
            )
            drawLine(
                color = streetColor,
                start = Offset(w * 0.78f, 0f),
                end = Offset(w * 0.78f, h),
                strokeWidth = 3.5f
            )

            // 4. Connected Digital Tour Path (Walking Route Connecting Culture & Gastronomy)
            val tourPath = Path().apply {
                val p1 = Offset(w * 0.52f, h * 0.30f) // Parque
                val p2 = Offset(w * 0.35f, h * 0.41f) // Casa Cultura
                val p3 = Offset(w * 0.74f, h * 0.58f) // Café Central
                val p4 = Offset(w * 0.32f, h * 0.72f) // Fonda El Fogón
                moveTo(p1.x, p1.y)
                lineTo(p2.x, p2.y)
                lineTo(p3.x, p3.y)
                lineTo(p4.x, p4.y)
            }
            drawPath(
                path = tourPath,
                color = RibbonColor1.copy(alpha = 0.85f),
                style = Stroke(
                    width = 4f,
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(14f, 10f), 0f)
                )
            )

            // 5. Draw Pins
            places.forEach { place ->
                val px = place.xRatio * w
                val py = place.yRatio * h
                val isSelected = place.id == selectedPlaceId
                val pinColor = if (place.kind == "cultura") Color(0xFF1E9AA6) else BrandClay

                // Selection Pulsing Halo
                if (isSelected) {
                    drawCircle(
                        color = pinColor.copy(alpha = 0.35f),
                        radius = 28f * pulseScale,
                        center = Offset(px, py - 20f)
                    )
                    drawCircle(
                        color = Color.White.copy(alpha = 0.5f),
                        radius = 20f * pulseScale,
                        center = Offset(px, py - 20f)
                    )
                }

                // Pin Shadow
                drawOval(
                    color = Color.Black.copy(alpha = 0.4f),
                    topLeft = Offset(px - 10f, py - 3f),
                    size = Size(20f, 6f)
                )

                // Pin Body (Teardrop vector shape)
                val pinPath = Path().apply {
                    val topY = py - 38f
                    moveTo(px, py)
                    cubicTo(px - 14f, py - 18f, px - 18f, topY + 12f, px - 18f, topY)
                    cubicTo(px - 18f, topY - 10f, px - 10f, topY - 18f, px, topY - 18f)
                    cubicTo(px + 10f, topY - 18f, px + 18f, topY - 10f, px + 18f, topY)
                    cubicTo(px + 18f, topY + 12f, px + 14f, py - 18f, px, py)
                    close()
                }

                drawPath(path = pinPath, color = pinColor)
                drawPath(
                    path = pinPath,
                    color = Color.White,
                    style = Stroke(width = if (isSelected) 3.5f else 2f)
                )

                // Center Icon Circle
                drawCircle(
                    color = Color(0xFF0F2F36),
                    radius = 9f,
                    center = Offset(px, py - 38f)
                )
                drawCircle(
                    color = if (place.kind == "cultura") BrandTealLight else Color(0xFFFFE2B8),
                    radius = 5.5f,
                    center = Offset(px, py - 38f)
                )
            }
        }
    }
}
