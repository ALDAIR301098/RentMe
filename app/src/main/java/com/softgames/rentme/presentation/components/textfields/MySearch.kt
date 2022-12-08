@file:OptIn(ExperimentalMaterial3Api::class)

package com.softgames.rentme.presentation.components.textfields

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.softgames.rentme.presentation.components.others.MyIcon
import com.softgames.rentme.presentation.theme.RentMeTheme

@Composable
fun MySearch(
    text: String,
    modifier: Modifier = Modifier
        .fillMaxWidth(.95f)
        .height(50.dp),
    onTextChange: (String) -> Unit,
    placeholder: String? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    textStyle: TextStyle = LocalTextStyle.current.copy(
        fontSize = 16.sp, color = MaterialTheme.colorScheme.onSurface
    ),
    shape: Shape = RoundedCornerShape(100),
) {

    var isFocused by remember { mutableStateOf(false) }
    var isDarkTheme = isSystemInDarkTheme()

    BasicTextField(
        value = text,
        onValueChange = onTextChange,
        singleLine = true,
        textStyle = textStyle,
        modifier = modifier.onFocusEvent { isFocused = it.isFocused },
        cursorBrush = SolidColor(if (isDarkTheme) Color.Gray else MaterialTheme.colorScheme.secondary),
        decorationBox = { innerTextField ->
            Surface(
                color = MaterialTheme.colorScheme.background,
                shape = shape
            ) {
                Row(
                    modifier = modifier,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    leadingIcon?.let {
                        Spacer(Modifier.padding(12.dp)); it.invoke()
                    }

                    Spacer(Modifier.width(16.dp))
                    Box(Modifier.weight(1f)) {
                        if (placeholder != null){
                            if (!isFocused){
                                Text(text = placeholder, color = if (isDarkTheme) Color.Gray else Color.DarkGray, fontSize = 16.sp)
                            } else {
                                innerTextField.invoke()
                            }
                        } else{
                            innerTextField.invoke()
                        }
                    }
                    Spacer(Modifier.width(16.dp))

                    trailingIcon?.let {
                        it.invoke()
                        Spacer(Modifier.width(12.dp))
                    }

                }
            }
        }
    )


}

@Preview(showBackground = true)
@Composable
private fun MySearchPreview() {
    RentMeTheme {
        MySearch(
            text = "Buscar",
            onTextChange = { },
            trailingIcon = { MyIcon(Icons.Outlined.Search) }
        )
    }
}