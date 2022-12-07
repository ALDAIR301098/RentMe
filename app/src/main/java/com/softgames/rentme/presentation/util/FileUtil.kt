package com.softgames.rentme.presentation.util

import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.core.content.FileProvider
import java.io.File
import java.util.*

class FileUtil {
    companion object {
        fun getImageUri(context: Context): Uri {
            val path_directory = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            val file = File.createTempFile("${Date()}_foto", ".jpg", path_directory)
            val uri = FileProvider.getUriForFile(context, "com.softgames.rentme", file)
            return uri
        }
    }
}