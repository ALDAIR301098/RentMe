package com.softgames.rentme.presentation.screens.home.house_detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Bed
import androidx.compose.material.icons.outlined.Groups
import androidx.compose.material.icons.outlined.MeetingRoom
import androidx.compose.material.icons.outlined.Room
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.softgames.rentme.domain.model.HouseFeature
import com.softgames.rentme.presentation.theme.RentMeTheme

@Composable
fun HouseDetailScreen() {

    ConstraintLayout {

        val (content, priceBar) = createRefs()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .statusBarsPadding()
                .navigationBarsPadding()
                .constrainAs(content) {
                    top.linkTo(parent.top); bottom.linkTo(priceBar.top)
                },
        ) {

            ImagesViewPager(imageList = emptyList())

            Spacer(modifier = Modifier.height(20.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                HouseName(name = "Casa deo arbol")

                HouseDetails(rating = 4.9f, timesRated = 11, colony = "Lopez Mateos")

                Divider(Modifier.fillMaxWidth())

                HostRow(name = "Jose Alfredo", photo = null)

                Divider(Modifier.fillMaxWidth())

                HouseDescription(description = "Es una casa muy bonita, cercas del mar" +
                        "es color azul, muy tranquilo la zona, muy bien ubicada.")

                Divider(Modifier.fillMaxWidth())

            }

            Spacer(modifier = Modifier.height(16.dp))

            HouseFeaturesTitle()

            Spacer(modifier = Modifier.height(16.dp))

            HouseFeaturesItems(
                listOf(
                HouseFeature("Baños", quantity = 3),
                HouseFeature("Camas", quantity = 2, icon = Icons.Outlined.Bed),
                HouseFeature("Cuartos", quantity = 4, icon = Icons.Outlined.MeetingRoom),
                HouseFeature("Personas", quantity = 4, icon = Icons.Outlined.Groups)
            ))

            Spacer(modifier = Modifier.height(48.dp))

        }

        PriceBar(
            modifier = Modifier.constrainAs(priceBar) { bottom.linkTo(parent.bottom) },
            price = 5750f
        )

    }

}

@Preview(showBackground = true, device = Devices.PIXEL_4_XL)
@Composable
private fun HouseDetailScreenPreview() {
    RentMeTheme {
        HouseDetailScreen()
    }
}