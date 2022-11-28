@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)

package com.softgames.rentme.presentation.screens.register

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.softgames.rentme.R
import com.softgames.rentme.presentation.components.buttons.MyButton
import com.softgames.rentme.presentation.components.others.MyIcon
import com.softgames.rentme.presentation.components.textfields.MyOutlinedTextField
import com.softgames.rentme.presentation.components.textfields.MyTextField
import com.softgames.rentme.presentation.theme.RentMeTheme

@Composable
fun RegisterScreen() {
    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = {
                    Text(
                        text = "Registrar nuevo usuario ",
                        modifier = Modifier.padding(end = 16.dp),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                },
                navigationIcon = { MyIcon(Icons.Default.Close) }
            )
        },

        ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Spacer(Modifier)

            Surface(
                modifier = Modifier.size(192.dp),
                color = MaterialTheme.colorScheme.tertiary,
                shape = RoundedCornerShape(28.dp)
            ) {
                MyIcon(
                    imageVector = Icons.Outlined.CameraAlt,
                    modifier = Modifier.padding(32.dp),
                    tint = Color.White
                )
            }

            var name by remember { mutableStateOf("") }
            var lastName by remember { mutableStateOf("") }
            var gender by remember { mutableStateOf("") }
            var birthDate by remember { mutableStateOf("") }
            var mail by remember { mutableStateOf("") }
            var password by remember { mutableStateOf("") }
            var repeatPassword by remember { mutableStateOf("") }

            Spacer(Modifier)

            MyOutlinedTextField(
                text = name,
                onTextChange = { name = it },
                label = { Text("Nombre") },
                leadingIcon = { MyIcon(Icons.Outlined.AccountCircle) }
            )

            MyOutlinedTextField(
                text = lastName,
                onTextChange = { lastName = it },
                label = { Text("Apellidos") },
                leadingIcon = { MyIcon(Icons.Outlined.AccountCircle) }
            )

            MyOutlinedTextField(
                text = gender,
                onTextChange = { gender = it },
                label = { Text("Género") },
                leadingIcon = { MyIcon(R.drawable.ic_yin_yang) },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(false) }
            )

            MyOutlinedTextField(
                text = birthDate,
                onTextChange = { birthDate = it },
                label = { Text("Fecha de nacimiento") },
                leadingIcon = { MyIcon(Icons.Outlined.CalendarMonth) },
                trailingIcon = {
                    Text(
                        text = "Elegir", modifier = Modifier.padding(end = 12.dp),
                        color = MaterialTheme.colorScheme.secondary
                    )
                },
                supportingText = { Text("dd/mm/aaaa") }
            )

            MyButton(onClick = { /*TODO*/ }) {
                Text("Finalizar registro")
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RegisterScreenPreview() {
    RentMeTheme {
        RegisterScreen()
    }
}