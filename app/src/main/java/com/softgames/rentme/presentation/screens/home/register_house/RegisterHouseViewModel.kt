package com.softgames.rentme.presentation.screens.home.register_house

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.util.toRange
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softgames.rentme.domain.model.*
import com.softgames.rentme.domain.model.RentMeUser.*
import com.softgames.rentme.domain.model.ScreenState.*
import com.softgames.rentme.services.RegisterService
import com.softgames.rentme.services.StorageService
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class RegisterHouseViewModel : ViewModel() {

    /* *********************************** MUTABLE STATES *************************************** */

    var currentUser: Host? by mutableStateOf(null)
        private set

    var registerError: String? by mutableStateOf(null)
        private set

    var txfHouseName by mutableStateOf(TextFieldValue())
        private set

    var txfColony by mutableStateOf(TextFieldValue())
        private set

    var txfDescription by mutableStateOf(TextFieldValue())
        private set

    var txfPrice by mutableStateOf(TextFieldValue())
        private set

    var txfGuestNumber by mutableStateOf(TextFieldValue())
        private set

    var txfRooms by mutableStateOf(TextFieldValue())
        private set

    var txfBeds by mutableStateOf(TextFieldValue())
        private set

    var txfBathrooms by mutableStateOf(TextFieldValue())
        private set

    var imageList = mutableStateListOf<Uri>()
        private set

    var screenState: ScreenState by mutableStateOf(USING)
        private set

    /* ************************************ UPDATE STATES *************************************** */

    fun updateHouseName(_houseName: String) {
        txfHouseName = txfHouseName.copy(text = _houseName)
    }

    fun updateColony(_colony: String) {
        txfColony = txfColony.copy(text = _colony)
    }

    fun updateDescription(_description: String) {
        txfDescription = txfDescription.copy(text = _description)
    }

    fun updatePrice(_price: String) {
        txfPrice = txfPrice.copy(text = _price)
    }

    fun updateGuestNumbers(_guestNumber: String) {
        txfGuestNumber = txfGuestNumber.copy(text = _guestNumber)
    }

    fun updateRooms(_rooms: String) {
        txfRooms = txfRooms.copy(text = _rooms)
    }

    fun updateBeds(_beds: String) {
        txfBeds = txfBeds.copy(text = _beds)
    }

    fun updateBathrooms(_batsrooms: String) {
        txfBathrooms = txfBathrooms.copy(text = _batsrooms)
    }

    fun addImageUri(imageUri: Uri) {
        imageList.add(imageUri)
    }

    fun hideRegisterErrorDialog() {
        registerError = null
    }

    fun updateCurrentUser(user: Host) {
        currentUser = user
    }

    fun updateScreenState(state: ScreenState) {
        screenState = state
    }

    /* ************************************** FUNCTIONS ***************************************** */

    fun registerHouse() {
        if (validateTextFields()) {
            try {
                screenState = LOADING
                viewModelScope.launch {
                    val house = House(
                        name = txfHouseName.text,
                        hostId = currentUser!!.uid,
                        hostName = currentUser!!.name,
                        hostPhoto = currentUser!!.photo,
                        colony = txfColony.text,
                        description = txfDescription.text,
                        price = txfPrice.text.toFloat(),
                        guestNumber = txfGuestNumber.text.toInt(),
                        rooms = txfRooms.text.toInt(),
                        beds = txfBeds.text.toInt(),
                        bathrooms = txfBathrooms.text.toInt(),
                        rating = (10..50).random().div(10).toFloat(),
                        timesRated = (1..100).random(),
                    )
                    val houseId = RegisterService.registerHouse(house).id
                    val photoUrls =
                        StorageService.uploadHousePhotos(currentUser!!.uid, houseId, imageList)
                    RegisterService.uploadHouseImagesUrls(houseId, photoUrls)
                    delay(500); screenState = FINISHED

                }

            } catch (e: Exception) {
                screenState = USING; registerError = e.message
            }
        }
    }

    fun validateTextFields(): Boolean {
        if (imageList.size == 0) {
            registerError = "Eliga una o mas fotos de la casa"; return false
        } else registerError = null
        if (txfHouseName.text.isEmpty()) {
            txfHouseName = txfHouseName.copy(error = "Ingrese el nombre"); return false
        } else txfHouseName = txfHouseName.copy(error = null)
        if (txfColony.text.isEmpty()) {
            txfColony = txfColony.copy(error = "Ingrese la colonia"); return false
        } else txfColony = txfColony.copy(error = null)
        if (txfDescription.text.isEmpty()) {
            txfDescription = txfDescription.copy(error = "Ingrese la descripcción"); return false
        } else txfDescription = txfDescription.copy(error = null)
        if (txfPrice.text.isEmpty()) {
            txfPrice = txfPrice.copy(error = "Ingrese el precio"); return false
        } else txfPrice = txfPrice.copy(error = null)
        if (txfGuestNumber.text.isEmpty()) {
            txfGuestNumber = txfGuestNumber.copy(error = "Vacío"); return false
        } else txfGuestNumber = txfGuestNumber.copy(error = null)
        if (txfRooms.text.isEmpty()) {
            txfRooms = txfRooms.copy(error = "Vacío"); return false
        } else txfRooms = txfRooms.copy(error = null)
        if (txfBeds.text.isEmpty()) {
            txfBeds = txfBeds.copy(error = "Vacío"); return false
        } else txfBeds = txfBeds.copy(error = null)
        if (txfBathrooms.text.isEmpty()) {
            txfBathrooms = txfBathrooms.copy(error = "Vacío"); return false
        } else txfBathrooms = txfBathrooms.copy(error = null); return true
    }

}