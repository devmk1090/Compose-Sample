package com.devkproject.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PieChart
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.devkproject.navigation.ui.overview.OverviewBody

enum class RallyScreen (
    val icon: ImageVector,
    val body: @Composable ((String) -> Unit) -> Unit
) {
    OverView(
        icon = Icons.Filled.PieChart,
        body = { OverviewBody() }
    );

    @Composable
    fun content(onScreenChange: (String) -> Unit) {
        body(onScreenChange)
    }
}