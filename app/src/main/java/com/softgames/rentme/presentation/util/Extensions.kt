package com.softgames.rentme.presentation.util

import android.content.Context
import android.widget.Toast
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.shouldShowRationale
import java.text.Normalizer

fun showMessage(context: Context, message: Any, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(context, message.toString(), duration).show()
}

@ExperimentalPermissionsApi
fun PermissionState.isPermanentlyDenied() = !status.shouldShowRationale && !status.isGranted
