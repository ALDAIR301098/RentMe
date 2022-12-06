package com.softgames.rentme.presentation.screens.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.softgames.rentme.domain.model.TextFieldValue

class RegisterViewModel : ViewModel() {

    /* *********************************** MUTABLE STATES *************************************** */

    var name by mutableStateOf(TextFieldValue())
        private set

    var lastName by mutableStateOf(TextFieldValue())
        private set

    var gender by mutableStateOf(TextFieldValue())
        private set

    var birthDate by mutableStateOf(TextFieldValue())
        private set

    var isPhotoDialogPickerVisible by mutableStateOf(false)
        private set

    var isDateDialogPickerVisible by mutableStateOf(false)
        private set

    /* ************************************* UPDATE STATES ************************************** */

    fun updateName(_name: String) {
        name = name.copy(text = _name)
    }

    fun updateLastName(_lastName: String) {
        lastName = lastName.copy(text = _lastName)
    }

    fun updateGender(_gender: String) {
        gender = gender.copy(text = _gender)
    }

    fun updateBirthDate(_birthDate: String) {
        birthDate = birthDate.copy(text = _birthDate)
    }

    fun showPhotoSelectorDialog() {
        isPhotoDialogPickerVisible = true
    }

    fun hidePhotoSelectorDialog() {
        isPhotoDialogPickerVisible = false
    }

    fun showDateDialogPicker() {
        isDateDialogPickerVisible = true
    }

    fun hideDateDialogPicker() {
        isDateDialogPickerVisible = false
    }

    /* ************************************* FUNCTIONS ****************************************** */


}