package com.softgames.rentme.presentation.screens.auth.phone_auth_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softgames.rentme.domain.model.ScreenState
import com.softgames.rentme.domain.model.ScreenState.*
import com.softgames.rentme.domain.model.TextFieldValue
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class PhoneAuthViewModel : ViewModel() {

    /* *********************************** MUTABLE STATES *************************************** */

    var code by mutableStateOf(TextFieldValue())
        private set

    var resendTime by mutableStateOf(10)
        private set

    var screenState: ScreenState by mutableStateOf(USING)
        private set

    /* ************************************ UPDATE STATES *************************************** */

    fun updateCode(_code: String) {
        code = code.copy(text = _code)
    }

    fun updateScreenState(state: ScreenState) {
        screenState = state
    }

    /* *************************************** FUNCTIONS **************************************** */

    init {
        startResendTimer()
    }

    private fun startResendTimer() {
        (10 downTo 0).asFlow().onEach { time ->
            delay(1000)
            resendTime = time
        }.launchIn(viewModelScope)
    }

    fun tryContunueAuth() {
        if (validateTextFields()) {
            viewModelScope.launch {
                screenState = LOADING; delay(500)
                screenState = FINISHED
            }
        }
    }

    private fun validateTextFields(): Boolean {
        if (code.text.isEmpty()) {
            code = code.copy(error = "Ingrese el código de verificación."); return false
        } else code = code.copy(error = null)

        if (code.text.length != 6) {
            code =
                code.copy(error = "El código de verificación debe contener 6 dígitos."); return false
        } else code = code.copy(error = null);

        if (code.text != "123456") {
            code = code.copy(error = "El código de verificación es incorrecto."); return false
        } else code = code.copy(error = null); return true

    }

}