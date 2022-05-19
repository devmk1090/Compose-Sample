package com.devkproject.navigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.devkproject.navigation.data.UserData
import com.devkproject.navigation.ui.accounts.AccountsBody
import com.devkproject.navigation.ui.bills.BillsBody
import com.devkproject.navigation.ui.components.RallyTabRow
import com.devkproject.navigation.ui.overview.OverviewBody
import com.devkproject.navigation.ui.theme.RallyTheme

class RallyActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RallyApp()
        }
    }
}

@Composable
fun RallyApp() {
    RallyTheme {
        val allScreens = RallyScreen.values().toList()
        val navController = rememberNavController()
        val backstackEntry = navController.currentBackStackEntryAsState()
        val currentScreen = RallyScreen.fromRoute(
            backstackEntry.value?.destination?.route
        )
        Scaffold(
            topBar = {
                RallyTabRow(
                    allScreens = allScreens,
                    onTabSelected = { screen ->
                        navController.navigate(screen.name)
                    },
                    currentScreen = currentScreen
                )
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = RallyScreen.Overview.name,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(RallyScreen.Overview.name) {
                    OverviewBody(
                        onClickSeeAllAccounts = { navController.navigate(RallyScreen.Accounts.name) },
                        onClickSeeAllBills = { navController.navigate(RallyScreen.Bills.name) }
                    )
                }
                composable(RallyScreen.Accounts.name) {
                    AccountsBody(accounts = UserData.accounts)
                }
                composable(RallyScreen.Bills.name) {
                    BillsBody(bills = UserData.bills)
                }
            }
        }
    }
}