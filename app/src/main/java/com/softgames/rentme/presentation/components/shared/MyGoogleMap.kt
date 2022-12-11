package com.softgames.rentme.presentation.components.shared

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Directions
import androidx.compose.material3.Button
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.GeoPoint
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.softgames.rentme.R
import com.softgames.rentme.presentation.components.others.MyIcon
import com.softgames.rentme.presentation.components.others.MyImage


@Composable
fun MyGoogleMap(
    city: GeoPoint,
    onPositionChange: (LatLng) -> Unit,
    onMapCameraMove: (Boolean) -> Unit,
) {
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(city.latitude, city.longitude), 12.5f)
    }

    onPositionChange(cameraPositionState.position.target)
    onMapCameraMove(cameraPositionState.isMoving)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        contentAlignment = Alignment.Center
    ) {
        GoogleMap(
            modifier = Modifier
                .fillMaxSize()
                .clip(MaterialTheme.shapes.large),
            cameraPositionState = cameraPositionState,
        )
        MyImage(
            drawable = R.drawable.img_marker,
            modifier = Modifier.size(48.dp)
        )
    }

}

@Composable
fun MyGoogleMap(
    location: GeoPoint,
    onMapCameraMove: (Boolean) -> Unit,
    onNavigateClicked: () -> Unit,
) {

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            LatLng(location.latitude, location.longitude), 14f
        )
    }

    LaunchedEffect(location) {
        cameraPositionState.animate(
            update = CameraUpdateFactory.newCameraPosition(
                CameraPosition(LatLng(location.latitude, location.longitude), 14f, 0f, 0f)
            ),
            durationMs = 1
        )
    }

    onMapCameraMove(cameraPositionState.isMoving)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        GoogleMap(
            modifier = Modifier
                .fillMaxSize()
                .clip(MaterialTheme.shapes.large),
            cameraPositionState = cameraPositionState,
        ) {
            Marker(
                state = MarkerState(
                    position = LatLng(
                        location.latitude, location.longitude
                    )
                ),
                icon = BitmapDescriptorFactory.fromResource(R.drawable.img_home)
            )
        }

        FilledTonalButton(
            onClick = onNavigateClicked,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp),
        ) {
            MyIcon(Icons.Outlined.Directions)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Instrucciones")
        }

    }
}