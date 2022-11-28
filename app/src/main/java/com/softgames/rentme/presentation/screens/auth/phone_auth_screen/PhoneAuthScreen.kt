@file:OptIn(ExperimentalMaterial3Api::class)

package com.softgames.rentme.presentation.screens.auth.phone_auth_screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.softgames.rentme.domain.model.ScreenState
import com.softgames.rentme.presentation.screens.auth.AuthViewModel
import com.softgames.rentme.presentation.screens.auth.phone_auth_screen.composables.*
import com.softgames.rentme.presentation.theme.RentMeTheme

@Composable
fun PhoneAuthScreen(
    authViewModel: AuthViewModel = viewModel(),
    viewModel: PhoneAuthViewModel = viewModel(),
    showRegisterScreen: () -> Unit,
    onBackPressed: () -> Unit,
) {
    when (viewModel.screenState) {

        is ScreenState.LOADING -> {
            LaunchedEffect(Unit) {
                viewModel.updateScreenState(ScreenState.WAITING)
                showRegisterScreen()
            }
        }

        else -> {

            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = { PhoneAuthAppBar(onBackPressed) }
            ) { paddingValues ->

                Column(
                    modifier = Modifier
                        .padding(paddingValues)
                        .padding(horizontal = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {

                    SmsCodeImage()

                    PhoneMessageCard(
                        phoneNumber = "+${authViewModel.phoneAuthData.countryCode} " +
                                authViewModel.phoneAuthData.phoneNumber,
                        changePhoneNumber = onBackPressed
                    )

                    Spacer(Modifier)


                    Column {

                        OtpCodeBoxes(
                            code = viewModel.code.text,
                            onCodeChange = { viewModel.updateCode(it) }
                        )

                        viewModel.code.error?.let {
                            Spacer(Modifier.height(6.dp)); ErrorText(it)
                        }
                    }

                    Spacer(Modifier)

                    VerifyPhoneButton(
                        onClick = {
                            viewModel.tryContunueAuth()
                        }
                    )

                    ResendCodeButton(
                        onClick = { authViewModel.sendVerificationCode() },
                        enabled = (viewModel.resendTime == 0),
                        resendTime = viewModel.resendTime
                    )

                }

            }

        }

    }
}

@Preview(showBackground = true)
@Composable
private fun PhoneAuthScreenPreview() {
    RentMeTheme {
        PhoneAuthScreen(showRegisterScreen = {}) { }
    }
}