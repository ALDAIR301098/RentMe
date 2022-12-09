package com.softgames.rentme.presentation.screens.auth.login_screen

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
import com.softgames.rentme.util.unacent
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    /* *********************************** MUTABLE STATES *************************************** */

    var country: Country? by mutableStateOf(null)
        private set

    var txfCountry by mutableStateOf(TextFieldValue())
        private set

    var txfCountryCode by mutableStateOf(TextFieldValue())
        private set

    var txfPhone by mutableStateOf(TextFieldValue())
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
        country = _country
        txfCountry.text = _country.name
        txfCountryCode.text = _country.code
    }

    fun updateCountryCode(_code: String) {
        country = searchCountry(_code)
        txfCountryCode = txfCountryCode.copy(text = _code)
        txfCountry = txfCountry.copy(text = country?.name ?: "")
    }

    fun updatePhone(_phone: String) {
        txfPhone = txfPhone.copy(text = _phone)
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
    } catch (e: Exception) { null }

    fun validateTextFields(): Boolean {

        if (txfCountry.text.isEmpty()) {
            txfCountry = txfCountry.copy(error = "Seleccione un país."); return false
        } else txfCountry = txfCountry.copy(error = null)

        if (txfCountryCode.text.isEmpty()) {
            txfCountryCode = txfCountryCode.copy(error = "Vacío."); return false
        } else txfCountryCode = txfCountryCode.copy(error = null)

        if (txfPhone.text.isEmpty()) {
            txfPhone = txfPhone.copy(error = "Ingrese el telefóno."); return false
        } else txfPhone = txfPhone.copy(error = null);

        if (txfPhone.text.length != 10) {
            txfPhone = txfPhone.copy(error = "Ingrese 10 dígitos."); return false
        } else txfPhone = txfPhone.copy(error = null); return true

    }

}