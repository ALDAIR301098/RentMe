package com.softgames.rentme.presentation.components.shared

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.softgames.rentme.presentation.components.others.MyIcon
import com.softgames.rentme.presentation.theme.RentMeTheme

@Composable
fun UserProfilePictureDialog(
    photoUri: String?,
    userName: String,
    onDismiss: () -> Unit,
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = MaterialTheme.shapes.large,
            modifier = Modifier.size(300.dp, 350.dp),
            color = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp),
            tonalElevation = 3.dp
        ) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    AsyncImage(
                        model = photoUri,
                        contentDescription = null,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                            .background(Color(0x75000000))
                    ) {
                        Text(
                            text = userName,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            color = Color.White,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .align(Alignment.BottomCenter),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onDismiss) {
                            MyIcon(Icons.Outlined.Edit)
                        }
                        IconButton(onDismiss) {
                            MyIcon(Icons.Outlined.Save)
                        }

                        IconButton(onDismiss) {
                            MyIcon(Icons.Outlined.IosShare)
                        }

                        IconButton(onDismiss) {
                            MyIcon(Icons.Outlined.Info)
                        }
                    }

                }

        }
    }
}

@Preview(showBackground = true)
@Composable
private fun UserProfilePictureDialogPreview() {
    RentMeTheme {
        UserProfilePictureDialog(
            photoUri = null,
            userName = "John Doe",
            onDismiss = {},
        )
    }
}