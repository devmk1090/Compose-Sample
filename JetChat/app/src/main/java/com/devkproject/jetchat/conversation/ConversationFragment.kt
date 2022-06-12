package com.devkproject.jetchat.conversation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.devkproject.jetchat.MainViewModel
import com.devkproject.jetchat.R
import com.devkproject.jetchat.data.exampleUiState
import com.devkproject.jetchat.theme.JetchatTheme

class ConversationFragment : Fragment() {

    private val activityViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(inflater.context).apply {
        layoutParams = LayoutParams(MATCH_PARENT, MATCH_PARENT)

        setContent {
            CompositionLocalProvider(
                LocalBackPressedDispatcher provides requireActivity().onBackPressedDispatcher
            ) {
                JetchatTheme {
                    ConversationContent(
                        uiState = exampleUiState,
                        navigateToProfile = { user ->
                            // Click callback
                            val bundle = bundleOf("userId" to user)
                            findNavController().navigate(
                                R.id.nav_profile,
                                bundle
                            )
                        },
                        onNavIconPressed = {
                            activityViewModel.openDrawer()
                        },
                        // Add padding so that we are inset from any navigation bars
                        modifier = Modifier.windowInsetsPadding(
                            WindowInsets
                                .navigationBars
                                .only(WindowInsetsSides.Horizontal + WindowInsetsSides.Top)
                        )
                    )
                }
            }
        }
    }
}