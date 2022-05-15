package com.devkproject.navigation.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable

/**
 * A [MaterialTheme]
 */
@Composable
fun RallyTheme(content: @Composable () -> Unit) {
    MaterialTheme(colors = ColorPalette, typography = Typography, content = content)

}

/**
 * A theme overlay used for dialogs.
 */
@Composable
fun RallyDialogThemeOverlay(content: @Composable () -> Unit) {

}
