@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.softgames.rentme.presentation.screens.home.register_house

import android.Manifest
import android.annotation.SuppressLint
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.app.ComponentActivity
import androidx.lifecycle.viewmodel.compose.viewModel
import com.softgames.rentme.presentation.components.others.MyIcon
import com.softgames.rentme.presentation.screens.register.composables.PhotoSelectorDialog
import com.softgames.rentme.presentation.theme.RentMeTheme
import com.softgames.rentme.presentation.util.CropImage1x1
import com.softgames.rentme.presentation.util.CropImage3x2
import com.softgames.rentme.util.createUriFromFile

@Composable
fun RegisterHouseScreen(
    activity: ComponentActivity,
    onCloseClicked: () -> Unit,
    viewModel: RegisterHouseViewModel = viewModel(),
) {

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val context = LocalContext.current

    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val imageList = remember { mutableStateListOf<Uri>() }

    var showCameraPermissionDeniedDialog by remember { mutableStateOf(false) }
    var isPhotoDialogPickerVisible by remember { mutableStateOf(false) }

    val cropImage = rememberLauncherForActivityResult(
        contract = CropImage3x2()
    ) {
        it?.let {
            imageList.add(it)
        }
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
    ) { uri ->
        uri?.let {
            val endUri = createUriFromFile(context)
            cropImage.launch(Pair(it, endUri))
        }
    }

    val imageFilesPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
    ) { uri ->
        uri?.let {
            val endUri = createUriFromFile(context)
            cropImage.launch(Pair(it, endUri))
        }
    }

    val cameraPermission = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            imageUri = createUriFromFile(context); takePhoto.launch(imageUri)
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    activity, Manifest.permission.CAMERA
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
            LargeTopAppBar(
                scrollBehavior = scrollBehavior,
                title = { Text("Registrar casa / departamento") },
                navigationIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        MyIcon(Icons.Outlined.Close)
                    }
                }
            )
        },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(
                    rememberScrollState())
        ) {
            Spacer(Modifier.height(24.dp))

            PhotoViewPager(
                imageList = imageList.toList(),
                onItemClick = { isPhotoDialogPickerVisible = true }
            )

            Spacer(Modifier.height(24.dp))

            Column(
                modifier = Modifier
                    .padding(horizontal = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {

                HouseNameTextField(
                    text = viewModel.houseName.text,
                    onTextChange = { viewModel.updateHouseName(it) },
                    error = viewModel.houseName.error
                )

                ColonyTextField(
                    text = viewModel.colony.text,
                    onTextChange = { viewModel.updateColony(it) },
                    error = viewModel.colony.error
                )

                DescriptionTextField(
                    text = viewModel.description.text,
                    onTextChange = { viewModel.updateDescription(it) },
                    error = viewModel.description.error
                )

                PriceTextField(
                    text = viewModel.price.text,
                    onTextChange = { viewModel.updatePrice(it) },
                    error = viewModel.price.error
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    GuestNumberTextField(
                        modifier = Modifier.weight(1f),
                        text = viewModel.guestNumber.text,
                        onTextChange = { viewModel.updateGuestNumbers(it) },
                        error = viewModel.guestNumber.error,
                    )
                    RoomsTextField(
                        modifier = Modifier.weight(1f),
                        text = viewModel.rooms.text,
                        onTextChange = { viewModel.updateRooms(it) },
                        error = viewModel.rooms.error
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    BeedsTextField(
                        modifier = Modifier.weight(1f),
                        text = viewModel.beds.text,
                        onTextChange = { viewModel.updateBeds(it) },
                        error = viewModel.beds.error
                    )
                    BathroomsTextField(
                        modifier = Modifier.weight(1f),
                        text = viewModel.bathrooms.text,
                        onTextChange = { viewModel.updateBathrooms(it) },
                        error = viewModel.bathrooms.error
                    )
                }

                Spacer(Modifier.height(8.dp))

                RegisterButton { }

            }
        }

        if (isPhotoDialogPickerVisible) {
            PhotoSelectorDialog(
                onCameraClicked = {
                    isPhotoDialogPickerVisible = false
                    cameraPermission.launch(Manifest.permission.CAMERA)
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

    }
}


@SuppressLint("RestrictedApi")
@Preview(showBackground = true)
@Composable
private fun RegisterHouseScreenPreview() {
    RentMeTheme {
        RegisterHouseScreen(ComponentActivity(), {})
    }
}