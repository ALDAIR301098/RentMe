package com.softgames.rentme.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.softgames.rentme.R

val poppins = FontFamily(
    Font(resId = R.font.poppins100, weight = FontWeight.W100),
    Font(resId = R.font.poppins200, weight = FontWeight.W200),
    Font(resId = R.font.poppins300, weight = FontWeight.W300),
    Font(resId = R.font.poppins400, weight = FontWeight.W400),
    Font(resId = R.font.poppins500, weight = FontWeight.W500),
    Font(resId = R.font.poppins600, weight = FontWeight.W600),
    Font(resId = R.font.poppins700, weight = FontWeight.W700),
    Font(resId = R.font.poppins800, weight = FontWeight.W800),
    Font(resId = R.font.poppins900, weight = FontWeight.W900)
)

val samsungSharp = FontFamily(
    Font(resId = R.font.samsung_light, weight = FontWeight.Light),
    Font(resId = R.font.samsung_medium, weight = FontWeight.Medium),
    Font(resId = R.font.samsung_bold, weight = FontWeight.Bold)
)

val typography = Typography(

    displayLarge = TextStyle(
        fontFamily = poppins,
        lineHeight = 64.sp,
        fontSize = 57.sp,
        letterSpacing = 0.sp,
        fontWeight = FontWeight.W400,
    ),

    displayMedium = TextStyle(
        fontFamily = poppins,
        lineHeight = 52.sp,
        fontSize = 45.sp,
        letterSpacing = 0.sp,
        fontWeight = FontWeight.W400,
    ),

    displaySmall = TextStyle(
        fontFamily = poppins,
        lineHeight = 44.sp,
        fontSize = 36.sp,
        letterSpacing = 0.sp,
        fontWeight = FontWeight.W400,
    ),

    headlineLarge = TextStyle(
        fontFamily = samsungSharp,
        lineHeight = 40.sp,
        fontSize = 32.sp,
        letterSpacing = 0.sp,
        fontWeight = FontWeight.Bold,
    ),
    headlineMedium = TextStyle(
        fontFamily = samsungSharp,
        lineHeight = 36.sp,
        fontSize = 28.sp,
        letterSpacing = 0.sp,
        fontWeight = FontWeight.Bold,
    ),

    headlineSmall = TextStyle(
        fontFamily = samsungSharp,
        lineHeight = 32.sp,
        fontSize = 24.sp,
        letterSpacing = 0.sp,
        fontWeight = FontWeight.Bold,
    ),

    titleLarge = TextStyle(
        fontFamily = samsungSharp,
        lineHeight = 28.sp,
        fontSize = 22.sp,
        letterSpacing = 0.sp,
        fontWeight = FontWeight.Bold,
    ),

    titleMedium = TextStyle(
        fontFamily = samsungSharp,
        lineHeight = 24.sp,
        fontSize = 16.sp,
        letterSpacing = 0.15.sp,
        fontWeight = FontWeight.Bold,
    ),
    titleSmall = TextStyle(
        fontFamily = samsungSharp,
        lineHeight = 20.sp,
        fontSize = 14.sp,
        letterSpacing = 0.1.sp,
        fontWeight = FontWeight.Bold,
    ),
    labelLarge = TextStyle(
        fontFamily = poppins,
        lineHeight = 20.sp,
        fontSize = 14.sp,
        letterSpacing = 0.1.sp,
        fontWeight = FontWeight.W500,
    ),
    labelMedium = TextStyle(
        fontFamily = poppins,
        lineHeight = 16.sp,
        fontSize = 12.sp,
        letterSpacing = 0.5.sp,
        fontWeight = FontWeight.W500,
    ),
    labelSmall = TextStyle(
        fontFamily = poppins,
        lineHeight = 16.sp,
        fontSize = 11.sp,
        letterSpacing = 0.5.sp,
        fontWeight = FontWeight.W500,
    ),
    bodyLarge = TextStyle(
        fontFamily = poppins,
        lineHeight = 24.sp,
        fontSize = 16.sp,
        letterSpacing = 0.5.sp,
        fontWeight = FontWeight.W400,
    ),
    bodyMedium = TextStyle(
        fontFamily = poppins,
        lineHeight = 20.sp,
        fontSize = 14.sp,
        letterSpacing = 0.25.sp,
        fontWeight = FontWeight.W400,
    ),
    bodySmall = TextStyle(
        fontFamily = poppins,
        lineHeight = 16.sp,
        fontSize = 12.sp,
        letterSpacing = 0.4.sp,
        fontWeight = FontWeight.W400,
    ),

    )