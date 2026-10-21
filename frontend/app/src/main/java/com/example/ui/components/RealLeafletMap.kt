package com.example.ui.components

import android.annotation.SuppressLint
import android.webkit.JavascriptInterface
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Layers
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.data.model.Place
import com.example.ui.theme.HtmlTeal
import com.example.ui.theme.HtmlTealDark

class MapJsBridge(private val onPinClick: (String) -> Unit) {
    @JavascriptInterface
    fun onMarkerClicked(id: String) {
        onPinClick(id)
    }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun RealLeafletMap(
    places: List<Place>,
    selectedPlaceId: String,
    onSelectPlace: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var webViewRef by remember { mutableStateOf<WebView?>(null) }
    var isSatellite by remember { mutableStateOf(false) }

    val selectedPlace = places.firstOrNull { it.id == selectedPlaceId }

    // Center map when selectedPlace changes
    LaunchedEffect(selectedPlaceId) {
        selectedPlace?.let { p ->
            webViewRef?.evaluateJavascript(
                "if (window.centerOnPlace) { window.centerOnPlace('${p.id}', ${p.latitude}, ${p.longitude}); }",
                null
            )
        }
    }

    // Toggle satellite layer
    LaunchedEffect(isSatellite) {
        webViewRef?.evaluateJavascript(
            "if (window.setSatellite) { window.setSatellite($isSatellite); }",
            null
        )
    }

    val htmlContent = remember(places) {
        val markersJson = places.joinToString(separator = ",") { p ->
            """
            {
               id: "${p.id}",
               name: "${p.name.replace("\"", "\\\"")}",
               kind: "${p.kind}",
               emoji: "${p.emoji}",
               lat: ${p.latitude},
               lng: ${p.longitude},
               tag: "${p.tag.replace("\"", "\\\"")}"
            }
            """.trimIndent()
        }

        """
        <!DOCTYPE html>
        <html>
        <head>
            <meta charset="utf-8" />
            <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
            <link rel="stylesheet" href="https://unpkg.com/leaflet@1.9.4/dist/leaflet.css" />
            <script src="https://unpkg.com/leaflet@1.9.4/dist/leaflet.js"></script>
            <style>
                body, html, #map { margin: 0; padding: 0; width: 100%; height: 100%; background: #0F2F36; font-family: -apple-system, system-ui, sans-serif; }
                .custom-pin {
                    display: flex; align-items: center; justify-content: center;
                    border-radius: 50% 50% 50% 0;
                    transform: rotate(-45deg);
                    box-shadow: 0 4px 10px rgba(0,0,0,0.5);
                    cursor: pointer;
                    transition: transform 0.2s ease;
                }
                .custom-pin.sel {
                    transform: rotate(-45deg) scale(1.3);
                    border: 3px solid #FFF !important;
                    z-index: 9999 !important;
                }
                .pin-emoji {
                    transform: rotate(45deg);
                    font-size: 16px;
                }
                .leaflet-control-attribution { font-size: 9px !important; background: rgba(15,47,54,0.7) !important; color: #FFF !important; }
            </style>
        </head>
        <body>
            <div id="map"></div>
            <script>
                var places = [$markersJson];
                var map = L.map('map', { zoomControl: false }).setView([4.9186, -74.0259], 14);

                var streetLayer = L.tileLayer('https://{s}.basemaps.cartocdn.com/rastertiles/voyager/{z}/{x}/{y}{r}.png', {
                    maxZoom: 19,
                    attribution: '&copy; OpenStreetMap & CartoDB'
                }).addTo(map);

                var satelliteLayer = L.tileLayer('https://server.arcgisonline.com/ArcGIS/rest/services/World_Imagery/MapServer/tile/{z}/{y}/{x}', {
                    maxZoom: 19,
                    attribution: '&copy; Esri World Imagery'
                });

                window.setSatellite = function(enable) {
                    if (enable) {
                        map.removeLayer(streetLayer);
                        satelliteLayer.addTo(map);
                    } else {
                        map.removeLayer(satelliteLayer);
                        streetLayer.addTo(map);
                    }
                };

                // Draw inter-municipal tour polyline (Bogotá - Chía - Cajicá - Zipaquirá)
                var routeCoords = [
                    [4.8450, -74.0750], // Chía / El Noviciado
                    [4.8620, -74.0530], // Andrés Chía
                    [4.9120, -74.0290], // Fundo Llanero
                    [4.9186, -74.0259], // Parque Cajicá
                    [4.9198, -74.0272], // Centro Cultural Botero
                    [4.9310, -74.0385], // Montepincio / Mirador
                    [4.9450, -74.0150], // Periland Eco Park
                    [5.0192, -74.0041]  // Zipaquirá Catedral de Sal
                ];
                var routeLine = L.polyline(routeCoords, {
                    color: '#0F6C77',
                    weight: 4,
                    opacity: 0.85,
                    dashArray: '8, 8'
                }).addTo(map);

                var markers = {};
                places.forEach(function(p) {
                    var isCultura = p.kind === 'cultura';
                    var bg = isCultura ? '#0F6C77' : '#A2502B';
                    var iconHtml = '<div class="custom-pin" style="width:34px;height:34px;background:' + bg + ';border:2px solid #FFF;"><span class="pin-emoji">' + p.emoji + '</span></div>';
                    
                    var icon = L.divIcon({
                        className: '',
                        html: iconHtml,
                        iconSize: [34, 34],
                        iconAnchor: [17, 34]
                    });

                    var m = L.marker([p.lat, p.lng], { icon: icon }).addTo(map);
                    m.on('click', function() {
                        if (window.AndroidBridge && window.AndroidBridge.onMarkerClicked) {
                            window.AndroidBridge.onMarkerClicked(p.id);
                        }
                    });
                    markers[p.id] = m;
                });

                window.centerOnPlace = function(id, lat, lng) {
                    map.setView([lat, lng], 15, { animate: true });
                    // Highlight selected pin
                    Object.keys(markers).forEach(function(k) {
                        var el = markers[k].getElement();
                        if (el) {
                            var pin = el.querySelector('.custom-pin');
                            if (pin) {
                                if (k === id) {
                                    pin.classList.add('sel');
                                } else {
                                    pin.classList.remove('sel');
                                }
                            }
                        }
                    });
                };
            </script>
        </body>
        </html>
        """.trimIndent()
    }

    Box(modifier = modifier.fillMaxSize().testTag("real_map_container")) {
        AndroidView(
            factory = { context ->
                WebView(context).apply {
                    settings.javaScriptEnabled = true
                    settings.domStorageEnabled = true
                    settings.cacheMode = WebSettings.LOAD_DEFAULT
                    webViewClient = WebViewClient()
                    addJavascriptInterface(MapJsBridge { placeId ->
                        onSelectPlace(placeId)
                    }, "AndroidBridge")
                    loadDataWithBaseURL("https://munitour.co", htmlContent, "text/html", "UTF-8", null)
                    webViewRef = this
                }
            },
            update = { wv ->
                webViewRef = wv
            },
            modifier = Modifier.fillMaxSize()
        )

        // Floating Map Controls (Satellite Layer Toggle & Center Cajicá)
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 90.dp, end = 14.dp)
        ) {
            SmallFloatingActionButton(
                onClick = { isSatellite = !isSatellite },
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = if (isSatellite) HtmlTealDark else MaterialTheme.colorScheme.primary,
                modifier = Modifier.testTag("toggle_satellite_fab")
            ) {
                Icon(Icons.Default.Layers, contentDescription = "Alternar Satélite / Calles")
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 146.dp, end = 14.dp)
        ) {
            SmallFloatingActionButton(
                onClick = {
                    webViewRef?.evaluateJavascript("map.setView([4.9186, -74.0259], 14, { animate: true });", null)
                },
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.primary,
                modifier = Modifier.testTag("center_cajica_fab")
            ) {
                Icon(Icons.Default.MyLocation, contentDescription = "Centrar en Cajicá")
            }
        }
    }
}
