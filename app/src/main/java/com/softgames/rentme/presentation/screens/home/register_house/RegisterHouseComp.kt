@file:OptIn(ExperimentalComposeUiApi::class)

package com.softgames.rentme.presentation.screens.home.register_house

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.softgames.rentme.presentation.components.buttons.MyButton
import com.softgames.rentme.presentation.components.others.MyIcon
import com.softgames.rentme.presentation.components.textfields.MyOutlinedTextField

@Composable
fun HouseNameTextField(
    text: String,
    onTextChange: (String) -> Unit,
    error: String?,
) {
    MyOutlinedTextField(
        text = text,
        onTextChange = onTextChange,
        error = error,
        label = { Text("Nombre de la casa") },
        supportingText = { error?.let { Text(text = it) } },
        leadingIcon = { MyIcon(Icons.Outlined.Cottage) }
    )
}

@Composable
fun ColonyTextField(
    text: String,
    onTextChange: (String) -> Unit,
    error: String?,
) {
    MyOutlinedTextField(
        text = text,
        onTextChange = onTextChange,
        error = error,
        label = { Text("Colonia") },
        supportingText = { error?.let { Text(text = it) } },
        leadingIcon = { MyIcon(Icons.Outlined.LocationOn) }
    )
}

@Composable
fun DescriptionTextField(
    text: String,
    onTextChange: (String) -> Unit,
    error: String?,
) {
    MyOutlinedTextField(
        text = text,
        onTextChange = onTextChange,
        error = error,
        label = { Text("Descripcción") },
        supportingText = { error?.let { Text(text = it) } },
        leadingIcon = { MyIcon(Icons.Outlined.Description) },
        keyboardOptions = KeyboardOptions.Default.copy(
            capitalization = KeyboardCapitalization.None,
            imeAction = ImeAction.None
        ),
        keyboardActions = KeyboardActions(

        ),
        singleLine = false,
        maxLines = 30
    )
}

@Composable
fun PriceTextField(
    text: String,
    onTextChange: (String) -> Unit,
    error: String?,
) {
    MyOutlinedTextField(
        text = text,
        onTextChange = onTextChange,
        error = error,
        label = { Text("Precio") },
        supportingText = { error?.let { Text(text = it) } },
        leadingIcon = { MyIcon(Icons.Outlined.AttachMoney) },
        trailingIcon = { Text(text = "Mxn", modifier = Modifier.padding(end = 12.dp)) },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number
        )
    )
}

@Composable
fun GuestNumberTextField(
    modifier: Modifier,
    text: String,
    onTextChange: (String) -> Unit,
    error: String?,
) {
    MyOutlinedTextField(
        text = text,
        onTextChange = onTextChange,
        modifier = modifier,
        error = error,
        label = { Text(text = "Personas", maxLines = 1, overflow = TextOverflow.Ellipsis) },
        supportingText = { error?.let { Text(text = it) } },
        leadingIcon = { MyIcon(Icons.Outlined.Groups) },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number
        )
    )
}

@Composable
fun RoomsTextField(
    modifier: Modifier,
    text: String,
    onTextChange: (String) -> Unit,
    error: String?,
) {
    MyOutlinedTextField(
        text = text,
        onTextChange = onTextChange,
        error = error,
        modifier = modifier,
        label = { Text("Cuartos") },
        supportingText = { error?.let { Text(text = it) } },
        leadingIcon = { MyIcon(Icons.Outlined.MeetingRoom) },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number
        )
    )
}

@Composable
fun BeedsTextField(
    modifier: Modifier,
    text: String,
    onTextChange: (String) -> Unit,
    error: String?,
) {
    MyOutlinedTextField(
        text = text,
        onTextChange = onTextChange,
        error = error,
        modifier = modifier,
        label = { Text("Camas") },
        supportingText = { error?.let { Text(text = it) } },
        leadingIcon = { MyIcon(Icons.Outlined.Bed) },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number
        ),
        singleLine = true
    )
}

@Composable
fun BathroomsTextField(
    modifier: Modifier,
    text: String,
    onTextChange: (String) -> Unit,
    error: String?,
) {
    MyOutlinedTextField(
        text = text,
        onTextChange = onTextChange,
        error = error,
        modifier = modifier,
        label = { Text("Baños") },
        supportingText = { error?.let { Text(text = it) } },
        leadingIcon = { MyIcon(Icons.Outlined.Bathroom) },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number
        ),
        singleLine = true

    )
}

@Composable
fun RegisterButton(
    onClick: () -> Unit,
) {
    MyButton(onClick) {
        Text("Finalizar registro")
    }
}