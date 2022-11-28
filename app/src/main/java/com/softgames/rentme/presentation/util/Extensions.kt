package com.softgames.rentme.presentation.util

import android.content.Context
import android.widget.Toast
import java.text.Normalizer

fun showMessage(context: Context, message: Any, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(context, message.toString(), duration).show()
}

fun String.unacent(): String {
    val REGEX_UNACCENT = "\\p{InCombiningDiacriticalMarks}+".toRegex()
    val temp = Normalizer.normalize(this, Normalizer.Form.NFD)
    return REGEX_UNACCENT.replace(temp, "")
}