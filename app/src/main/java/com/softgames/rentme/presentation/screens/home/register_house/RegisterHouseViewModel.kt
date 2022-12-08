package com.softgames.rentme.presentation.screens.home.register_house

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.softgames.rentme.domain.model.TextFieldValue

class RegisterHouseViewModel : ViewModel() {

    /* *********************************** MUTABLE STATES *************************************** */

    var houseName by mutableStateOf(TextFieldValue())
        private set

    var colony by mutableStateOf(TextFieldValue())
        private set

    var description by mutableStateOf(TextFieldValue())
        private set

    var price by mutableStateOf(TextFieldValue())
        private set

    var guestNumber by mutableStateOf(TextFieldValue())
        private set

    var rooms by mutableStateOf(TextFieldValue())
        private set

    var beds by mutableStateOf(TextFieldValue())
        private set

    var bathrooms by mutableStateOf(TextFieldValue())
        private set

    var imageList: MutableList<Uri> by mutableStateOf(ArrayList())
        private set

    /* ************************************ UPDATE STATES *************************************** */

    fun updateHouseName(_houseName: String) {
        houseName = houseName.copy(text = _houseName)
    }

    fun updateColony(_colony: String) {
        colony = colony.copy(text = _colony)
    }

    fun updateDescription(_description: String) {
        description = description.copy(text = _description)
    }

    fun updatePrice(_price: String) {
        price = price.copy(text = _price)
    }

    fun updateGuestNumbers(_guestNumber: String) {
        guestNumber = guestNumber.copy(text = _guestNumber)
    }

    fun updateRooms(_rooms: String) {
        rooms = rooms.copy(text = _rooms)
    }

    fun updateBeds(_beds: String) {
        beds = beds.copy(text = _beds)
    }

    fun updateBathrooms(_batsrooms: String) {
        bathrooms = bathrooms.copy(text = _batsrooms)
    }

    fun addImageUri(imageUri: Uri) {
        imageList.add(imageUri)
    }


    /* ************************************** FUNCTIONS ***************************************** */

}