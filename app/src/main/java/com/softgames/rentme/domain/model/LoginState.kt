package com.softgames.rentme.domain.model

sealed class LoginState {
    object NOT_LOGGED : LoginState()
    object LOADING : LoginState()
    object LOGGED : LoginState()
}
