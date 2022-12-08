package com.softgames.rentme.util

import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.core.content.FileProvider
import java.io.File
import java.text.Normalizer

fun String.unacent(): String {
    val REGEX_UNACCENT = "\\p{InCombiningDiacriticalMarks}+".toRegex()
    val temp = Normalizer.normalize(this, Normalizer.Form.NFD)
    return REGEX_UNACCENT.replace(temp, "")
}

fun CharSequence.isPersonNamesOnly(): Boolean{
    val regex = "^[A-Za-z ÁÉÍÓÚáéíóú]*$".toRegex()
    return regex.matches(this)
}

fun createUriFromFile(context: Context): Uri {
    val photoFile = File.createTempFile(
        "IMG_", ".jpg", context.getExternalFilesDir(Environment.DIRECTORY_PICTURES))
    return FileProvider.getUriForFile(context, "${context.packageName}.provider", photoFile)
}