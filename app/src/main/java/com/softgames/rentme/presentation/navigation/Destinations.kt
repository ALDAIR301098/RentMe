package com.softgames.rentme.presentation.navigation

sealed class Destinations(val route: String) {
    object LoginScreen : Destinations("LoginScreen")
    object PhoneAuthScreen : Destinations("PhoneAuthScreen/{countryCode}/{phone}") {
        fun createRoute(countryCode: String, phone: String) = "PhoneAuthScreen/$countryCode/$phone"
    }

    object RegisterScreen : Destinations("RegisterScreen/{userId}") {
        fun createRoute(userId: String) = "RegisterScreen/$userId"
    }

    object GuestHomeScreen : Destinations("GuestHomeScreen")
    object HostHomeScreen : Destinations("HostHomeScreen")
    object HouseDetailScreen : Destinations("HouseDetailScreen")
    object RegisterHouseScreen : Destinations("RegisterHouseScreen")
}
