@file:OptIn(ExperimentalPagerApi::class, ExperimentalPagerApi::class)

package com.softgames.rentme.presentation.screens.home.register_house

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CameraAlt
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.softgames.rentme.presentation.components.others.MyImage
import com.softgames.rentme.presentation.theme.RentMeTheme

@Composable
fun PhotoViewPager(
    imageList: List<Uri>,
    onItemClick: () -> Unit,
) {
    HorizontalPager(count = imageList.size.plus(1)) { index ->
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(275.dp)
                .padding(horizontal = 20.dp)
                .clip(RoundedCornerShape(28.dp))
                .background(MaterialTheme.colorScheme.tertiary)
                .clickable { if (index == imageList.size) onItemClick() },
            contentAlignment = Alignment.Center
        ) {

            if (index != imageList.size) {

                AsyncImage(
                    model = imageList[index],
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                Surface(
                    color = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .width(80.dp)
                        .align(Alignment.BottomEnd)
                        .padding(16.dp)
                ) {
                    Text(
                        text = "${index.plus(1)}/${imageList.size}",
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier
                            .padding(8.dp)
                            .align(Alignment.Center),
                        textAlign = TextAlign.Center
                    )
                }
            } else {

                MyImage(
                    imageVector = Icons.Outlined.CameraAlt,
                    modifier = Modifier
                        .size(100.dp)
                        .clickable { onItemClick() },
                    colorFilter = ColorFilter.tint(Color.White)
                )
                Text(
                    text = "Elegir Foto",
                    color = MaterialTheme.colorScheme.inverseOnSurface,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(48.dp)
                )

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PhotoViewPagerPreview() {
    RentMeTheme {
        PhotoViewPager(ArrayList()) { }
    }
}