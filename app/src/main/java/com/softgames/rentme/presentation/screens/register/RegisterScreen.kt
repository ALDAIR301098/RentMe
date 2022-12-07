@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.softgames.rentme.presentation.screens.register

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.softgames.rentme.presentation.screens.register.composables.*
import com.softgames.rentme.presentation.theme.RentMeTheme
import com.softgames.rentme.presentation.util.FileUtil

@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel = viewModel(),
) {

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    var hasImage by remember { mutableStateOf(false) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current
    //var cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)
    //var requestOpenCamera by remember { mutableStateOf(false) }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        hasImage = success
    }

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

            PhotoUser(hasImage, imageUri) {
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
                    val uri = FileUtil.getImageUri(context)
                    imageUri = uri
                    cameraLauncher.launch(uri)

                    /*cameraPermissionState.launchPermissionRequest()
                    requestOpenCamera = true*/
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


    }
}


@Preview(showBackground = true)
@Composable
private fun RegisterScreenPreview() {
    RentMeTheme {
        RegisterScreen()
    }
}