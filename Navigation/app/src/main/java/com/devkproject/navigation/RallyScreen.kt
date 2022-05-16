package com.devkproject.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PieChart
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

enum class RallyScreen (
    val icon: ImageVector,
    val body: @Composable ((String) -> Unit) -> Unit
) {
    OverView(
        icon = Icons.Filled.PieChart,
    )
}