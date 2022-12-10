package com.softgames.rentme.presentation.navigation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softgames.rentme.presentation.navigation.Destinations.*
import com.softgames.rentme.services.AuthService
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    var isLoading by mutableStateOf(true)
        private set

    var startDestination: String by mutableStateOf(LoginScreen.route)
        private set

    init {
        defineMainScreen()
    }

    private fun defineMainScreen() {
        viewModelScope.launch {
            //CHECK IS USER IS LOGGED IN
            val currentUser = AuthService.getCurrentUser()
            currentUser?.let {

                //CHECK IF USER IS REGISTRED
                val userInfo = AuthService.getUserInfo(currentUser.uid)
                if (userInfo.exists()) {

                    //CHECK USER TYPE
                    val userType = userInfo.getString("type")!!
                    when (userType) {
                        "Guest" -> {
                            startDestination = GuestHomeScreen.route
                            delay(500); isLoading = false
                        }
                        "Host" -> {
                            startDestination = HostHomeScreen.route
                            delay(500); isLoading = false
                        }
                    }

                } else {
                    //IF USER NOT EXIST SENT TO REGISTER SCREEN
                    startDestination = RegisterScreen.createRoute(currentUser.uid)
                    delay(500); isLoading = false
                }
            }
            delay(500); isLoading = false
        }
    }

}