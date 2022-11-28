package com.softgames.rentme.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.softgames.rentme.presentation.navigation.Destinations.*
import com.softgames.rentme.presentation.screens.auth.AuthViewModel
import com.softgames.rentme.presentation.screens.auth.login_screen.LoginScreen
import com.softgames.rentme.presentation.screens.auth.phone_auth_screen.PhoneAuthScreen
import com.softgames.rentme.presentation.screens.menu.HomeScreen
import com.softgames.rentme.presentation.screens.register.RegisterScreen

@Composable
fun NavigationHost() {

    val navController = rememberNavController()
    val authViewModel: AuthViewModel = viewModel()

    NavHost(
        navController = navController, startDestination = LoginScreen.route
    ) {

        composable(LoginScreen.route) {
            LoginScreen(
                authViewModel = authViewModel,
                onCloseScreen = { },
                showPhoneAuthScreen = {
                    navController.navigate(PhoneAuthScreen.route)
                },
                showHomeScreen = {
                    navController.navigate(HomeScreen.route) {
                        popUpTo(LoginScreen.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(PhoneAuthScreen.route) {
            PhoneAuthScreen(
                authViewModel = authViewModel,
                onBackPressed = { navController.popBackStack() },
                showRegisterScreen = { navController.navigate(RegisterScreen.route) }
            )
        }

        composable(RegisterScreen.route) {
            RegisterScreen()
        }

        composable(HomeScreen.route) {
            HomeScreen()
        }

    }

}