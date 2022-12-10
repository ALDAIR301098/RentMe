@file:OptIn(ExperimentalMaterial3Api::class)

package com.softgames.rentme.presentation.screens.auth.phone_auth_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ComponentActivity
import androidx.lifecycle.viewmodel.compose.viewModel
import com.softgames.rentme.domain.model.ScreenState.*
import com.softgames.rentme.presentation.screens.auth.phone_auth_screen.composables.*
import com.softgames.rentme.presentation.theme.RentMeTheme
import com.softgames.rentme.presentation.util.showMessage
import com.softgames.rentme.services.AuthService
import kotlinx.coroutines.launch

@Composable
fun PhoneAuthScreen(
    activity: ComponentActivity,
    phone: String,
    countryCode: String,
    viewModel: PhoneAuthViewModel = viewModel(),
    navigateRegisterScreen: (String) -> Unit,
    navigateGuestHomeScreen: () -> Unit,
    navigateHostHomeScreen: () -> Unit,
    onBackPressed: () -> Unit,
) {

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {

        viewModel.updateCountryCode(countryCode)
        viewModel.updatePhone(phone)

        if (!viewModel.codeWasSend) {
            viewModel.sendVerificationCode(
                activity = activity,
                onCodeSent = {
                    showMessage(context, "Se envío el código de verificación.")
                },
                onSignInSuccessful = {
                    showMessage(
                        context = context,
                        message = "Se inicio sesión exitosamente."
                    )
                }
            )
        }
    }

    when (viewModel.screenState) {

        is FINISHED -> {
            LaunchedEffect(Unit) {
                scope.launch {
                    val userInfo = AuthService.getUserInfo(viewModel.userId!!)
                    if (userInfo.exists()) {
                        val userType = userInfo.getString("type")!!
                        when (userType) {
                            "Guest" -> {
                                viewModel.updateScreenState(WAITING)
                                navigateGuestHomeScreen()
                            }
                            "Host" -> {
                                viewModel.updateScreenState(WAITING)
                                navigateHostHomeScreen()
                            }
                        }

                    } else {
                        viewModel.updateScreenState(WAITING)
                        navigateRegisterScreen(viewModel.userId!!)
                    }
                }
            }
        }

        else -> {

            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = { PhoneAuthAppBar(onBackPressed) }
            ) { paddingValues ->

                Column(
                    Modifier.padding(paddingValues)
                ) {
                    if (viewModel.screenState is LOADING) {
                        LinearProgressIndicator(Modifier.fillMaxWidth())
                        Spacer(Modifier.height(16.dp))
                    }

                    Column(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {

                        SmsCodeImage()

                        PhoneMessageCard(
                            phoneNumber = "+${viewModel.countryCode} " + viewModel.phone,
                            onChangeClicked = onBackPressed
                        )

                        Spacer(Modifier)

                        Column {

                            OtpCodeBoxes(
                                code = viewModel.txfSmsCode.text,
                                onCodeChange = { viewModel.updateSmsCode(it) }
                            )

                            viewModel.txfSmsCode.error?.let {
                                Spacer(Modifier.height(6.dp)); ErrorText(it)
                            }

                        }

                        Spacer(Modifier)

                        VerifyPhoneButton(
                            enabled = viewModel.codeWasSend,
                            onClick = {
                                viewModel.signInRentMe(
                                    onSuccess = {
                                        showMessage(
                                            context = context,
                                            message = "Se inicio sesión exitosamente."
                                        )
                                    }
                                )
                            }
                        )

                        ResendCodeButton(
                            onClick = { },
                            enabled = (viewModel.resendTime == 0),
                            resendTime = viewModel.resendTime
                        )

                    }

                }

            }

        }
    }
}

@SuppressLint("RestrictedApi")
@Preview(showBackground = true)
@Composable
private fun PhoneAuthScreenPreview() {
    RentMeTheme {
        PhoneAuthScreen(
            activity = ComponentActivity(),
            countryCode = "52",
            phone = "3221827267",
            navigateRegisterScreen = {},
            navigateGuestHomeScreen = {},
            navigateHostHomeScreen = {},
            onBackPressed = {}
        )
    }
}