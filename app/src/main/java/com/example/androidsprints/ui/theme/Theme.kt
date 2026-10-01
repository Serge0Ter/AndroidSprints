package com.example.androidsprints.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = PrimaryColor,
    error = AccentColor,
    tertiary = AccentBlue,
    tertiaryContainer = SliderTrackColor,
    background = BackgroundColor,
    surface = SurfaceColor,
    outline = DividerColor,
    surfaceVariant = SurfaceVariantColor,
    onSurface = TextPrimaryColor,
    onBackground = TextPrimaryColor,
    onSurfaceVariant = TextSecondaryColor,
    onPrimary = SurfaceColor,
    onError = SurfaceColor
)
private val DarkColorScheme = darkColorScheme(
    primary = PrimaryColorDark,
    error = AccentColorDark,
    tertiary = AccentBlueDark,
    tertiaryContainer = SliderTrackColorDark,
    background = BackgroundColorDark,
    surface = SurfaceColorDark,
    outline = DividerColorDark,
    surfaceVariant = SurfaceVariantColorDark,
    onSurface = TextPrimaryColorDark,
    onBackground = TextPrimaryColorDark,
    onSurfaceVariant = TextSecondaryColorDark,
    onPrimary = SurfaceColorDark,
    onError = SurfaceColorDark

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun RecipesAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    //    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = recipesAppTypography,
        content = content
    )
}