package com.softgames.rentme.presentation.screens.home.guest_home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class GuestHomeViewModel : ViewModel() {

    /* *********************************** MUTABLE STATES *************************************** */

    var txtSearch by mutableStateOf("")
        private set

    /* ************************************* UPDATE STATES ************************************** */

    fun updateTxtSearch(_txtSearch: String) {
        txtSearch = _txtSearch
    }

    /* ************************************* FUNCTIONS ****************************************** */

}