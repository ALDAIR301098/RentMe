package com.softgames.rentme.presentation.components.others

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun MyIcon(
    imageVector: ImageVector,
    modifier: Modifier = Modifier.size(24.dp),
    description: String? = null,
    tint: Color = LocalContentColor.current,
) {
    Icon(
        painter = rememberVectorPainter(imageVector),
        contentDescription = description,
        modifier = modifier,
        tint = tint
    )
}

@Composable
fun MyIcon(
    @DrawableRes drawable: Int,
    modifier: Modifier = Modifier.size(24.dp),
    description: String? = null,
    tint: Color = Color.Unspecified,
) {
    Icon(
        painter = painterResource(drawable),
        contentDescription = description,
        modifier = modifier,
        tint = tint
    )
}

@Composable
fun MyIcon(
    bitmap: ImageBitmap,
    modifier: Modifier = Modifier.size(24.dp),
    description: String? = null,
    tint: Color = LocalContentColor.current
) {
    val painter = remember(bitmap) { BitmapPainter(bitmap) }
    Icon(
        painter = painter,
        contentDescription = description,
        modifier = modifier,
        tint = tint
    )
}