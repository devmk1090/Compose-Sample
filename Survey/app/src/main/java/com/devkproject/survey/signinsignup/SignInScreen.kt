package com.devkproject.survey.signinsignup

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable

sealed class SignInEvent {
    data class SignIn(val email: String, val password: String) : SignInEvent()
    object SignUp : SignInEvent()
    object SignInAsGuest : SignInEvent()
    object NavigateBack : SignInEvent()
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SignIn(onNavigationEvent: (SignInEvent) -> Unit) {

}
