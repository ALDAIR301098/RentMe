@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class,
    ExperimentalMaterial3Api::class)

package com.softgames.rentme.presentation.screens.home.guest_home

import android.annotation.SuppressLint
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.softgames.rentme.domain.model.toGuest
import com.softgames.rentme.presentation.theme.RentMeTheme
import com.softgames.rentme.services.AuthService

@Composable
fun GuestHomeScreen(
    viewModel: GuestHomeViewModel = viewModel(),
    navigateHouseDetailScreen: (String) -> Unit,
) {

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    LaunchedEffect(Unit) {
        val userId = AuthService.getCurrentUser()?.uid!!
        val user = AuthService.getUserInfo(userId).toGuest()
        viewModel.setUser(user)
    }

    Scaffold(
        topBar = {
            HomeAppBar(
                user = viewModel.currentUser,
                txtSearch = viewModel.txtSearch,
                onQueryChange = {
                    viewModel.updateTxtSearch(it)
                    viewModel.filterHousesList()
                },
                scrollBehavior = scrollBehavior,
                onSearchPressed = { }
            )
        },
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection)
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            item { Spacer(Modifier.height(8.dp)) }

            items(viewModel.filterHousesList) { house ->
                HouseItem(house) {
                    navigateHouseDetailScreen(it.id)
                }
            }

            item { Spacer(Modifier.height(8.dp)) }
        }
    }
}

@SuppressLint("RestrictedApi")
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun GuestHomeScreenPreview() {
    RentMeTheme {
        GuestHomeScreen(
            navigateHouseDetailScreen = {}
        )
    }
}