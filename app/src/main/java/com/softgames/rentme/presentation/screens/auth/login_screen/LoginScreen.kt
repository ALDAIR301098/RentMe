@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)

package com.softgames.rentme.presentation.screens.auth.login_screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.softgames.rentme.domain.model.LoginState.*
import com.softgames.rentme.domain.model.LoginState.LOADING
import com.softgames.rentme.domain.model.ScreenState
import com.softgames.rentme.domain.model.ScreenState.*
import com.softgames.rentme.presentation.screens.auth.AuthViewModel
import com.softgames.rentme.presentation.screens.auth.PhoneAuthData
import com.softgames.rentme.presentation.screens.auth.login_screen.composables.*
import com.softgames.rentme.presentation.theme.RentMeTheme

@Composable
fun LoginScreen(
    onCloseScreen: () -> Unit,
    showPhoneAuthScreen: () -> Unit,
    showHomeScreen: () -> Unit,
    authViewModel: AuthViewModel = viewModel(),
    viewModel: LoginViewModel = viewModel(),
) {

    when (authViewModel.loginState) {

        is LOGGED -> {
            LaunchedEffect(Unit) { showHomeScreen() }
        }

        is NOT_LOGGED, LOADING -> {

            when (viewModel.screenState) {

                is FINISHED -> {
                    LaunchedEffect(Unit) {
                        authViewModel.updatePhoneAuthData(PhoneAuthData(
                            countryCode = viewModel.code.text,
                            phoneNumber = viewModel.phone.text
                        ))
                        viewModel.updateScreenState(WAITING)
                        showPhoneAuthScreen()
                    }
                }
                else -> {

                    val keyboardController = LocalSoftwareKeyboardController.current

                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        topBar = { LoginAppBar(onCloseScreen) }
                    ) { paddingValues ->

                        Column(
                            Modifier.padding(paddingValues)
                        ) {

                            if (
                                authViewModel.loginState == LOADING ||
                                viewModel.screenState == ScreenState.LOADING
                            ) {
                                LinearProgressIndicator(Modifier.fillMaxWidth())
                                Spacer(Modifier.height(16.dp))
                            }

                            Column(
                                modifier = Modifier.padding(horizontal = 20.dp),
                                verticalArrangement = Arrangement.spacedBy(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {

                                WelcomeImage()

                                Column {

                                    CountryDropDownMenu(
                                        country = viewModel.currentCountry,
                                        error = viewModel.country.error,
                                        showCountrySelector = viewModel.showCountrySelector,
                                        onCountrySelectorVisibilityChange = {
                                            viewModel.updateSelectorVisibility(it)
                                        }
                                    )

                                    Spacer(Modifier.height(4.dp))

                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                                    ) {

                                        CodeTextField(
                                            text = viewModel.code.text,
                                            error = viewModel.code.error
                                        ) {
                                            viewModel.updateCode(it)
                                        }

                                        PhoneTextField(
                                            text = viewModel.phone.text,
                                            error = viewModel.phone.error
                                        ) {
                                            viewModel.updatePhone(it)
                                        }

                                    }
                                    PhoneSupportingText()
                                }

                                LoginButton(viewModel.enableLoginButton) {
                                    keyboardController?.hide()
                                    viewModel.tryContinueAuth()
                                }
                                LoginDivider()
                                MailButton { }

                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                                ) {
                                    GoogleButton(Modifier.weight(1f)) { }
                                    FacebookButton(Modifier.weight(1f)) { }
                                }
                            }
                        }

                        if (viewModel.showCountrySelector) {
                            CountrySelector(
                                onDissmiss = {
                                    viewModel.updateSelectorVisibility(false)
                                },
                                onCountrySelected = {
                                    viewModel.updateCountry(it)
                                    viewModel.updateSelectorVisibility(false)
                                }
                            )
                        }

                    }
                }

            }

        }

    }

}

/* ***************************************** PREVIEW ******************************************** */

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun LoginScreenPreview() {
    RentMeTheme { LoginScreen({}, {}, {}) }
}