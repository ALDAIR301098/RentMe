@file:OptIn(
    ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
)

package com.softgames.rentme.presentation.screens.register

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.util.Log
import androidx.activity.ComponentActivity
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
import androidx.core.app.ActivityCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.softgames.rentme.presentation.screens.register.composables.*
import com.softgames.rentme.presentation.theme.RentMeTheme
import com.softgames.rentme.presentation.util.CropImage

@Composable
fun RegisterScreen(
    activity: ComponentActivity,
    viewModel: RegisterViewModel = viewModel(),
) {

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    var hasImage by remember { mutableStateOf(false) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current

    var showCameraPermissionDeniedDialog by remember { mutableStateOf(false) }
    var isPhotoDialogPickerVisible by remember { mutableStateOf(false) }
    var isDateDialogPickerVisible by remember { mutableStateOf(false) }

    val cropImage = rememberLauncherForActivityResult(
        contract = CropImage()
    ) {
        hasImage = it != null
        imageUri = it
    }

    val takePhoto = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            cropImage.launch(Pair(imageUri!!, imageUri!!))
        }
    }

    val galleryImagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
    ) {
        if (it != null) {
            val endUri = viewModel.createUriFromFile(context)
            cropImage.launch(Pair(it, endUri))
        }
    }

    val imageFilesPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
    ) {
        if (it != null) {
            val endUri = viewModel.createUriFromFile(context)
            cropImage.launch(Pair(it, endUri))
        }
    }

    val cameraPermission = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            hasImage = false; imageUri = viewModel.createUriFromFile(context); takePhoto.launch(
                imageUri
            )
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    activity, android.Manifest.permission.CAMERA
                )
            ) {
                //SHOW MESSAGE RELATIONALE FOR WHY REQUEST CAMERA PERMISSION
            } else {
                showCameraPermissionDeniedDialog = true
            }
        }
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
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {

            Spacer(Modifier.height(24.dp))

            PhotoUser(hasImage, imageUri) {
                isPhotoDialogPickerVisible = true
            }

            Spacer(Modifier.height(16.dp))

            NameTextField(
                text = viewModel.name.text,
                error = viewModel.name.error,
                onTextChange = { viewModel.updateName(it) }
            )

            LastNameTextField(
                text = viewModel.lastName.text,
                error = viewModel.lastName.error,
                onTextChange = { viewModel.updateLastName(it) }
            )

            GenderDropDownMenu(
                error = viewModel.gender.error,
                onGenderChange = {
                    Log.d("Aldair", it)
                    viewModel.updateGender(it)
                }
            )


            BirthDateTextField(
                text = viewModel.birthDate.text,
                error = viewModel.birthDate.error,
                onTextChange = {
                    viewModel.updateBirthDate(it)
                },
                onDatePickerClicked = { isDateDialogPickerVisible = true }
            )

            UserTypeSelector(
                userSelected = viewModel.userType,
                onUserTypeChange = { viewModel.updateUserType(it) }
            )

            RegisterButton {
                Log.d("ALDAIR", viewModel.birthDate.text.length.toString())
                viewModel.tryContinueLogin()
            }

            Spacer(Modifier.height(12.dp))

        }

        if (isPhotoDialogPickerVisible) {
            PhotoSelectorDialog(
                onCameraClicked = {
                    isPhotoDialogPickerVisible = false
                    cameraPermission.launch(android.Manifest.permission.CAMERA)
                },
                onGalleryClicked = {
                    isPhotoDialogPickerVisible = false
                    galleryImagePicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                },
                onFileClicked = {
                    isPhotoDialogPickerVisible = false
                    imageFilesPicker.launch("image/*")
                },
                onDissmiss = {
                    isPhotoDialogPickerVisible = false
                },
            )
        }

        if (isDateDialogPickerVisible) {
            DatePickerDialog(
                onDissMiss = { isDateDialogPickerVisible = false },
                onDateSelected = {
                    viewModel.updateBirthDate(it); isDateDialogPickerVisible = false
                }
            )
        }

        if (showCameraPermissionDeniedDialog) {
            CameraPermissionDeniedDialog(
                context = context,
                onDissmiss = { showCameraPermissionDeniedDialog = false },
                onConfirmClicked = {
                    showCameraPermissionDeniedDialog = false
                    launchAppSettingActivity(context)
                }
            )
        }

    }
}

private fun launchAppSettingActivity(context: Context) {
    context.startActivity(
        Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.fromParts("package", context.packageName, null)
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun RegisterScreenPreview() {
    RentMeTheme {
        RegisterScreen(ComponentActivity())
    }
}