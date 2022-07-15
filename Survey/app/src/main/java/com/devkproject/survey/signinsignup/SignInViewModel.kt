package com.devkproject.survey.signinsignup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.devkproject.survey.Screen
import com.devkproject.survey.Screen.SignUp
import com.devkproject.survey.Screen.Survey
import com.devkproject.survey.repository.UserRepository
import com.devkproject.survey.util.Event

class SignInViewModel (private val userRepository: UserRepository): ViewModel() {

    private val _navigateTo = MutableLiveData<Event<Screen>>()
    val navigateTo: LiveData<Event<Screen>>
        get() = _navigateTo

    /**
     * Consider all sign ins successful
     */
    fun signIn(email: String, password: String) {
        userRepository.signIn(email, password)
        _navigateTo.value = Event(Survey)
    }

    fun signInAsGuest() {
        userRepository.signInAsGuest()
        _navigateTo.value = Event(Survey)
    }

    fun signUp() {
        _navigateTo.value = Event(SignUp)
    }
}

class SignInViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignInViewModel::class.java)) {
            return SignInViewModel(UserRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}