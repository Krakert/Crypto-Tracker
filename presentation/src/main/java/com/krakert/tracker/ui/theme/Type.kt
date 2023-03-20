package com.krakert.tracker.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.wear.compose.material.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.krakert.tracker.R


val Rubik = FontFamily(
    Font(resId = R.font.rubik_light, weight = FontWeight.Light),
    Font(resId = R.font.rubik_light_italic, weight = FontWeight.Light, style = FontStyle.Italic),
    Font(resId = R.font.rubik_medium, weight = FontWeight.Medium),
    Font(resId = R.font.rubik_medium_italic, weight = FontWeight.Medium, style = FontStyle.Italic),
    Font(resId = R.font.rubik_bold, weight = FontWeight.Bold),
    Font(resId = R.font.rubik_bold_italic, weight = FontWeight.Bold, style = FontStyle.Italic),
    Font(resId = R.font.rubik_black, weight = FontWeight.Black),
    Font(resId = R.font.rubik_black_italic, weight = FontWeight.Black, style = FontStyle.Italic)
)

val Typography = Typography(
//    h1 = TextStyle(
//        fontFamily = Rubik,
//        fontWeight = FontWeight.Bold,
//        fontSize = 32.sp
//    ),
//    h2 = TextStyle(
//        fontFamily = Rubik,
//        fontWeight = FontWeight.Bold,
//        fontSize = 24.sp
//    ),
//    h3 = TextStyle(
//        fontFamily = Rubik,
//        fontWeight = FontWeight.Bold,
//        fontSize = 14.sp
//    ),
//    subtitle1 = TextStyle(
//        fontFamily = Rubik,
//        fontWeight = FontWeight.Medium,
//        fontSize = 16.sp
//    ),
    body1 = TextStyle(
        fontFamily = Rubik,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp
    ),
    button = TextStyle(
        fontFamily = Rubik,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp
    )
)

data class CustomTypography(
//    val pokemonBadgeId: TextStyle = TextStyle(
//        fontFamily = Rubik,
//        fontWeight = FontWeight.Medium,
//        fontSize = 10.sp
//    ),
//    val pokemonDetailsId: TextStyle = TextStyle(
//        fontFamily = Rubik,
//        fontWeight = FontWeight.Light,
//        fontSize = 24.sp,
//        color = TextSecondaryColor
//    ),
    val searchQuery: TextStyle = TextStyle(
        fontFamily = Rubik,
        fontWeight = FontWeight.Light,
        fontSize = 16.sp,
//        color = TextPrimaryColor
    ),
    val searchHint: TextStyle = TextStyle(
        fontFamily = Rubik,
        fontWeight = FontWeight.Light,
        fontSize = 16.sp,
//        color = TextSecondaryColor
    )
)

val LocalTypography = staticCompositionLocalOf { CustomTypography() }