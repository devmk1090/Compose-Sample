package com.devkproject.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.MoneyOff
import androidx.compose.material.icons.filled.PieChart
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.devkproject.navigation.data.UserData
import com.devkproject.navigation.ui.accounts.AccountsBody
import com.devkproject.navigation.ui.overview.OverviewBody
import com.devkproject.navigation.ui.bills.BillsBody

enum class RallyScreen (
    val icon: ImageVector,
) {
    Overview(
        icon = Icons.Filled.PieChart,
    ),
    Accounts(
        icon = Icons.Filled.AttachMoney,
    ),
    Bills(
        icon = Icons.Filled.MoneyOff,
    );

    companion object {
        fun fromRoute(route: String?): RallyScreen =
            when (route?.substringBefore("/")) {
                Accounts.name -> Accounts
                Bills.name -> Bills
                Overview.name -> Overview
                null -> Overview
                else -> throw IllegalArgumentException("Route $route is not recognized.")
            }
    }
}