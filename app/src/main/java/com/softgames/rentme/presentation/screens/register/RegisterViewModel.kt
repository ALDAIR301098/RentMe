package com.softgames.rentme.presentation.screens.register

import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softgames.rentme.domain.model.RentMeUser
import com.softgames.rentme.domain.model.RentMeUser.*
import com.softgames.rentme.domain.model.ScreenState
import com.softgames.rentme.domain.model.TextFieldValue
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File

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

    var userType: RentMeUser by mutableStateOf(Guest)
        private set

    var screenState: ScreenState by mutableStateOf(ScreenState.USING)
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

    fun updateUserType(_userType: RentMeUser) {
        userType = _userType
    }

    /* ************************************* FUNCTIONS ****************************************** */


    fun tryContinueLogin() {
        if (validateTextFields()) screenState = ScreenState.FINISHED
    }

    private fun validateTextFields(): Boolean {
        if (name.text.isEmpty()) {
            name = name.copy(error = "Ingrese su nombre"); return false
        } else name = name.copy(error = null)
        if (lastName.text.isEmpty()) {
            lastName = lastName.copy(error = "Ingrese sus apellidos"); return false
        } else lastName = lastName.copy(error = null)
        if (gender.text.isEmpty()) {
            gender = gender.copy(error = "Seleccione su genero"); return false
        } else gender = gender.copy(error = null)
        if (birthDate.text.isEmpty()) {
            birthDate = birthDate.copy(error = "Ingrese su fecha de nacimiento"); return false
        } else birthDate = birthDate.copy(error = null);
        if (birthDate.text.length != 10) {
            birthDate = birthDate.copy(error = "Fecha incorrecta"); return false
        } else birthDate = birthDate.copy(error = null); return true
    }

    fun createUriFromFile(context: Context): Uri {
        val photoFile = File.createTempFile(
            "IMG_", ".jpg", context.getExternalFilesDir(Environment.DIRECTORY_PICTURES))
        return FileProvider.getUriForFile(context, "${context.packageName}.provider", photoFile)
    }

}