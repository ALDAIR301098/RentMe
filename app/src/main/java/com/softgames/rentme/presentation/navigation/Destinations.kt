package com.softgames.rentme.presentation.navigation

sealed class Destinations(val route: String) {
    object LoginScreen: Destinations("LoginScreen")
    object PhoneAuthScreen: Destinations("PhoneAuthScreen")
    object RegisterScreen: Destinations("RegisterScreen")
    object GuestHomeScreen: Destinations("GuestHomeScreen")
    object HostHomeScreen: Destinations("HostHomeScreen")
    object HouseDetailScreen: Destinations("HouseDetailScreen")
    object RegisterHouseScreen: Destinations("RegisterHouseScreen")
}
