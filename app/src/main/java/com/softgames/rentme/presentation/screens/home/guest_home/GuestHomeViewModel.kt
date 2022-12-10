package com.softgames.rentme.presentation.screens.home.guest_home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softgames.rentme.data.repository.HousesRepo
import com.softgames.rentme.domain.model.House
import com.softgames.rentme.domain.model.toHouse
import kotlinx.coroutines.launch

class GuestHomeViewModel : ViewModel() {

    /* *********************************** MUTABLE STATES *************************************** */

    var txtSearch by mutableStateOf("")
        private set

    var housesList: List<House> by mutableStateOf(emptyList())
        private set

    /* ************************************* UPDATE STATES ************************************** */

    fun updateTxtSearch(_txtSearch: String) {
        txtSearch = _txtSearch
    }

    /* ************************************* FUNCTIONS ****************************************** */

    init {
        viewModelScope.launch {
            housesList = HousesRepo.getFullHomeList().map { it.toHouse() }
        }
    }

}