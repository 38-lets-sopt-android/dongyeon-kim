package com.example.letssopt

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.example.letssopt.auth.LoginScreen
import com.example.letssopt.auth.LoginViewModel
import com.example.letssopt.auth.SignUpResult
import com.example.letssopt.auth.SignUpScreen
import com.example.letssopt.auth.SignUpViewModel
import com.example.letssopt.home.HomeScreen
import com.example.letssopt.home.HomeViewModel
import com.example.letssopt.ui.theme.LETSSOPTTheme

class MainActivity : ComponentActivity() {
    private val loginViewModel: LoginViewModel by viewModels()
    private val signUpViewModel: SignUpViewModel by viewModels()
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loginViewModel.setRegisteredAccount(
            AuthPreferenceManager.getRegisteredEmail(this),
            AuthPreferenceManager.getRegisteredPassword(this)
        )

        setContent {
            LETSSOPTTheme {
                AppRootScreen(
                    isLoggedIn = AuthPreferenceManager.isLoggedIn(this),
                    loginViewModel = loginViewModel,
                    signUpViewModel = signUpViewModel,
                    homeViewModel = homeViewModel
                )
            }
        }
    }
}

private enum class AppRoute {
    Login,
    SignUp,
    Home
}

@Composable
private fun AppRootScreen(
    isLoggedIn: Boolean,
    loginViewModel: LoginViewModel,
    signUpViewModel: SignUpViewModel,
    homeViewModel: HomeViewModel
) {
    val context = LocalContext.current
    var currentRoute by remember {
        mutableStateOf(if (isLoggedIn) AppRoute.Home else AppRoute.Login)
    }

    when (currentRoute) {
        AppRoute.Login -> {
            LoginScreen(
                viewModel = loginViewModel,
                onLoginClick = {
                    if (loginViewModel.login()) {
                        Toast.makeText(context, "로그인에 성공했습니다", Toast.LENGTH_SHORT).show()
                        AuthPreferenceManager.setLoggedIn(context, true)
                        currentRoute = AppRoute.Home
                    } else {
                        Toast.makeText(context, "로그인에 실패했습니다", Toast.LENGTH_SHORT).show()
                    }
                },
                onSignUpClick = {
                    currentRoute = AppRoute.SignUp
                }
            )
        }

        AppRoute.SignUp -> {
            SignUpScreen(
                viewModel = signUpViewModel,
                onSignUpClick = {
                    when (val result = signUpViewModel.signUp()) {
                        is SignUpResult.Success -> {
                            Toast.makeText(context, "회원가입에 성공했습니다", Toast.LENGTH_SHORT).show()
                            AuthPreferenceManager.saveRegisteredAccount(
                                context = context,
                                email = result.account.email,
                                password = result.account.password
                            )
                            loginViewModel.setRegisteredAccount(
                                result.account.email,
                                result.account.password
                            )
                            currentRoute = AppRoute.Login
                        }

                        is SignUpResult.Failure -> {
                            Toast.makeText(context, result.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            )
        }

        AppRoute.Home -> {
            HomeScreen(viewModel = homeViewModel)
        }
    }
}
