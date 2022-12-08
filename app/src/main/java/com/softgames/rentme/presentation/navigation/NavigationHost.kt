package com.softgames.rentme.presentation.navigation

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.softgames.rentme.presentation.navigation.Destinations.*
import com.softgames.rentme.presentation.screens.auth.AuthViewModel
import com.softgames.rentme.presentation.screens.auth.login_screen.LoginScreen
import com.softgames.rentme.presentation.screens.auth.phone_auth_screen.PhoneAuthScreen
import com.softgames.rentme.presentation.screens.home.guest_home.GuestHomeScreen
import com.softgames.rentme.presentation.screens.home.host_home.HostHomeScreen
import com.softgames.rentme.presentation.screens.home.register_house.RegisterHouseScreen
import com.softgames.rentme.presentation.screens.register.RegisterScreen

@Composable
fun NavigationHost(
    activity: ComponentActivity,
) {

    val navController = rememberNavController()
    val authViewModel: AuthViewModel = viewModel()

    NavHost(
        navController = navController, startDestination = RegisterHouseScreen.route
    ) {

        composable(LoginScreen.route) {
            LoginScreen(
                authViewModel = authViewModel,
                onCloseScreen = { },
                showPhoneAuthScreen = {
                    navController.navigate(PhoneAuthScreen.route)
                },
                showHomeScreen = {
                    navController.navigate(GuestHomeScreen.route) {
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
            RegisterScreen(activity)
        }

        composable(GuestHomeScreen.route) {
            GuestHomeScreen()
        }

        composable(HostHomeScreen.route) {
            HostHomeScreen()
        }

        composable(RegisterHouseScreen.route) {
            RegisterHouseScreen(
                activity = activity,
                onCloseClicked = { navController.popBackStack() }
            )
        }

    }

}