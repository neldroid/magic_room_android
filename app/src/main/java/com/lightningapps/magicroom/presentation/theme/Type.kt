package com.lightningapps.magicroom.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.lightningapps.magicroom.R


private val fontFamily = FontFamily(
    Font(R.font.opensans_regular),
    Font(R.font.opensans_bold, weight = FontWeight.Bold),
    Font(R.font.opensans_extrabold, weight = FontWeight.ExtraBold),
    Font(R.font.opensans_light, weight = FontWeight.Light),
    Font(R.font.opensans_medium, weight = FontWeight.Medium),
    Font(R.font.opensans_semibold, weight = FontWeight.SemiBold),
)
// Set of Material typography styles to start with
val CustomTypography = Typography(
    titleLarge = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 25.sp
    ),
    titleMedium = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Light,
        fontSize = 20.sp
    ),
    bodySmall = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp
    ),
)