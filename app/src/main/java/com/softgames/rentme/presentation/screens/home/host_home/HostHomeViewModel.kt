package com.softgames.rentme.presentation.screens.home.host_home

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softgames.rentme.data.repository.HousesRepo
import com.softgames.rentme.domain.model.House
import com.softgames.rentme.domain.model.RentMeUser.Host
import com.softgames.rentme.domain.model.toHost
import com.softgames.rentme.domain.model.toHouse
import com.softgames.rentme.services.AuthService
import kotlinx.coroutines.launch

class HostHomeViewModel : ViewModel() {

    /* *********************************** MUTABLE STATES *************************************** */

    var user: Host? by mutableStateOf(null)
        private set

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
            val userId = AuthService.getCurrentUser()?.uid
            user = AuthService.getUserInfo(userId!!).toHost()
            housesList = HousesRepo.getHostHomesList(userId).map { it.toHouse() }
        }
    }

}