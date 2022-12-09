@file:OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)

package com.softgames.rentme.presentation.screens.auth.login_screen.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Public
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.softgames.rentme.data.model.Country
import com.softgames.rentme.presentation.components.others.MyIcon
import com.softgames.rentme.presentation.components.others.MyImage
import com.softgames.rentme.presentation.components.textfields.MyTextField
import com.softgames.rentme.presentation.screens.auth.login_screen.LoginViewModel
import com.softgames.rentme.presentation.theme.RentMeTheme

@Composable
fun CountrySelector(
    viewModel: LoginViewModel = viewModel(),
    onCountrySelected: (Country) -> Unit,
    onDissmiss: () -> Unit,
) {

    var txtSearch by remember { mutableStateOf("") }
    val countries = viewModel.filterCountries(txtSearch)

    //SCREEN ***************************************************************************************

    Dialog(
        onDismissRequest = onDissmiss
    ) {
        Surface(
            shape = AlertDialogDefaults.shape,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(.85f),
            color = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp),
            tonalElevation = 3.dp
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {

                TitleText()
                SearchTextField(
                    txtSearch = txtSearch,
                    onTextChange = { txtSearch = it }
                )

                CountryList(countries) { onCountrySelected(it) }

            }
        }
    }

}

//COMPOSABLES **************************************************************************************
@Composable
private fun TitleText() {
    Text(
        text = "Seleccionar país",
        style = MaterialTheme.typography.headlineSmall,
        modifier = Modifier
            .padding(start = 20.dp, top = 20.dp, end = 20.dp)
            .fillMaxWidth()
    )
}

@Composable
private fun SearchTextField(
    txtSearch: String,
    onTextChange: (String) -> Unit,
) {

    val keyboard = LocalSoftwareKeyboardController.current

    MyTextField(
        text = txtSearch,
        onTextChange = onTextChange,
        modifier = Modifier.padding(horizontal = 20.dp),
        shape = RoundedCornerShape(100),
        leadingIcon = { MyIcon(imageVector = Icons.Default.Public) },
        placeholder = { Text("Buscar") },
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.White,
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent
        ),
        keyboardOptions = KeyboardOptions.Default.copy(
            capitalization = KeyboardCapitalization.Words,
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = { keyboard?.hide() }
        ),
        singleLine = true
    )

}

@Composable
private fun CountryList(
    countries: List<Country>,
    onCountrySelected: (Country) -> Unit,
) {
    LazyColumn(Modifier.fillMaxWidth()) {
        items(countries) { country ->
            CountryListItem(
                country = country,
                onClick = onCountrySelected
            )
        }
    }
}

@Composable
private fun CountryListItem(
    country: Country,
    onClick: (Country) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(country) }
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        MyImage(
            drawable = country.flag,
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = country.name,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp)
        )
        Text("+${country.code}")
    }
}

@Preview(showBackground = true)
@Composable
private fun CountrySelectorPreview() {
    RentMeTheme {
        CountrySelector(onCountrySelected = {}) {

        }
    }
}