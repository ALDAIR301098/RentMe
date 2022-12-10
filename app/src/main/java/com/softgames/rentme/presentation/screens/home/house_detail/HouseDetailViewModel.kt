package com.softgames.rentme.presentation.screens.home.house_detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.softgames.rentme.domain.model.House

class HouseDetailViewModel : ViewModel() {

    var house: House? by mutableStateOf(House())
        private set

    fun updateHouse(_house: House) {
        this.house = _house
    }

}