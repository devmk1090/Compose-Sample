package com.devkproject.jetchat.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import com.devkproject.jetchat.theme.JetchatTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JetchatScaffold (
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    onProfileClicked: (String) -> Unit,
    onChatClicked: (String) -> Unit,
    content: @Composable () -> Unit
) {
    JetchatTheme {
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                JetchatDrawer(
                    onProfileClicked = onProfileClicked,
                    onChatClicked = onChatClicked
                )
            },
            content = content
        )
    }
}