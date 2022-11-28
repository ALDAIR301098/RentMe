@file:OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)

package com.softgames.rentme.presentation.screens.auth.login_screen.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.outlined.Mail
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material.icons.outlined.Sms
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import com.softgames.rentme.R
import com.softgames.rentme.data.model.Country
import com.softgames.rentme.presentation.components.buttons.MyButton
import com.softgames.rentme.presentation.components.buttons.MyOutlinedButton
import com.softgames.rentme.presentation.components.others.MyIcon
import com.softgames.rentme.presentation.components.others.MyImage
import com.softgames.rentme.presentation.components.textfields.MyOutlinedTextField

@Composable
fun LoginAppBar(
    onClose: () -> Unit,
) {
    LargeTopAppBar(
        title = { Text("Bienvenido a RentMe") },
        navigationIcon = {
            IconButton(onClose) {
                MyIcon(imageVector = Icons.Default.Close)
            }
        }
    )
}

@Composable
fun WelcomeImage() {
    MyImage(
        drawable = R.drawable.img_fire,
        modifier = Modifier
            .size(96.dp)
            .padding(16.dp)
    )
}

@Composable
fun CountryDropDownMenu(
    country: Country?,
    error: String?,
    showCountrySelector: Boolean,
    onCountrySelectorVisibilityChange: (Boolean) -> Unit,
) {

    ExposedDropdownMenuBox(
        expanded = showCountrySelector,
        onExpandedChange = onCountrySelectorVisibilityChange,
        modifier = Modifier
    ) {
        MyOutlinedTextField(
            text = country?.name ?: "",
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(),
            onTextChange = { },
            label = {
                Text("País o región")
            },
            leadingIcon = {
                if (country != null) MyIcon(country.flag, Modifier.size(24.dp))
                else MyIcon(Icons.Default.Public)
            },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(showCountrySelector)
            },
            supportingText = {
                if (error != null) Text(
                    text = error,
                    color = MaterialTheme.colorScheme.error
                )
            },
            error = error,
            readOnly = true,
        )
    }
}

@Composable
fun CodeTextField(
    text: String,
    error: String?,
    onTextChange: (String) -> Unit,
) {

    val focusManager = LocalFocusManager.current

    MyOutlinedTextField(
        text = text,
        modifier = Modifier.width(125.dp),
        label = { Text("Lada") },
        onTextChange = { if (it.length <= 3) onTextChange(it.trim()) },
        leadingIcon = { Text("+") },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(
            onNext = { focusManager.moveFocus(FocusDirection.Right) }
        ),
        supportingText = {
            if (error != null) Text(
                text = error,
                color = MaterialTheme.colorScheme.error
            )
        },
        error = error,
    )
}

@Composable
fun PhoneTextField(
    text: String,
    error: String?,
    onTextChange: (String) -> Unit,
) {
    MyOutlinedTextField(
        text = text,
        onTextChange = { if (it.isDigitsOnly()) onTextChange(it) },
        label = { Text("Teléfono") },
        leadingIcon = { MyIcon(Icons.Outlined.Phone) },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number
        ),
        supportingText = {
            if (error != null) Text(
                text = error,
                color = MaterialTheme.colorScheme.error
            )
        },
        maxChar = 10,
        error = error
    )
}

@Composable
fun PhoneSupportingText() {
    Text(
        text = "Te enviaremos un SMS con un código de verificación," +
                " para comprobar que la cuenta te pertenece.",
        modifier = Modifier.padding(top = 6.dp),
        style = MaterialTheme.typography.bodySmall,
    )
}

@Composable
fun LoginDivider() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Divider(Modifier.weight(1f))
        Text("o ingresa mediante")
        Divider(Modifier.weight(1f))
    }
}

@Composable
fun LoginButton(
    enable: Boolean,
    onClick: () -> Unit,
) {
    MyButton(
        onClick = onClick,
        enabled = enable
    ) {
        MyIcon(Icons.Outlined.Sms)
        Spacer(Modifier.width(8.dp))
        Text("Recibir código OTP")
    }
}

@Composable
fun MailButton(
    onClick: () -> Unit,
) {
    MyOutlinedButton(
        onClick = onClick,
        colors = ButtonDefaults.elevatedButtonColors(
            contentColor = MaterialTheme.colorScheme.onBackground
        )) {
        MyIcon(Icons.Outlined.Mail, Modifier.size(24.dp))
        Spacer(Modifier.width(8.dp))
        Text("Continuar con correo")
    }
}

@Composable
fun GoogleButton(
    modifier: Modifier,
    onClick: () -> Unit,
) {
    MyOutlinedButton(
        modifier = modifier.height(56.dp),
        onClick = onClick,
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = MaterialTheme.colorScheme.onBackground
        )
    ) {
        MyIcon(R.drawable.ic_google)
        Spacer(Modifier.width(8.dp))
        Text("Google")
    }
}

@Composable
fun FacebookButton(
    modifier: Modifier,
    onClick: () -> Unit,
) {
    MyOutlinedButton(
        modifier = modifier.height(56.dp),
        onClick = onClick,
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = MaterialTheme.colorScheme.onBackground
        )
    ) {
        MyIcon(R.drawable.ic_facebook)
        Spacer(Modifier.width(8.dp))
        Text("Facebook")
    }
}