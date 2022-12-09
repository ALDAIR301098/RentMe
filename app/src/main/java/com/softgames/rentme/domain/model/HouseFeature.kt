package com.softgames.rentme.domain.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Bathroom
import androidx.compose.ui.graphics.vector.ImageVector

data class HouseFeature(
    var name: String = "",
    var icon: ImageVector = Icons.Outlined.Bathroom,
    var quantity: Int = 0,
)
