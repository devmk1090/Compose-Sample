package com.devkproject.jetchat

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.material3.DrawerValue.Closed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.*
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.viewinterop.AndroidViewBinding
import androidx.core.os.bundleOf
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.devkproject.jetchat.components.JetchatScaffold
import com.devkproject.jetchat.conversation.BackPressHandler
import com.devkproject.jetchat.conversation.LocalBackPressedDispatcher
import com.devkproject.jetchat.databinding.ContentMainBinding
import kotlinx.coroutines.launch

class NavActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Turn off the decor fitting system windows, which allows us to handle insets,
        // including IME animations
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContentView(
            ComposeView(this).apply {
                consumeWindowInsets = false
                setContent {
                    CompositionLocalProvider(
                        LocalBackPressedDispatcher provides this@NavActivity.onBackPressedDispatcher
                    ) {
                        val drawerState = rememberDrawerState(initialValue = Closed)

                        val drawerOpen by viewModel.drawerShouldBeOpened.collectAsState()
                        if (drawerOpen) {
                            // Open drawer and reset state in VM.
                            LaunchedEffect(Unit) {
                                drawerState.open()
                                viewModel.resetOpenDrawerAction()
                            }
                        }

                        // Intercepts back navigation when the drawer is open
                        val scope = rememberCoroutineScope()
                        if (drawerState.isOpen) {
                            BackPressHandler {
                                scope.launch {
                                    drawerState.close()
                                }
                            }
                        }

                        JetchatScaffold(
                            drawerState = drawerState,
                            onChatClicked = {
                                findNavController().popBackStack(R.id.nav_home, false)
                                scope.launch {
                                    drawerState.close()
                                }
                            },
                            onProfileClicked = {
                                val bundle = bundleOf("userId" to it)
                                findNavController().navigate(R.id.nav_profile, bundle)
                                scope.launch {
                                    drawerState.close()
                                }
                            }
                        ) {
                            AndroidViewBinding(ContentMainBinding::inflate)
                        }
                    }
                }
            }
        )
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController().navigateUp() || super.onSupportNavigateUp()
    }

    /**
     * See https://issuetracker.google.com/142847973
     */
    private fun findNavController(): NavController {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        return navHostFragment.navController
    }
}