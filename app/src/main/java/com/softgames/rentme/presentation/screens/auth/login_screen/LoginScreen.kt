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
import com.softgames.rentme.domain.model.ScreenState
import com.softgames.rentme.domain.model.ScreenState.FINISHED
import com.softgames.rentme.domain.model.ScreenState.WAITING
import com.softgames.rentme.presentation.screens.auth.login_screen.composables.*
import com.softgames.rentme.presentation.theme.RentMeTheme

@Composable
fun LoginScreen(
    onCloseScreen: () -> Unit,
    navigatePhoneAuthScreen: (countryCode: String, phone: String) -> Unit,
    navigateGuestHomeScreen: () -> Unit,
    navigateHostHomeScreen: () -> Unit,
    viewModel: LoginViewModel = viewModel(),
) {

    when (viewModel.screenState) {

        is FINISHED -> {
            LaunchedEffect(Unit) {
                viewModel.updateScreenState(WAITING)
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

                    if (viewModel.screenState == ScreenState.LOADING) {
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
                                country = viewModel.country,
                                error = viewModel.txfCountry.error,
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
                                    text = viewModel.txfCountryCode.text,
                                    error = viewModel.txfCountryCode.error
                                ) {
                                    viewModel.updateCountryCode(it)
                                }

                                PhoneTextField(
                                    text = viewModel.txfPhone.text,
                                    error = viewModel.txfPhone.error
                                ) {
                                    viewModel.updatePhone(it)
                                }

                            }
                            PhoneSupportingText()
                        }

                        LoginButton(viewModel.enableLoginButton) {
                            keyboardController?.hide()
                            if (viewModel.validateTextFields()) {
                                navigatePhoneAuthScreen(
                                    viewModel.txfCountryCode.text,
                                    viewModel.txfPhone.text
                                )
                            }
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

/* ***************************************** PREVIEW ******************************************** */

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun LoginScreenPreview() {
    RentMeTheme {
        LoginScreen(onCloseScreen = { /*TODO*/ },
            navigatePhoneAuthScreen = { code, phone -> },
            navigateGuestHomeScreen = { /*TODO*/ },
            navigateHostHomeScreen = { /*TODO*/ })
    }
}