package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = HtmlTealDark,
    onPrimary = HtmlBgDark,
    primaryContainer = HtmlPanelDark,
    onPrimaryContainer = HtmlFgDark,
    secondary = HtmlClayDark,
    onSecondary = Color(0xFF1A0D06),
    secondaryContainer = Color(0xFF2A1A10),
    onSecondaryContainer = Color(0xFFFFE2B8),
    tertiary = HtmlCherryDark,
    onTertiary = Color.White,
    background = HtmlBgDark,
    onBackground = HtmlFgDark,
    surface = HtmlPanelDark,
    onSurface = HtmlFgDark,
    surfaceVariant = Color(0xFF1B424D),
    onSurfaceVariant = HtmlMutedDark,
    outline = HtmlLineDark
)

private val LightColorScheme = lightColorScheme(
    primary = HtmlTeal,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFD6EFF2),
    onPrimaryContainer = HtmlFgLight,
    secondary = HtmlClay,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFFFF1D6),
    onSecondaryContainer = Color(0xFF4A1F0D),
    tertiary = HtmlCherry,
    onTertiary = Color.White,
    background = HtmlBgLight,
    onBackground = HtmlFgLight,
    surface = HtmlPanelLight,
    onSurface = HtmlFgLight,
    surfaceVariant = Color(0xFFE5EDEC),
    onSurfaceVariant = HtmlMutedLight,
    outline = HtmlLineLight
)

@Composable
fun MuniTourTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
