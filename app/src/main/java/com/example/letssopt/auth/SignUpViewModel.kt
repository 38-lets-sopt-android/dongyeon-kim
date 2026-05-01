package com.example.letssopt.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

data class SignUpUiState(
    val email: String = "",
    val password: String = "",
    val passwordCheck: String = ""
) {
    val isSignUpButtonEnabled: Boolean
        get() = email.isNotBlank() && password.isNotBlank() && passwordCheck.isNotBlank()
}

data class SignUpAccount(
    val email: String,
    val password: String
)

sealed interface SignUpResult {
    data class Success(val account: SignUpAccount) : SignUpResult
    data class Failure(val message: String) : SignUpResult
}

class SignUpViewModel : ViewModel() {
    var uiState by mutableStateOf(SignUpUiState())
        private set

    fun onEmailChanged(value: String) {
        uiState = uiState.copy(email = value)
    }

    fun onPasswordChanged(value: String) {
        uiState = uiState.copy(password = value)
    }

    fun onPasswordCheckChanged(value: String) {
        uiState = uiState.copy(passwordCheck = value)
    }

    fun signUp(): SignUpResult {
        val trimmedEmail = uiState.email.trim()

        if (!isValidEmail(trimmedEmail)) {
            return SignUpResult.Failure("이메일 형식이 올바르지 않습니다")
        }

        if (uiState.password.length !in 8..12) {
            return SignUpResult.Failure("비밀번호는 8~12글자여야 합니다")
        }

        if (uiState.password != uiState.passwordCheck) {
            return SignUpResult.Failure("비밀번호가 일치하지 않습니다")
        }

        return SignUpResult.Success(
            SignUpAccount(
                email = trimmedEmail,
                password = uiState.password
            )
        )
    }

    private fun isValidEmail(email: String): Boolean {
        return EMAIL_REGEX.matches(email)
    }

    private companion object {
        val EMAIL_REGEX = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")
    }
}
