package com.softgames.rentme.presentation.util

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.OpenableColumns
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.core.net.toUri
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.shouldShowRationale
import com.google.android.gms.common.util.IOUtils.copyStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.Normalizer

fun showMessage(context: Context, message: Any, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(context, message.toString(), duration).show()
}

@Throws(IOException::class)
fun Uri.getFilePathContentResolverFromUri(context: Context): Uri? {
    val fileName: String = this.getFileName(context = context)
    val file = File(context.externalCacheDir, fileName)
    file.createNewFile()
    FileOutputStream(file).use { outputStream ->
        context.contentResolver.openInputStream(this).use { inputStream ->
            inputStream?.let { copyStream(it, outputStream) } //Simply reads input to output stream
            outputStream.flush()
        }
    }
    return file.toUri()
}

fun Uri.getFileExtension(context: Context): String? {
    val fileType: String? = this.let { context.contentResolver.getType(it) }
    return MimeTypeMap.getSingleton().getExtensionFromMimeType(fileType)
}

fun Uri.getFileName(context: Context): String {
    var fileName: String? = this.getFileNameFromCursor(context)
    if (fileName == null) {
        val fileExtension: String? = this.getFileExtension(context)
        fileName = "temp_file" + if (fileExtension != null) ".$fileExtension" else ""
    } else if (!fileName.contains(".")) {
        val fileExtension: String? = this.getFileExtension(context)
        fileName = "$fileName.$fileExtension"
    }
    return fileName
}

fun Uri.getFileNameFromCursor(context: Context): String? {
    val fileCursor: Cursor? = this.let {
        context.contentResolver
            .query(it, arrayOf(OpenableColumns.DISPLAY_NAME), null, null, null)
    }
    var fileName: String? = null
    if (fileCursor != null && fileCursor.moveToFirst()) {
        val cIndex: Int = fileCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        if (cIndex != -1) {
            fileName = fileCursor.getString(cIndex)
        }
    }
    return fileName
}
