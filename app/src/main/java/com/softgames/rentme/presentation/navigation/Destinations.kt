package com.softgames.rentme.presentation.navigation

sealed class Destinations(val route: String) {
    object LoginScreen: Destinations("LoginScreen")
    object PhoneAuthScreen: Destinations("PhoneAuthScreen")
    object MailAuthScreen: Destinations("MailAuthScreen")
    object RegisterScreen: Destinations("RegisterScreen")
    object HomeScreen: Destinations("HomeScreen")
}
