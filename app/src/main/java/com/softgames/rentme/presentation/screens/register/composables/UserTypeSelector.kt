package com.softgames.rentme.presentation.screens.register.composables

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.softgames.rentme.R
import com.softgames.rentme.domain.model.RentMeUser
import com.softgames.rentme.domain.model.RentMeUser.Guest
import com.softgames.rentme.domain.model.RentMeUser.Host
import com.softgames.rentme.presentation.components.others.MyImage
import com.softgames.rentme.presentation.theme.RentMeTheme

@Composable
fun UserTypeSelector(
    userSelected: RentMeUser,
    onUserTypeChange: (RentMeUser) -> Unit,
) {

    Column {

        Text(
            text = "Seleccione su tipo de usuario",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(6.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            UserOption(
                modifier = Modifier.weight(1f),
                isSelected = userSelected == Guest,
                userType = Guest,
                onClick = onUserTypeChange
            )
            UserOption(
                modifier = Modifier.weight(1f),
                isSelected = userSelected == Host,
                userType = Host,
                onClick = onUserTypeChange
            )
        }

        Spacer(modifier = Modifier.height(10.dp))
    }
}

@Composable
private fun UserOption(
    modifier: Modifier,
    isSelected: Boolean,
    userType: RentMeUser,
    onClick: (RentMeUser) -> Unit,
) {
    Surface(
        modifier = modifier
            .height(125.dp)
            .clip(RoundedCornerShape(28.dp))
            .border(
                width = if (isSelected) 3.dp else 0.dp,
                color = if (isSelected) MaterialTheme.colorScheme.secondary else Color.Transparent,
                shape = RoundedCornerShape(28.dp)
            )
            .clickable { onClick(userType) },
        color = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp),
        tonalElevation = 3.dp,
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(
                space = 8.dp, alignment = Alignment.CenterVertically
            )
        ) {
            MyImage(
                drawable = if (userType is Guest) R.drawable.img_guest else R.drawable.img_host,
                modifier = Modifier.size(50.dp)
            )
            Text(text = if (userType is Guest) "Huesped" else "Anfitrión")
        }
    }
}
