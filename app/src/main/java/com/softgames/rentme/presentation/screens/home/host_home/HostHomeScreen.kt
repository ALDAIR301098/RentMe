@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.softgames.rentme.presentation.screens.home.host_home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.softgames.rentme.presentation.screens.home.guest_home.HomeAppBar
import com.softgames.rentme.presentation.theme.RentMeTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import com.softgames.rentme.domain.model.House
import com.softgames.rentme.presentation.components.others.MyIcon
import com.softgames.rentme.presentation.screens.home.guest_home.HouseItem
import com.softgames.rentme.presentation.util.showMessage
import com.softgames.rentme.services.AuthService
import kotlinx.coroutines.launch

@Composable
fun HostHomeScreen(
    viewModel: HostHomeViewModel = viewModel(),
    navigateRegisterHouseScreen: (String) -> Unit,
) {

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            HomeAppBar(
                txtSearch = viewModel.txtSearch,
                onQueryChange = { viewModel.updateTxtSearch(it) },
                scrollBehavior = scrollBehavior,
                onSearchPressed = { showMessage(context, viewModel.txtSearch) }
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

            item { Spacer(Modifier) }

            val house = House(
                name = "Casa del arbol",
                rating = 4.6f,
                timesRated = 10,
                colony = "Fluvial Vallarta",
                price = 7500f
            )

            items(10) {
                HouseItem(house)
            }

            item { Spacer(Modifier) }
        }

    }

}

@Preview(showBackground = true)
@Composable
private fun HostHomeScreenPreview() {
    RentMeTheme {
        HostHomeScreen() {}
    }
}