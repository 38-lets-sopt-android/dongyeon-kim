package com.example.letssopt

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val registeredEmail: String = "",
    val registeredPassword: String = ""
)

class LoginViewModel : ViewModel() {
    var uiState by mutableStateOf(LoginUiState())
        private set

    fun onEmailChanged(value: String) {
        uiState = uiState.copy(email = value)
    }

    fun onPasswordChanged(value: String) {
        uiState = uiState.copy(password = value)
    }

    fun setRegisteredAccount(email: String, password: String) {
        uiState = uiState.copy(
            registeredEmail = email,
            registeredPassword = password
        )
    }

    fun isLoginSuccess(): Boolean {
        return uiState.email.isNotBlank() &&
            uiState.password.isNotBlank() &&
            uiState.email == uiState.registeredEmail &&
            uiState.password == uiState.registeredPassword
    }
}
