@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class)

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
import com.softgames.rentme.domain.model.ScreenState.*
import com.softgames.rentme.domain.model.toHost
import com.softgames.rentme.presentation.components.others.MyIcon
import com.softgames.rentme.presentation.components.shared.MyGoogleMap
import com.softgames.rentme.presentation.screens.register.composables.PhotoSelectorDialog
import com.softgames.rentme.presentation.theme.RentMeTheme
import com.softgames.rentme.presentation.util.CropImage3x2
import com.softgames.rentme.services.AuthService
import com.softgames.rentme.util.createUriFromFile
import kotlinx.coroutines.launch

@Composable
fun RegisterHouseScreen(
    userId: String,
    activity: ComponentActivity,
    onFinish: () -> Unit,
    viewModel: RegisterHouseViewModel = viewModel(),
) {

    val scope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val context = LocalContext.current

    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var isMapCameraMoving by remember { mutableStateOf(false) }

    var showCameraPermissionDeniedDialog by remember { mutableStateOf(false) }
    var isPhotoDialogPickerVisible by remember { mutableStateOf(false) }

    val cropImage = rememberLauncherForActivityResult(
        contract = CropImage3x2()
    ) {
        it?.let {
            viewModel.addImageUri(it)
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

    LaunchedEffect(Unit) {
        scope.launch {
            val user = AuthService.getUserInfo(userId).toHost()
            viewModel.updateCurrentUser(user)
        }
    }

    when (viewModel.screenState) {

        is FINISHED -> {
            LaunchedEffect(Unit) {
                viewModel.updateScreenState(WAITING)
                onFinish()
            }
        }

        else -> {

            Scaffold(
                topBar = {
                    LargeTopAppBar(
                        scrollBehavior = scrollBehavior,
                        title = { Text("Registrar casa o departamento") },
                        navigationIcon = {
                            IconButton(onClick = { /*TODO*/ }) {
                                MyIcon(Icons.Outlined.Close)
                            }
                        }
                    )
                },
                modifier = Modifier
                    .nestedScroll(scrollBehavior.nestedScrollConnection),
            ) { paddingValues ->

                Column(Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                ) {

                    if (viewModel.screenState is LOADING) {
                        LinearProgressIndicator(Modifier.fillMaxWidth())
                        Spacer(Modifier.height(16.dp))
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState(), !isMapCameraMoving)
                    ) {
                        Spacer(Modifier.height(24.dp))

                        PhotoViewPager(
                            imageList = viewModel.imageList,
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
                                text = viewModel.txfHouseName.text,
                                onTextChange = { viewModel.updateHouseName(it) },
                                error = viewModel.txfHouseName.error
                            )

                            ColonyTextField(
                                text = viewModel.txfColony.text,
                                onTextChange = { viewModel.updateColony(it) },
                                error = viewModel.txfColony.error
                            )

                            DescriptionTextField(
                                text = viewModel.txfDescription.text,
                                onTextChange = { viewModel.updateDescription(it) },
                                error = viewModel.txfDescription.error
                            )

                            PriceTextField(
                                text = viewModel.txfPrice.text,
                                onTextChange = { viewModel.updatePrice(it) },
                                error = viewModel.txfPrice.error
                            )

                            Row(
                                horizontalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                GuestNumberTextField(
                                    modifier = Modifier.weight(1f),
                                    text = viewModel.txfGuestNumber.text,
                                    onTextChange = { viewModel.updateGuestNumbers(it) },
                                    error = viewModel.txfGuestNumber.error,
                                )
                                RoomsTextField(
                                    modifier = Modifier.weight(1f),
                                    text = viewModel.txfRooms.text,
                                    onTextChange = { viewModel.updateRooms(it) },
                                    error = viewModel.txfRooms.error
                                )
                            }

                            Row(
                                horizontalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                BeedsTextField(
                                    modifier = Modifier.weight(1f),
                                    text = viewModel.txfBeds.text,
                                    onTextChange = { viewModel.updateBeds(it) },
                                    error = viewModel.txfBeds.error
                                )
                                BathroomsTextField(
                                    modifier = Modifier.weight(1f),
                                    text = viewModel.txfBathrooms.text,
                                    onTextChange = { viewModel.updateBathrooms(it) },
                                    error = viewModel.txfBathrooms.error
                                )
                            }

                            Spacer(Modifier.height(8.dp))

                            LocationTitle()

                            MyGoogleMap(
                                city = viewModel.city,
                                onPositionChange = {
                                    viewModel.updateLocation(it)
                                },
                                onMapCameraMove = { isMapCameraMoving = it }
                            )

                            Spacer(Modifier.height(16.dp))

                            RegisterButton { viewModel.registerHouse() }

                            Spacer(Modifier.height(16.dp))

                        }
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

                viewModel.registerError?.let {
                    RegisterHouseErrorDialog(
                        error = it,
                        onDissmiss = { viewModel.hideRegisterErrorDialog() }
                    )
                }

            }

        }

    }
}


@SuppressLint("RestrictedApi")
@Preview(showBackground = true, heightDp = 1500)
@Composable
private fun RegisterHouseScreenPreview() {
    RentMeTheme {
        RegisterHouseScreen("", ComponentActivity(), {})
    }
}