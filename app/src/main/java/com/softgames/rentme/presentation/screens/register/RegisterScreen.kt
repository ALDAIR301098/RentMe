@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalPermissionsApi::class)

package com.softgames.rentme.presentation.screens.register

import android.Manifest
import android.app.Activity
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.softgames.rentme.presentation.screens.register.composables.*
import com.softgames.rentme.presentation.theme.RentMeTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(
    activity: Activity,
    viewModel: RegisterViewModel = viewModel(),
) {

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    var cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)
    var requestOpenCamera by remember { mutableStateOf(false) }
    var firstCheck by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            RegisterAppBar(
                scrollBehavior = scrollBehavior,
                onCloseClicked = { }
            )
        },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Spacer(Modifier.height(16.dp))

            PhotoUser {
                viewModel.showPhotoSelectorDialog()
            }

            Spacer(Modifier)

            NameTextField(
                text = viewModel.name.text,
                onTextChange = { viewModel.updateName(it) }
            )

            LastNameTextField(
                text = viewModel.lastName.text,
                onTextChange = { viewModel.updateLastName(it) }
            )

            GenderDropDownMenu { viewModel.updateGender(it) }


            BirthDateTextField(
                text = viewModel.birthDate.text,
                onTextChange = { viewModel.updateBirthDate(it) },
                onDatePickerClicked = { viewModel.showDateDialogPicker() }
            )

            RegisterButton { }

        }

        if (viewModel.isPhotoDialogPickerVisible) {
            PhotoSelectorDialog(
                onCameraClicked = {
                    viewModel.hidePhotoSelectorDialog()
                    cameraPermissionState.launchPermissionRequest()
                    requestOpenCamera = true
                    firstCheck = false
                },
                onGalleryClicked = {
                    viewModel.hidePhotoSelectorDialog()
                },
                onFiledClicked = {
                    viewModel.hidePhotoSelectorDialog()
                },
                onDissmiss = {
                    viewModel.hidePhotoSelectorDialog()
                },
            )
        }

        if (viewModel.isDateDialogPickerVisible) {
            DatePickerDialog(
                onDissMiss = { viewModel.hideDateDialogPicker() },
                onDateSelected = { viewModel.updateBirthDate(it); viewModel.hideDateDialogPicker() }
            )
        }


        if (requestOpenCamera) {
            when {

                cameraPermissionState.status.isGranted -> {
                    Log.d("ALDAIR", "ABRIENDO CAMARA")
                    requestOpenCamera = false
                }

                cameraPermissionState.status.shouldShowRationale -> {
                    Log.d("ALDAIR", "RELATIONALE")
                    requestOpenCamera = false
                    cameraPermissionState.launchPermissionRequest()
                }

                !cameraPermissionState.status.isGranted && !cameraPermissionState.status.shouldShowRationale -> {
                    Log.d("ALDAIR", "DENIED PERMANENTLY")
                    requestOpenCamera = false
                }

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RegisterScreenPreview() {
    RentMeTheme {
        RegisterScreen(Activity())
    }
}