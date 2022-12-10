package com.softgames.rentme.presentation.navigation

import com.softgames.rentme.domain.model.House

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

    object HouseDetailScreen : Destinations("HouseDetailScreen/{houseId}") {
        fun createRoute(houseId: String) = "HouseDetailScreen/$houseId"
    }

    object RegisterHouseScreen : Destinations("RegisterHouseScreen/{userId}") {
        fun createRoute(userId: String) = "RegisterHouseScreen/$userId"
    }
}
