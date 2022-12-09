package com.softgames.rentme.domain.model

sealed class AuthState {
    object NOT_LOGGED : AuthState()
    object LOADING : AuthState()
    object LOGGED : AuthState()
}
