package com.example.ui.components

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.BitmapDrawable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.data.model.Place
import com.example.ui.theme.HtmlCherry
import com.example.ui.theme.HtmlClay
import com.example.ui.theme.HtmlTeal
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

@Composable
fun OsmMapView(
    places: List<Place>,
    selectedPlaceId: String,
    onSelectPlace: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    var mapView by remember { mutableStateOf<MapView?>(null) }

    LaunchedEffect(Unit) {
        Configuration.getInstance().userAgentValue = context.packageName
        Configuration.getInstance().load(context, context.getSharedPreferences("osmdroid", Context.MODE_PRIVATE))
    }

    val selectedPlace = places.firstOrNull { it.id == selectedPlaceId }

    LaunchedEffect(selectedPlaceId, mapView) {
        selectedPlace?.let { p ->
            mapView?.controller?.animateTo(GeoPoint(p.latitude, p.longitude), 16.0, 1000L)
        }
    }

    LaunchedEffect(places, selectedPlaceId, mapView) {
        val mv = mapView ?: return@LaunchedEffect
        mv.overlays.clear()
        
        places.forEach { place ->
            val marker = Marker(mv)
            marker.position = GeoPoint(place.latitude, place.longitude)
            marker.title = place.name
            marker.id = place.id
            marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
            
            val isSelected = place.id == selectedPlaceId
            val iconDrawable = createPinDrawable(context, place, isSelected)
            marker.icon = iconDrawable
            
            marker.setOnMarkerClickListener { m, _ ->
                onSelectPlace(m.id)
                true
            }
            mv.overlays.add(marker)
        }
        mv.invalidate()
    }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> mapView?.onResume()
                Lifecycle.Event.ON_PAUSE -> mapView?.onPause()
                else -> {}
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
            mapView?.onDetach()
        }
    }

    AndroidView(
        factory = { ctx ->
            MapView(ctx).apply {
                setTileSource(TileSourceFactory.MAPNIK)
                setMultiTouchControls(true)
                controller.setZoom(14.5)
                controller.setCenter(GeoPoint(4.9186, -74.0259)) // Cajicá
                mapView = this
            }
        },
        modifier = modifier
    )
}

private fun createPinDrawable(context: Context, place: Place, isSelected: Boolean): BitmapDrawable {
    val size = if (isSelected) 120 else 80
    val bitmap = Bitmap.createBitmap(size, size + 20, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    // Base color
    val color = when (place.kind) {
        "sabor" -> HtmlClay.toArgb()
        "cultura" -> HtmlCherry.toArgb()
        else -> HtmlTeal.toArgb()
    }

    // Shadow
    paint.color = 0x66000000
    canvas.drawCircle(size / 2f, size / 2f + 5, size / 2f - 10, paint)

    // Pin circle
    paint.color = color
    canvas.drawCircle(size / 2f, size / 2f, size / 2f - 10, paint)

    // Border
    paint.color = if (isSelected) 0xFFFFEB3B.toInt() else 0xFFFFFFFF.toInt()
    paint.style = Paint.Style.STROKE
    paint.strokeWidth = if (isSelected) 8f else 5f
    canvas.drawCircle(size / 2f, size / 2f, size / 2f - 10, paint)

    // Emoji text
    paint.style = Paint.Style.FILL
    paint.textSize = if (isSelected) 50f else 35f
    paint.textAlign = Paint.Align.CENTER
    val fontMetrics = paint.fontMetrics
    val textY = size / 2f - (fontMetrics.descent + fontMetrics.ascent) / 2
    canvas.drawText(place.emoji, size / 2f, textY, paint)

    return BitmapDrawable(context.resources, bitmap)
}
