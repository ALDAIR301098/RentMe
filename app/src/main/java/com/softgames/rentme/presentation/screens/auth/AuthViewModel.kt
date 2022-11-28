package com.softgames.rentme.presentation.screens.auth

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.softgames.rentme.domain.model.LoginState
import com.softgames.rentme.domain.model.LoginState.NOT_LOGGED

class AuthViewModel : ViewModel() {

    /* *********************************** MUTABLE STATES *************************************** */

    var loginState: LoginState by mutableStateOf(NOT_LOGGED)
        private set

    var phoneAuthData by mutableStateOf(PhoneAuthData())
        private set

    /* ************************************ UPDATE STATES *************************************** */

    fun updatePhoneAuthData(data: PhoneAuthData) {
        phoneAuthData = data
    }

    /* ************************************* FUNCTIONS ****************************************** */

    fun sendVerificationCode() {

    }

}

data class PhoneAuthData(
    var countryCode: String = "",
    var phoneNumber: String = "",
    var smsCode: String = "",
    var codeWasSend: Boolean = false,
)