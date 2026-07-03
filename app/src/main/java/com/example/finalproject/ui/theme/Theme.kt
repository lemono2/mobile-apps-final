package com.example.finalproject.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val StadiumColorScheme = darkColorScheme(
    primary = StadiumGreen,
    onPrimary = Color(0xFF00210E),
    primaryContainer = Color(0xFF00391C),
    onPrimaryContainer = StadiumLime,
    secondary = StadiumLime,
    onSecondary = Color(0xFF17380B),
    secondaryContainer = Color(0xFF16351A),
    onSecondaryContainer = StadiumLime,
    tertiary = StadiumLime,
    onTertiary = Color(0xFF17380B),
    background = StadiumCharcoal,
    onBackground = StadiumOnSurface,
    surface = StadiumSurface,
    onSurface = StadiumOnSurface,
    surfaceVariant = StadiumSurfaceVariant,
    onSurfaceVariant = StadiumOnSurfaceVariant,
    error = Color(0xFFFF5252),
    onError = Color(0xFF370000),
)

@Composable
fun FinalProjectTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = StadiumColorScheme,
        typography = Typography,
        content = content,
    )
}
