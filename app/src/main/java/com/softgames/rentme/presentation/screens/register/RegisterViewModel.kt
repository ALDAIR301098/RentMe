package com.softgames.rentme.presentation.screens.register

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softgames.rentme.domain.model.RentMeUser
import com.softgames.rentme.domain.model.RentMeUser.Guest
import com.softgames.rentme.domain.model.ScreenState
import com.softgames.rentme.domain.model.ScreenState.*
import com.softgames.rentme.domain.model.TextFieldValue
import com.softgames.rentme.services.RegisterService
import com.softgames.rentme.services.StorageService
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {

    /* *********************************** MUTABLE STATES *************************************** */

    var userId: String? by mutableStateOf(null)
        private set

    var imageUri: Uri? by mutableStateOf(null)
        private set

    var txfName by mutableStateOf(TextFieldValue())
        private set

    var txfLastName by mutableStateOf(TextFieldValue())
        private set

    var txfGender by mutableStateOf(TextFieldValue())
        private set

    var txfBirthDate by mutableStateOf(TextFieldValue())
        private set

    var user: RentMeUser by mutableStateOf(Guest)
        private set

    var screenState: ScreenState by mutableStateOf(USING)
        private set

    var registerError: String? by mutableStateOf(null)
        private set

    /* ************************************* UPDATE STATES ************************************** */

    fun updateUserId(_userId: String) {
        userId = _userId
    }

    fun updateName(_name: String) {
        txfName = txfName.copy(text = _name)
    }

    fun updateLastName(_lastName: String) {
        txfLastName = txfLastName.copy(text = _lastName)
    }

    fun updateGender(_gender: String) {
        txfGender = txfGender.copy(text = _gender)
    }

    fun updateBirthDate(_birthDate: String) {
        txfBirthDate = txfBirthDate.copy(text = _birthDate)
    }

    fun updateUserType(_userType: RentMeUser) {
        user = _userType
    }

    fun updateImageUri(_imageUri: Uri) {
        imageUri = _imageUri
    }

    fun updateScreenState(_screenState: ScreenState) {
        screenState = _screenState
    }

    /* ************************************* FUNCTIONS ****************************************** */


    fun tryRegisterUser(
        onSuccess: () -> Unit,
    ) {
        if (validateTextFields()) {
            viewModelScope.launch {
                try {

                    //UPDATE SCREEN STATE
                    screenState = LOADING

                    //SET DATA TO USER
                    user.apply {
                        uid = userId!!; name = txfName.text; lastName = txfLastName.text
                        gender = txfGender.text; birthDate = txfBirthDate.text
                    }

                    //UPLOAD PHOTO AND SET TO USER
                    if (imageUri != null) {
                        tryUploadPhoto(userId!!, imageUri!!) { imageUrl ->
                            user.photo = imageUrl

                            //REGISER USER
                            viewModelScope.launch {
                                RegisterService.registerUser(user)
                                onSuccess(); delay(500)
                                screenState = FINISHED
                            }
                        }

                    // REGISTER USER WITHOUT PHOTO
                    } else {
                        RegisterService.registerUser(user)
                        onSuccess(); delay(500)
                        screenState = FINISHED
                    }

                } catch (e: Exception) {
                    screenState = USING
                    Log.d("ALDAIR", "ERROR WHEN REGISTER USER: ${e.message}")
                }
            }
        }
    }

    private fun tryUploadPhoto(
        userId: String,
        uri: Uri,
        onSuccess: (String) -> Unit,
    ) {
        viewModelScope.launch {
            try {
                val imageUrl = StorageService.uploadUserPhoto(userId, uri)
                onSuccess(imageUrl)
            } catch (e: Exception) {
                screenState = USING
                registerError = e.message
                Log.d("ALDAIR", "ERROR WHEN UPLOAD PHOTO USER: ${e.message}")
            }
        }
    }

    private fun validateTextFields(): Boolean {
        if (txfName.text.isEmpty()) {
            txfName = txfName.copy(error = "Ingrese su nombre"); return false
        } else txfName = txfName.copy(error = null)
        if (txfLastName.text.isEmpty()) {
            txfLastName = txfLastName.copy(error = "Ingrese sus apellidos"); return false
        } else txfLastName = txfLastName.copy(error = null)
        if (txfGender.text.isEmpty()) {
            txfGender = txfGender.copy(error = "Seleccione su genero"); return false
        } else txfGender = txfGender.copy(error = null)
        if (txfBirthDate.text.isEmpty()) {
            txfBirthDate = txfBirthDate.copy(error = "Ingrese su fecha de nacimiento"); return false
        } else txfBirthDate = txfBirthDate.copy(error = null);
        if (txfBirthDate.text.length != 8) {
            txfBirthDate = txfBirthDate.copy(error = "Fecha incorrecta"); return false
        } else txfBirthDate = txfBirthDate.copy(error = null); return true
    }

}