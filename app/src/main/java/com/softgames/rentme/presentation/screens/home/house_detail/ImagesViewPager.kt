@file:OptIn(ExperimentalPagerApi::class)

package com.softgames.rentme.presentation.screens.home.house_detail

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.softgames.rentme.presentation.components.others.MyImage
import com.softgames.rentme.presentation.theme.RentMeTheme
import com.softgames.rentme.R

@Composable
fun ImagesViewPager(
    imageList: List<Uri>,
) {
   if (imageList.isNotEmpty()){
       HorizontalPager(count = imageList.size) { index ->
           Box(
               modifier = Modifier
                   .fillMaxWidth()
                   .height(305.dp),
               contentAlignment = Alignment.Center
           ) {

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
           }
       }
   }
    else {
       Box(
           modifier = Modifier
               .fillMaxWidth()
               .height(275.dp)
               .background(MaterialTheme.colorScheme.secondary),
           contentAlignment = Alignment.Center
       ){
           MyImage(
               drawable = R.drawable.ic_image_error,
               modifier = Modifier.size(100.dp)
           )
       }
   }
}


@Preview(showBackground = true)
@Composable
private fun ImagesViewPagerPreview() {
    RentMeTheme {
        ImagesViewPager(emptyList())
    }
}