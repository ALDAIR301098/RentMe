@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class,
    ExperimentalMaterial3Api::class)

package com.softgames.rentme.presentation.screens.register.composables

import android.content.Context
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ReportOff
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.CameraAlt
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import coil.compose.AsyncImage
import com.softgames.rentme.R
import com.softgames.rentme.presentation.components.buttons.MyButton
import com.softgames.rentme.presentation.components.others.MyIcon
import com.softgames.rentme.presentation.components.textfields.MyOutlinedTextField
import com.softgames.rentme.presentation.util.DateTransformation
import com.softgames.rentme.util.isPersonNamesOnly
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun RegisterAppBar(
    scrollBehavior: TopAppBarScrollBehavior,
    onCloseClicked: () -> Unit,
) {
    LargeTopAppBar(
        title = {
            Text(
                text = "Registrar nuevo usuario ",
                modifier = Modifier.padding(end = 16.dp),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        },
        navigationIcon = {
            IconButton(onCloseClicked) {
                MyIcon(Icons.Default.Close)
            }
        },
        scrollBehavior = scrollBehavior
    )
}

@Composable
fun PhotoUser(
    hasImage: Boolean,
    imageUri: Uri?,
    onClick: () -> Unit,
) {
    Surface(
        modifier = Modifier
            .size(192.dp)
            .clickable { onClick() },
        color = MaterialTheme.colorScheme.tertiary,
        shape = RoundedCornerShape(28.dp)
    ) {
        if (hasImage && imageUri != null) {
            AsyncImage(
                model = imageUri,
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )
        } else {
            MyIcon(
                imageVector = Icons.Outlined.CameraAlt,
                modifier = Modifier.padding(32.dp),
                tint = Color.White
            )
        }
    }
}

@Composable
fun NameTextField(
    text: String,
    error: String?,
    onTextChange: (String) -> Unit,
) {
    MyOutlinedTextField(
        text = text,
        error = error,
        onTextChange = { if (it.isPersonNamesOnly()) onTextChange(it) },
        label = { Text("Nombre") },
        leadingIcon = { MyIcon(Icons.Outlined.AccountCircle) },
        supportingText = { error?.let { Text(it) } }
    )
}

@Composable
fun LastNameTextField(
    text: String,
    error: String?,
    onTextChange: (String) -> Unit,
) {
    MyOutlinedTextField(
        text = text,
        error = error,
        onTextChange = { if (it.isPersonNamesOnly()) onTextChange(it) },
        label = { Text("Apellidos") },
        leadingIcon = { MyIcon(Icons.Outlined.AccountCircle) },
        supportingText = { error?.let { Text(it) } }
    )
}

@Composable
fun GenderDropDownMenu(
    error: String?,
    onGenderChange: (String) -> Unit,
) {
    val keyboard = LocalSoftwareKeyboardController.current
    val scope = rememberCoroutineScope()
    val genders = listOf("", "Masculino", "Femenino")
    var genderSelected by rememberSaveable { mutableStateOf(genders[0]) }
    var isExpanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = isExpanded,
        onExpandedChange = { isExpanded = !isExpanded },
        modifier = Modifier
            .fillMaxWidth()
            .onFocusChanged {
                keyboard?.hide(); if (it.isFocused)
                scope.launch { delay(200); isExpanded = true }
            }
    ) {
        MyOutlinedTextField(
            text = genderSelected,
            error = error,
            onTextChange = { },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            readOnly = true,
            label = { Text("Género") },
            leadingIcon = {
                MyIcon(drawable = R.drawable.ic_yin_yang,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant)
            },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(false) },
            supportingText = { error?.let { Text(it) } },
            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colorScheme.secondary,
                focusedLabelColor = MaterialTheme.colorScheme.secondary
            )
        )

        ExposedDropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            (1..2).forEach { index ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = genders[index],
                            style = MaterialTheme.typography.bodyLarge,
                        )
                    },
                    onClick = {
                        genderSelected = genders[index];
                        onGenderChange(genderSelected); isExpanded = false
                    },
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )
            }
        }
    }
}

@Composable
fun BirthDateTextField(
    text: String,
    error: String?,
    onTextChange: (String) -> Unit,
    onDatePickerClicked: () -> Unit,
) {
    MyOutlinedTextField(
        text = text,
        error = error,
        onTextChange = { if (it.isDigitsOnly()) onTextChange(it) },
        label = { Text("Fecha de nacimiento") },
        supportingText = {
            Text(text = error ?: "dd/mm/aaaa")
        },
        leadingIcon = { MyIcon(Icons.Outlined.CalendarMonth) },
        trailingIcon = {
            Text(
                text = "Elegir",
                modifier = Modifier
                    .padding(end = 12.dp)
                    .clickable { onDatePickerClicked() },
                color = MaterialTheme.colorScheme.primary,
            )
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number
        ),
        maxChar = 8,
        visualTransformation = DateTransformation()
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

@Composable
fun CameraPermissionDeniedDialog(
    context: Context,
    onDissmiss: () -> Unit,
    onConfirmClicked: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDissmiss,
        icon = { MyIcon(Icons.Default.ReportOff) },
        title = { Text("Permiso de camara denegado") },
        text = { Text(stringResource(R.string.camera_permission_denied)) },
        confirmButton = { TextButton(onConfirmClicked) { Text("Ir a ajustes") } },
        dismissButton = { TextButton(onDissmiss) { Text("Cancelar") } }
    )
}
