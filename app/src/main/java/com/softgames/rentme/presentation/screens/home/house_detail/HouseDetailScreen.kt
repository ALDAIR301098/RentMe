package com.softgames.rentme.presentation.screens.home.house_detail

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Bed
import androidx.compose.material.icons.outlined.Groups
import androidx.compose.material.icons.outlined.MeetingRoom
import androidx.compose.material3.Divider
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.content.ContextCompat.startActivity
import com.softgames.rentme.data.repository.HousesRepo
import com.softgames.rentme.domain.model.HouseFeature
import com.softgames.rentme.presentation.components.shared.MyGoogleMap
import com.softgames.rentme.presentation.components.shared.UserProfilePictureDialog
import com.softgames.rentme.presentation.theme.RentMeTheme
import kotlinx.coroutines.launch

@Composable
fun HouseDetailScreen(
    houseId: String,
    viewModel: HouseDetailViewModel = HouseDetailViewModel(),
) {

    val scope = rememberCoroutineScope()
    var isMapCameraMoving by remember { mutableStateOf(false) }
    var showUserProfilePictureDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        scope.launch {
            val house = HousesRepo.getCurrentHouse(houseId)
            viewModel.updateHouse(house)
        }
    }

    ConstraintLayout {

        val (content, priceBar) = createRefs()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState(), !isMapCameraMoving)
                .statusBarsPadding()
                .navigationBarsPadding()
                .constrainAs(content) {
                    top.linkTo(parent.top); bottom.linkTo(priceBar.top)
                },
        ) {

            ImagesViewPager(viewModel.house!!.photoList)

            Spacer(modifier = Modifier.height(20.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                HouseName(name = viewModel.house!!.name)

                HouseDetails(
                    rating = viewModel.house!!.rating,
                    timesRated = viewModel.house!!.timesRated,
                    colony = viewModel.house!!.colony
                )

                Divider(Modifier.fillMaxWidth())

                HostRow(
                    name = viewModel.house!!.hostName,
                    photo = viewModel.house!!.hostPhoto,
                    onPhotoClicked = { showUserProfilePictureDialog = true }
                )

                Divider(Modifier.fillMaxWidth())

                HouseDescriptionTitle()

                HouseDescription(description = viewModel.house!!.description)

                Divider(Modifier.fillMaxWidth())

            }

            Spacer(modifier = Modifier.height(16.dp))

            HouseFeaturesTitle()

            Spacer(modifier = Modifier.height(16.dp))

            HouseFeaturesItems(
                listOf(
                    HouseFeature(name = "Baños", quantity = viewModel.house!!.bathrooms),
                    HouseFeature("Camas",
                        quantity = viewModel.house!!.beds,
                        icon = Icons.Outlined.Bed),
                    HouseFeature("Cuartos",
                        quantity = viewModel.house!!.rooms,
                        icon = Icons.Outlined.MeetingRoom),
                    HouseFeature("Personas",
                        quantity = viewModel.house!!.guestNumber,
                        icon = Icons.Outlined.Groups)
                ))

            Spacer(modifier = Modifier.height(16.dp))

            Divider(Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp))

            Spacer(modifier = Modifier.height(16.dp))

            HouseMapTitle()

            Spacer(modifier = Modifier.height(16.dp))

            MyGoogleMap(
                location = viewModel.house!!.location,
                onMapCameraMove = { isMapCameraMoving = it },
                onNavigateClicked = {
                    val gmmIntentUri =
                        Uri.parse("google.navigation:q=${viewModel.house!!.location.latitude},${viewModel.house!!.location.longitude}")
                    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                    mapIntent.setPackage("com.google.android.apps.maps")
                    startActivity(context, mapIntent, null)
                }
            )

            Spacer(modifier = Modifier.height(64.dp))

        }

        PriceBar(
            modifier = Modifier
                .navigationBarsPadding()
                .constrainAs(priceBar) { bottom.linkTo(parent.bottom) },
            price = viewModel.house!!.price
        )

        if (showUserProfilePictureDialog) {
            UserProfilePictureDialog(
                photoUri = viewModel.house!!.hostPhoto,
                userName = viewModel.house!!.hostName,
                onDismiss = { showUserProfilePictureDialog = false }
            )
        }

    }

}

@Preview(showBackground = true, device = Devices.PIXEL_4_XL, heightDp = 1250)
@Composable
private fun HouseDetailScreenPreview() {
    RentMeTheme {
        HouseDetailScreen("")
    }
}