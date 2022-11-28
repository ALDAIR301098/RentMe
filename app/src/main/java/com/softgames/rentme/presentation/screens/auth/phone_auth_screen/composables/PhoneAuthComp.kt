@file:OptIn(ExperimentalMaterial3Api::class)

package com.softgames.rentme.presentation.screens.auth.phone_auth_screen.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material.icons.outlined.Sms
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.softgames.rentme.presentation.components.buttons.MyButton
import com.softgames.rentme.presentation.components.buttons.MyTonalButton
import com.softgames.rentme.presentation.components.others.MyIcon

@Composable
fun PhoneAuthAppBar(
    onBackPressed: () -> Unit,
) {
    LargeTopAppBar(
        title = {
            Text(
                text = "Verificación telefónica",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {
            IconButton(onBackPressed) {
                MyIcon(Icons.Default.ArrowBack)
            }
        }
    )
}

@Composable
fun SmsCodeImage() {
    MyIcon(
        imageVector = Icons.Outlined.Sms,
        modifier = Modifier
            .size(96.dp)
            .padding(16.dp),
        tint = MaterialTheme.colorScheme.tertiary
    )
}

@Composable
fun PhoneMessageCard(
    phoneNumber: String = "+52 3222550033",
    changePhoneNumber: () -> Unit,
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text("Ingresa el código de verificación que enviamos al siguiente número:")
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = phoneNumber,
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.weight(1f),
                    color = MaterialTheme.colorScheme.tertiary,
                )
                OutlinedButton(onClick = changePhoneNumber) {
                    Text("Cambiar")
                }
            }
        }
    }
}

@Composable
fun VerifyPhoneButton(
    onClick: () -> Unit,
) {
    MyButton(onClick = onClick) {
        Text(text = "Finalizar")
    }
}

@Composable
fun ResendCodeButton(
    onClick: () -> Unit,
    enabled: Boolean,
    resendTime: Int,
) {
    MyTonalButton(
        onClick = onClick,
        enabled = enabled
    ) {
        MyIcon(Icons.Outlined.Refresh)
        Spacer(Modifier.width(8.dp))
        Text(
            text = if (resendTime != 0)
                "Reenviar código en $resendTime seg"
            else "Reenviar código"
        )
    }
}

@Composable
fun ErrorText(
    text: String,
) {
    Text(
        text = text,
        modifier = Modifier.padding(start = 6.dp),
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.error
    )
}