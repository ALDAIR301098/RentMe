package com.softgames.rentme.presentation.screens.register.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.softgames.rentme.presentation.components.others.MyIcon
import com.softgames.rentme.presentation.theme.RentMeTheme

@Composable
fun PhotoSelectorDialog(
    onDissmiss: () -> Unit,
    onCameraClicked: () -> Unit,
    onGalleryClicked: () -> Unit,
    onFileClicked: () -> Unit,
) {

    Dialog(onDissmiss) {
        Surface(
            shape = AlertDialogDefaults.shape,
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp),
            tonalElevation = 3.dp,
            shadowElevation = 3.dp
        ) {
            Column {

                TitleText()

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    CameraOption(onCameraClicked)
                    GalleryOption(onGalleryClicked)
                    FilesOption(onFileClicked)
                    CancelOption(onDissmiss)
                }
            }
        }
    }

}

@Composable
private fun TitleText() {
    Text(
        text = "Elegir foto de perfil",
        style = MaterialTheme.typography.headlineSmall,
        modifier = Modifier.padding(20.dp)
    )
}

@Composable
private fun CameraOption(
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(45.dp)
            .clickable { onClick() }
            .padding(horizontal = 28.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        MyIcon(Icons.Outlined.CameraAlt)
        Spacer(Modifier.width(16.dp))
        Text("Cámara")
    }
}

@Composable
private fun GalleryOption(
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(45.dp)
            .clickable { onClick() }
            .padding(horizontal = 28.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        MyIcon(Icons.Outlined.Image)
        Spacer(Modifier.width(16.dp))
        Text("Galeria")
    }
}

@Composable
private fun FilesOption(
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(45.dp)
            .clickable { onClick() }
            .padding(horizontal = 28.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        MyIcon(Icons.Outlined.Folder)
        Spacer(Modifier.width(16.dp))
        Text("Mis archivos")
    }
}

@Composable
private fun CancelOption(
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(45.dp)
            .clickable { onClick() }
            .padding(horizontal = 28.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        MyIcon(Icons.Outlined.Cancel)
        Spacer(Modifier.width(16.dp))
        Text("Cancelar")
    }
}

@Preview(showBackground = true)
@Composable
private fun PhotoSelectorDialogPreview() {
    RentMeTheme {

        PhotoSelectorDialog(
            onCameraClicked = {},
            onGalleryClicked = {},
            onFileClicked = {},
            onDissmiss = {}
        )

    }
}