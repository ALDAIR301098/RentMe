package com.softgames.rentme.presentation.screens.auth.phone_auth_screen

import android.app.Activity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.PhoneAuthProvider
import com.softgames.rentme.domain.model.FirebaseCodeResponse
import com.softgames.rentme.domain.model.ScreenState
import com.softgames.rentme.domain.model.ScreenState.*
import com.softgames.rentme.domain.model.TextFieldValue
import com.softgames.rentme.services.PhoneAuthService
import com.softgames.rentme.services.RegisterService
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class PhoneAuthViewModel : ViewModel() {

    /* *********************************** MUTABLE STATES *************************************** */

    var userId: String? by mutableStateOf(null)
        private set

    var screenState: ScreenState by mutableStateOf(USING)
        private set

    var authError: String? by mutableStateOf(null)
        private set

    var phone by mutableStateOf("")
        private set

    var countryCode by mutableStateOf("")
        private set

    var txfSmsCode by mutableStateOf(TextFieldValue())
        private set

    var resendTime by mutableStateOf(10)
        private set

    var codeWasSend by mutableStateOf(false)
        private set

    private var verificationId by mutableStateOf("")

    private var token: PhoneAuthProvider.ForceResendingToken? by mutableStateOf(null)


    /* ************************************ UPDATE STATES *************************************** */

    fun updatePhone(_phone: String) {
        phone = _phone
    }

    fun updateCountryCode(_countryCode: String) {
        countryCode = _countryCode
    }

    fun updateSmsCode(_smsCode: String) {
        txfSmsCode = txfSmsCode.copy(text = _smsCode)
    }

    fun updateScreenState(_screenState: ScreenState) {
        screenState = _screenState
    }

    /* ************************************* FUNCTIONS ****************************************** */

    init {
        startResendTimer()
    }

    fun sendVerificationCode(
        activity: Activity,
        onCodeSent: () -> Unit,
        onSignInSuccessful: () -> Unit,
    ) {
        viewModelScope.launch {
            val phoneNumber = "+".plus(countryCode).plus(phone)
            PhoneAuthService.sendVerificationCode(phoneNumber, activity) { codeResponse ->

                when (codeResponse) {

                    is FirebaseCodeResponse.AUTOMAMTIC_VERIFICATION -> {
                        txfSmsCode = txfSmsCode.copy(text = codeResponse.credential.smsCode ?: "")
                        screenState = USING
                        codeWasSend = true
                        signInRentMe(onSignInSuccessful)
                    }

                    is FirebaseCodeResponse.FAILURE -> {
                        authError = codeResponse.exception.message
                        screenState = USING
                    }

                    is FirebaseCodeResponse.LOADING -> {
                        screenState = LOADING
                    }

                    is FirebaseCodeResponse.ON_CODE_SEND -> {
                        screenState = USING
                        verificationId = codeResponse.verificationId
                        token = codeResponse.token
                        codeWasSend = true
                        onCodeSent()
                    }

                }

            }
        }
    }

    fun signInRentMe(
        onSuccess: () -> Unit,
    ) {
        if (validateTextFields()) {

            screenState = LOADING
            val credential = PhoneAuthService.generateCredential(
                verificationId, txfSmsCode.text
            )

            viewModelScope.launch {
                try {
                    PhoneAuthService.signInWithPhoneAuthCredential(credential) {
                        if (it.isSuccessful) {
                            userId = it.result.user?.uid; onSuccess(); screenState = FINISHED
                        } else {
                            screenState = USING; authError = it.exception?.message
                        }
                    }
                } catch (e: Exception) {
                    authError = e.message
                }
            }

        }
    }

    suspend fun checkIfUserExist() = RegisterService.checkIfUserExist(userId!!)

    private fun validateTextFields(): Boolean {
        if (txfSmsCode.text.isEmpty()) {
            txfSmsCode = txfSmsCode.copy(error = "Ingrese el código de verificación."); return false
        } else txfSmsCode = txfSmsCode.copy(error = null)

        if (txfSmsCode.text.length != 6) {
            txfSmsCode =
                txfSmsCode.copy(error = "El código de verificación debe contener 6 dígitos."); return false
        } else txfSmsCode = txfSmsCode.copy(error = null); return true
    }

    private fun startResendTimer() {
        (10 downTo 0).asFlow().onEach { time ->
            delay(1000)
            resendTime = time
        }.launchIn(viewModelScope)
    }

}
