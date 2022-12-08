@file:OptIn(ExperimentalMaterial3Api::class)

package com.softgames.rentme.presentation.screens.home.guest_home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.softgames.rentme.domain.model.House
import com.softgames.rentme.presentation.components.others.MyIcon
import com.softgames.rentme.presentation.components.textfields.MySearch
import com.softgames.rentme.presentation.theme.RentMeTheme

@Composable
fun HomeAppBar(
    txtSearch: String,
    onQueryChange: (String) -> Unit,
    scrollBehavior: TopAppBarScrollBehavior
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
                    placeholder = "Bucar departamento"
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        scrollBehavior = scrollBehavior
    )
}

@Composable
fun HouseItem(
    house: House,
) {

    var isFavorite by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        //HOUSE IMAGE
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(350.dp)
                .clip(RoundedCornerShape(28.dp))
                .background(Color.Blue)

        ) {
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
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = house.name,
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.headlineSmall,
                    maxLines = 2
                )
                Spacer(modifier = Modifier.width(16.dp))
                Row(
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

@Preview(showBackground = true)
@Composable
fun HouseItemPreview() {
    RentMeTheme {
        val house = House(
            name = "Casa del arbol",
            rating = 4.6f,
            timesRated = 10,
            colony = "Fluvial Vallarta",
            price = 7500f
        )
        HouseItem(house)
    }
}