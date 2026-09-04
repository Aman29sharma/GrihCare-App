package com.example.grihacare.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val GrihaCareColorScheme = lightColorScheme(

    primary = GrihaTeal,

    onPrimary = GrihaWhite,

    primaryContainer = GrihaMint,

    onPrimaryContainer = GrihaTealDark,

    secondary = GrihaWarning,

    onSecondary = GrihaWhite,

    background = GrihaBackground,

    onBackground = GrihaBlack,

    surface = GrihaWhite,

    onSurface = GrihaBlack,

    surfaceVariant = GrihaMint,

    onSurfaceVariant = GrihaGray,

    outline = GrihaBorder,

    error = GrihaRed,

    onError = GrihaWhite
)

@Composable
fun GrihaCareTheme(
    content: @Composable () -> Unit
) {

    MaterialTheme(

        colorScheme = GrihaCareColorScheme,

        typography = Typography(),

        content = content
    )
}