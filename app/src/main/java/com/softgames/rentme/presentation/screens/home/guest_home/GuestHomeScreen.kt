@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)

package com.softgames.rentme.presentation.screens.home.guest_home

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.softgames.rentme.domain.model.House
import com.softgames.rentme.domain.model.RentMeUser
import com.softgames.rentme.presentation.theme.RentMeTheme

@Composable
fun GuestHomeScreen(
    viewModel: GuestHomeViewModel = viewModel()
) {
    Scaffold(
        topBar = {
            GuestHomeAppBar(
                txtSearch = viewModel.txtSearch,
                onQueryChange = { viewModel.updateTxtSearch(it) }
            )
        }
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            item { Spacer(Modifier) }

            val house = House(
                name = "Casa del arbol",
                rating = 4.6f,
                timesRated = 10,
                colony = "Fluvial Vallarta",
                price = 7500f
            )

            items(10){
                HouseItem(house)
            }
        }
    }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun GuestHomeScreenPreview() {
    RentMeTheme {
        GuestHomeScreen()
    }
}