package com.softgames.rentme.presentation.screens.home.host_home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class HostHomeViewModel : ViewModel() {

    /* *********************************** MUTABLE STATES *************************************** */

    var txtSearch by mutableStateOf("")
    private set

    /* ************************************* UPDATE STATES ************************************** */

    fun updateTxtSearch(_txtSearch: String){
        txtSearch = _txtSearch
    }

    /* ************************************* FUNCTIONS ****************************************** */

}