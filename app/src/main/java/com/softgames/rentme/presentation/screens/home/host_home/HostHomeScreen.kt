@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class)

package com.softgames.rentme.presentation.screens.home.host_home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.softgames.rentme.domain.model.House
import com.softgames.rentme.domain.model.toGuest
import com.softgames.rentme.presentation.components.others.MyIcon
import com.softgames.rentme.presentation.screens.home.guest_home.HomeAppBar
import com.softgames.rentme.presentation.screens.home.guest_home.HouseItem
import com.softgames.rentme.presentation.theme.RentMeTheme
import com.softgames.rentme.presentation.util.showMessage
import com.softgames.rentme.services.AuthService
import kotlinx.coroutines.launch

@Composable
fun HostHomeScreen(
    viewModel: HostHomeViewModel = viewModel(),
    navigateRegisterHouseScreen: (String) -> Unit,
    navigateHouseDetailScreen: (String) -> Unit,
) {

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            HomeAppBar(
                user = viewModel.user,
                txtSearch = viewModel.txtSearch,
                onQueryChange = {
                    viewModel.updateTxtSearch(it)
                    viewModel.filterHousesList()
                },
                scrollBehavior = scrollBehavior,
                onSearchPressed = { }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    scope.launch {
                        val userId = AuthService.getCurrentUser()?.uid ?: "Pepito"
                        navigateRegisterHouseScreen(userId)
                    }
                }
            ) {
                MyIcon(Icons.Outlined.Add)
                Spacer(Modifier.width(8.dp))
                Text("Agregar")
            }
        },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
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

@Preview(showBackground = true)
@Composable
private fun HostHomeScreenPreview() {
    RentMeTheme {
        HostHomeScreen(
            navigateRegisterHouseScreen = {},
            navigateHouseDetailScreen = {}
        )
    }
}