package com.softgames.rentme.presentation.navigation

import androidx.activity.ComponentActivity
import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.softgames.rentme.presentation.navigation.Destinations.*
import com.softgames.rentme.presentation.screens.auth.login_screen.LoginScreen
import com.softgames.rentme.presentation.screens.auth.phone_auth_screen.PhoneAuthScreen
import com.softgames.rentme.presentation.screens.home.guest_home.GuestHomeScreen
import com.softgames.rentme.presentation.screens.home.host_home.HostHomeScreen
import com.softgames.rentme.presentation.screens.home.house_detail.HouseDetailScreen
import com.softgames.rentme.presentation.screens.home.register_house.RegisterHouseScreen
import com.softgames.rentme.presentation.screens.register.RegisterScreen

@Composable
fun NavigationHost(
    activity: ComponentActivity,
    startDestination: String,
) {

    val navController = rememberNavController()

    NavHost(
        navController = navController, startDestination = startDestination
    ) {

        composable(LoginScreen.route) {
            LoginScreen(
                onCloseScreen = { },
                navigatePhoneAuthScreen = { countryCode, phone ->
                    navController.navigate(PhoneAuthScreen.createRoute(countryCode, phone))
                },
                navigateGuestHomeScreen = {
                    navController.navigate(GuestHomeScreen.route) {
                        popUpTo(LoginScreen.route) { inclusive = true }
                    }
                },
                navigateHostHomeScreen = {}
            )
        }

        composable(PhoneAuthScreen.route) {

            val countryCode = it.arguments?.getString("countryCode")!!
            val phone = it.arguments?.getString("phone")!!

            PhoneAuthScreen(
                activity = activity,
                countryCode = countryCode,
                phone = phone,
                onBackPressed = { navController.popBackStack() },
                navigateRegisterScreen = { userId ->
                    navController.navigate(RegisterScreen.createRoute(userId)) {
                        popUpTo(PhoneAuthScreen.route) { inclusive = true }
                    }
                },
                navigateHostHomeScreen = { navController.navigate(HostHomeScreen.route) },
                navigateGuestHomeScreen = { navController.navigate(GuestHomeScreen.route) }
            )
        }

        composable(RegisterScreen.route) {

            val userId = it.arguments?.getString("userId") ?: ""

            RegisterScreen(
                userId = userId,
                activity = activity,
                navigateHostHomeScreen = {
                    navController.navigate(HostHomeScreen.route)
                },
                navigateGuestHomeScreen = {
                    navController.navigate(GuestHomeScreen.route)
                },
                onCloseScreen = { navController.popBackStack() }
            )
        }

        composable(GuestHomeScreen.route) {
            GuestHomeScreen(activity)
        }

        composable(HostHomeScreen.route) {
            HostHomeScreen(
                navigateRegisterHouseScreen = { userId ->
                    navController.navigate(RegisterHouseScreen.createRoute(userId))
                }
            )
        }

        composable(RegisterHouseScreen.route) {

            val userId = it.arguments?.getString("userId") ?: "Pepito"

            RegisterHouseScreen(
                userId = userId,
                activity = activity,
                onCloseClicked = { navController.popBackStack() }
            )
        }

        composable(HouseDetailScreen.route) {
            HouseDetailScreen()
        }

    }

}