package com.softgames.rentme.presentation.screens.auth.login_screen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softgames.rentme.data.model.Country
import com.softgames.rentme.data.source.countries
import com.softgames.rentme.domain.model.ScreenState
import com.softgames.rentme.domain.model.ScreenState.*
import com.softgames.rentme.domain.model.TextFieldValue
import com.softgames.rentme.presentation.util.unacent
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    /* *********************************** MUTABLE STATES *************************************** */

    var currentCountry: Country? by mutableStateOf(null)
        private set

    var country by mutableStateOf(TextFieldValue())
        private set

    var code by mutableStateOf(TextFieldValue())
        private set

    var phone by mutableStateOf(TextFieldValue())
        private set

    var screenState: ScreenState by mutableStateOf(USING)
        private set

    var showCountrySelector by mutableStateOf(false)
        private set

    var enableLoginButton by mutableStateOf(false)
        private set

    /* ************************************* UPDATE STATES ************************************** */

    fun updateSelectorVisibility(isVisible: Boolean) {
        showCountrySelector = isVisible
    }

    fun updateCountry(_country: Country) {
        currentCountry = _country
        country.text = _country.name
        code.text = _country.code
    }

    fun updateCode(_code: String) {
        currentCountry = searchCountry(_code)
        code = code.copy(text = _code)
        country = country.copy(text = currentCountry?.name ?: "")
    }

    fun updatePhone(_phone: String) {
        phone = phone.copy(text = _phone)
        enableLoginButton = (_phone.length == 10)
    }

    fun updateScreenState(state: ScreenState) {
        screenState = state
    }

    /* ************************************* FUNCTIONS ****************************************** */

    fun filterCountries(txtSearch: String): List<Country> {
        return countries.filter {
            it.name.unacent().contains(txtSearch, true) ||
                    it.name.contains(txtSearch, true)
        }
    }

    private fun searchCountry(code: String) = try {
        countries.filter { it.code == code }[0]
    } catch (e: Exception) {
        null
    }

    fun tryContinueAuth() {
        if (validateTextFields()) {
            viewModelScope.launch {
                screenState = LOADING; delay(500)
                screenState = FINISHED
            }
        }
    }

    private fun validateTextFields(): Boolean {

        if (country.text.isEmpty()) {
            country = country.copy(error = "Seleccione un país."); return false
        } else country = country.copy(error = null)

        if (code.text.isEmpty()) {
            code = code.copy(error = "Vacío."); return false
        } else code = code.copy(error = null)

        if (phone.text.isEmpty()) {
            phone = phone.copy(error = "Ingrese el telefóno."); return false
        } else phone = phone.copy(error = null);

        if (phone.text.length != 10) {
            phone = phone.copy(error = "Ingrese 10 dígitos."); return false
        } else phone = phone.copy(error = null); return true

    }

}