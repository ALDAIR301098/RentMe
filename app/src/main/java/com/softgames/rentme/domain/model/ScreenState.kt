package com.softgames.rentme.domain.model

sealed class ScreenState {
    object USING : ScreenState()
    object WAITING: ScreenState()
    object LOADING : ScreenState()
    object FINISHED : ScreenState()
}
