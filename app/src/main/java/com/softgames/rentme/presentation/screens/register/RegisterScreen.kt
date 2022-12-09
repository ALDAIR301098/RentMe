@file:OptIn(
    ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
)

package com.softgames.rentme.presentation.screens.register

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
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
import com.softgames.rentme.domain.model.ScreenState.*
import com.softgames.rentme.presentation.screens.register.composables.*
import com.softgames.rentme.presentation.theme.RentMeTheme
import com.softgames.rentme.presentation.util.CropImage1x1
import com.softgames.rentme.presentation.util.showMessage
import com.softgames.rentme.util.createUriFromFile

@Composable
fun RegisterScreen(
    userId: String,
    activity: ComponentActivity,
    viewModel: RegisterViewModel = viewModel(),
    navigateHostHomeScreen: () -> Unit,
    navigateGuestHomeScreen: () -> Unit,
    onCloseScreen: () -> Unit,
) {

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val context = LocalContext.current

    var hasImage by remember { mutableStateOf(false) }
    var showCameraPermissionDeniedDialog by remember { mutableStateOf(false) }
    var isPhotoDialogPickerVisible by remember { mutableStateOf(false) }
    var isDateDialogPickerVisible by remember { mutableStateOf(false) }

    val cropImage = rememberLauncherForActivityResult(
        contract = CropImage1x1()
    ) {
        it?.let {
            hasImage = true
            viewModel.updateImageUri(it)
        }
    }

    val takePhoto = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            cropImage.launch(Pair(viewModel.imageUri!!, viewModel.imageUri!!))
        }
    }

    val galleryImagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
    ) {
        if (it != null) {
            val endUri = createUriFromFile(context)
            cropImage.launch(Pair(it, endUri))
        }
    }

    val imageFilesPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
    ) {
        if (it != null) {
            val endUri = createUriFromFile(context)
            cropImage.launch(Pair(it, endUri))
        }
    }

    val cameraPermission = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            hasImage = false
            viewModel.updateImageUri(createUriFromFile(context));
            takePhoto.launch(viewModel.imageUri)
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

    LaunchedEffect(Unit) {
        viewModel.updateUserId(userId)
        showMessage(context, userId)
    }

    when (viewModel.screenState) {
        is FINISHED -> {
            LaunchedEffect(Unit) {
                viewModel.updateScreenState(WAITING)
                when (viewModel.user.type) {
                    "Host" -> navigateHostHomeScreen()
                    "Guest" -> navigateGuestHomeScreen()
                }
            }
        }
        else -> {

            Scaffold(
                topBar = {
                    RegisterAppBar(
                        scrollBehavior = scrollBehavior,
                        onCloseClicked = onCloseScreen
                    )
                },
                modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
            ) { paddingValues ->

                Column(Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                ) {

                    if (viewModel.screenState == LOADING) {
                        LinearProgressIndicator(Modifier.fillMaxWidth())
                        Spacer(Modifier.height(16.dp))
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(horizontal = 20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {

                        Spacer(Modifier.height(24.dp))

                        PhotoUser(hasImage, viewModel.imageUri) {
                            isPhotoDialogPickerVisible = true
                        }

                        Spacer(Modifier.height(16.dp))

                        NameTextField(
                            text = viewModel.txfName.text,
                            error = viewModel.txfName.error,
                            onTextChange = { viewModel.updateName(it) }
                        )

                        LastNameTextField(
                            text = viewModel.txfLastName.text,
                            error = viewModel.txfLastName.error,
                            onTextChange = { viewModel.updateLastName(it) }
                        )

                        GenderDropDownMenu(
                            error = viewModel.txfGender.error,
                            onGenderChange = {
                                viewModel.updateGender(it)
                            }
                        )


                        BirthDateTextField(
                            text = viewModel.txfBirthDate.text,
                            error = viewModel.txfBirthDate.error,
                            onTextChange = {
                                viewModel.updateBirthDate(it)
                            },
                            onDatePickerClicked = { isDateDialogPickerVisible = true }
                        )

                        Spacer(Modifier)

                        UserTypeSelector(
                            userSelected = viewModel.user,
                            onUserTypeChange = { viewModel.updateUserType(it) }
                        )

                        Spacer(Modifier)

                        RegisterButton {
                            viewModel.tryRegisterUser(
                                onSuccess = {
                                    showMessage(context, "¡Registro exitoso!")
                                }
                            )
                        }

                        Spacer(Modifier.height(16.dp))

                    }

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
    }
}

private fun launchAppSettingActivity(context: Context) {
    context.startActivity(
        Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.fromParts("package", context.packageName, null)
        }
    )
}

@Preview(showBackground = true, heightDp = 1000)
@Composable
private fun RegisterScreenPreview() {
    RentMeTheme {
        RegisterScreen(
            userId = "",
            activity = ComponentActivity(),
            navigateHostHomeScreen = {},
            navigateGuestHomeScreen = {},
            onCloseScreen = {}
        )
    }
}