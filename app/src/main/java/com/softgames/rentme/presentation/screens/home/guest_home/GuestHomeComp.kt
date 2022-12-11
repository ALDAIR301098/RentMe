@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalPagerApi::class)

package com.softgames.rentme.presentation.screens.home.guest_home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.softgames.rentme.domain.model.House
import com.softgames.rentme.domain.model.RentMeUser
import com.softgames.rentme.presentation.components.others.MyIcon
import com.softgames.rentme.presentation.components.textfields.MySearch

@Composable
fun HomeAppBar(
    user: RentMeUser?,
    txtSearch: String,
    onQueryChange: (String) -> Unit,
    onSearchPressed: () -> Unit,
    onPhotoClicked: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior,
) {
    CenterAlignedTopAppBar(
        title = {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                MySearch(
                    text = txtSearch,
                    onTextChange = onQueryChange,
                    leadingIcon = { MyIcon(Icons.Outlined.Search) },
                    trailingIcon = {
                        AsyncImage(
                            model = user?.photo,
                            contentDescription = null,
                            modifier = Modifier
                                .size(32.dp)
                                .clip(RoundedCornerShape(100))
                                .clickable { onPhotoClicked() },
                        )
                    },
                    placeholder = "Bucar departamento",
                    keyboardActions = KeyboardActions(
                        onSearch = { onSearchPressed() }
                    )
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        scrollBehavior = scrollBehavior,
    )
}

@Composable
fun HouseItem(
    house: House,
    onClick: (House) -> Unit,
) {

    var isFavorite by rememberSaveable { mutableStateOf(false) }

    Column(
        Modifier
            .fillMaxWidth()
            .clickable { onClick(house) },
    ) {

        //HOUSE IMAGE
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(350.dp)
                .clip(RoundedCornerShape(28.dp))

        ) {

            val pagerState = rememberPagerState()

            HorizontalPager(
                count = house.photoList.size,
                state = pagerState,
            ) { index ->
                AsyncImage(
                    model = house.photoList[index],
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            HorizontalPagerIndicator(
                pagerState = pagerState,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 16.dp)
            )

            IconButton(
                onClick = { isFavorite = !isFavorite },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
            ) {
                MyIcon(
                    imageVector = if (isFavorite) Icons.Outlined.Favorite else Icons.Outlined.FavoriteBorder,
                    tint = if (isFavorite) Color.Red else Color.DarkGray,
                )
            }
        }

        Spacer(Modifier.height(8.dp))

        Column(
            modifier = Modifier.padding(horizontal = 8.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {

                Text(
                    text = house.name,
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.headlineSmall,
                    maxLines = 2
                )
                Spacer(modifier = Modifier.width(16.dp))
                Row(
                    modifier = Modifier.padding(top = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    MyIcon(imageVector = Icons.Filled.Star, modifier = Modifier.size(20.dp))
                    Text(house.rating.toString())
                    Text("(${house.timesRated})")
                }
            }

            Text(text = house.colony, style = MaterialTheme.typography.bodyLarge)
            Text(
                buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.W600,
                            fontSize = 16.sp
                        )
                    ) {
                        append("${house.price} MXN / ")
                    }
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.W400,
                            fontSize = 16.sp
                        )
                    ) {
                        append("mes")
                    }
                }
            )
        }

    }
}