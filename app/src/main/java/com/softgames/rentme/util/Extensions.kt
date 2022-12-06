package com.softgames.rentme.util

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

fun CharSequence.isDateOnly(): Boolean{
    val regex = "^[0-9/]*$".toRegex()
    return regex.matches(this)
}