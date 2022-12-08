package com.softgames.rentme.presentation.components.textfields

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
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
        .fillMaxWidth()
        .height(56.dp),
    onTextChange: (String) -> Unit,
    placeholder: String? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    textStyle: TextStyle = LocalTextStyle.current.copy(
        fontSize = 16.sp
    ),
    shape: Shape = RoundedCornerShape(100)
) {
    BasicTextField(
        value = text,
        onValueChange = onTextChange,
        singleLine = true,
        textStyle = textStyle,
        modifier = modifier,
        decorationBox = { innerTextField ->
            Surface(
                color = MaterialTheme.colorScheme.onSurface,
                shape = shape
            ) {
                Row(
                    modifier = modifier,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    leadingIcon?.let {
                        Spacer(Modifier.padding(16.dp)); it.invoke()
                    }

                    Spacer(Modifier.width(16.dp))
                    Box(Modifier.weight(1f)) {
                        innerTextField.invoke()
                    }
                    Spacer(Modifier.width(16.dp))

                    trailingIcon?.let {
                        it.invoke()
                        Spacer(Modifier.width(16.dp))
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