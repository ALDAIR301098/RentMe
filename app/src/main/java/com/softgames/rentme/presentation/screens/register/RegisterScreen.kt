@file:OptIn(
    ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalPermissionsApi::class
)

package com.softgames.rentme.presentation.screens.register

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
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
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.softgames.rentme.Manifest
import com.softgames.rentme.presentation.screens.register.composables.*
import com.softgames.rentme.presentation.theme.RentMeTheme

@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel = viewModel(),
) {

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    var hasImage by remember { mutableStateOf(false) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current

    var requestOpenCamera by remember { mutableStateOf(false) }

    val cameraPermission = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            if (requestOpenCamera) {

            } else {
                if (requestOpenCamera) {

                }
            }
        }
    }

    val imageFilesPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
    ) { uri ->
        Log.d("ALDAIR", "PATH FROM FILE: $uri")
        hasImage = uri != null
        imageUri = uri
    }

    val galleryImagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
    ) {
        Log.d("ALDAIR", "PATH FROM GALLERY: $it")
        hasImage = it != null
        imageUri = it
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
                    requestOpenCamera = true
                    cameraPermission.launch(android.Manifest.permission.CAMERA)
                },
                onGalleryClicked = {
                    viewModel.hidePhotoSelectorDialog()
                    galleryImagePicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                },
                onFileClicked = {
                    viewModel.hidePhotoSelectorDialog()
                    imageFilesPicker.launch("image/*")
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

fun checkCameraPermission(context: Context){
    when{

        ContextCompat.checkSelfPermission(
            context,
            android.Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED -> {

        }

        Activity().shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA) ->{

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