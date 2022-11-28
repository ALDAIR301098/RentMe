@file:OptIn(ExperimentalComposeUiApi::class)

package com.softgames.rentme.presentation.screens.auth.phone_auth_screen.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import com.softgames.rentme.presentation.components.textfields.MyTextField
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun OtpCodeBoxes(
    code: String,
    onCodeChange: (String) -> Unit,
    codeLenght: Int = 6,
) {

    val focusRequester = remember { FocusRequester() }
    val keyboard = LocalSoftwareKeyboardController.current

    Box {

        MyTextField(
            text = code,
            onTextChange = {
                if (it.length <= codeLenght && it.isDigitsOnly()) onCodeChange(it)
            },
            modifier = Modifier
                .size(0.dp)
                .focusRequester(focusRequester),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Phone
            )
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            repeat(codeLenght) { index ->
                OtpCodeBox(
                    digit = code.getOrNull(index)?.toString() ?: "",
                    modifier = Modifier
                        .size(50.dp)
                        .clickable { focusRequester.requestFocus(); keyboard?.show() }
                        .border(
                            width = if (code.length == index) 3.dp else 1.dp,
                            color = if (code.length == index) MaterialTheme.colorScheme.primary else Color.DarkGray,
                            shape = RoundedCornerShape(8.dp)
                        ),
                    isCursorVisible = code.length == index
                )
            }
        }
    }

}

@Composable
private fun OtpCodeBox(
    digit: String,
    modifier: Modifier,
    isCursorVisible: Boolean = false,
) {

    val scope = rememberCoroutineScope()
    var cursorSymbol by remember { mutableStateOf("") }

    LaunchedEffect(cursorSymbol, isCursorVisible) {
        if (isCursorVisible) {
            scope.launch {
                delay(350)
                cursorSymbol = if (cursorSymbol.isEmpty()) "|" else ""
            }
        }
    }

    Box(modifier) {
        Text(
            text = if (isCursorVisible) cursorSymbol else digit,
            modifier = Modifier.align(Alignment.Center)
        )
    }

}