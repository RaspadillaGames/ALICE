package com.alice.app.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val AliceColorScheme = darkColorScheme(
    primary = AlicePrimary,
    secondary = AliceSecondary,
    background = AliceBackground,
    surface = AliceSurface,
    surfaceVariant = AliceSurfaceVariant,
    error = AliceError,
    onPrimary = AliceTextPrimary,
    onSecondary = AliceBackground,
    onBackground = AliceTextPrimary,
    onSurface = AliceTextPrimary,
    onSurfaceVariant = AliceTextSecondary,
    onError = AliceTextPrimary
)

@Composable
fun AliceTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = AliceBackground.toArgb()
            window.navigationBarColor = AliceBackground.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = false
            }
        }
    }

    MaterialTheme(
        colorScheme = AliceColorScheme,
        typography = AliceTypography,
        content = content
    )
}
