package com.softgames.rentme.presentation.screens.home.house_detail

import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.softgames.rentme.R
import com.softgames.rentme.domain.model.HouseFeature
import com.softgames.rentme.presentation.components.buttons.MyButton
import com.softgames.rentme.presentation.components.others.MyIcon
import com.softgames.rentme.presentation.components.others.MyImage

@Composable
fun HouseName(
    name: String,
) {
    Text(
        text = name,
        modifier = Modifier.fillMaxWidth(),
        style = MaterialTheme.typography.headlineSmall,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
fun HouseDetails(
    rating: Float,
    timesRated: Int,
    colony: String,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        MyImage(
            imageVector = Icons.Filled.Star,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground),
            modifier = Modifier.size(20.dp)
        )
        Text(rating.toString())
        Text("·")
        Text(
            buildAnnotatedString {
                withStyle(SpanStyle(
                    fontWeight = FontWeight.W500,
                    textDecoration = TextDecoration.Underline)
                ) {
                    append("$timesRated evaluaciones")
                }
            }
        )
        Text("·")
        Text(colony)
    }
}

@Composable
fun HostRow(
    name: String,
    photo: Uri?,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Anfitrion de la casa: ${name}.",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.W500),
            maxLines = 2,
            modifier = Modifier.weight(1f)
        )
        AsyncImage(
            model = photo ?: R.drawable.img_user_profile,
            contentDescription = null,
            modifier = Modifier.size(48.dp),
        )
    }
}

@Composable
fun HouseDescription(
    description: String,
) {
    Text(text = description)
}

@Composable
fun HouseFeaturesTitle() {
    Text(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        text = "Caracteristicas de la casa",
        style = MaterialTheme.typography.titleLarge.copy(
            fontWeight = FontWeight.W500
        )
    )
}

@Composable
fun HouseFeaturesItems(
    featureList: List<HouseFeature>,
) {
    LazyRow(
        modifier = Modifier
            .nestedScroll(rememberNestedScrollInteropConnection()),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item { Spacer(Modifier) }
        items(featureList) { item ->
            HouseFeatureItem(item)
        }
        item { Spacer(Modifier) }
    }
}

@Composable
fun HouseFeatureItem(
    houseFeature: HouseFeature,
) {
    OutlinedCard(
        modifier = Modifier
            .width(150.dp)
            .height(125.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
        ) {
            MyIcon(
                imageVector = houseFeature.icon,
                modifier = Modifier.size(48.dp),
                tint = Color.DarkGray
            )
            Text("${houseFeature.quantity} ${houseFeature.name}")
        }
    }
}

@Composable
fun PriceBar(
    modifier: Modifier,
    price: Float,
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
    ) {
        Column {
            Divider(Modifier.fillMaxWidth())
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .navigationBarsPadding()
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    buildAnnotatedString {
                        withStyle(SpanStyle()) {
                            append("$${price} ")
                        }
                        append("mes")
                    }
                )
                MyButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .width(150.dp)
                        .height(56.dp)
                ) {
                    Text("Rentar")
                }
            }
        }
    }
}

