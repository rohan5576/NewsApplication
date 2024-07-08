package com.rohanlodhi.newsapplication.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val NewsTypography = Typography(
    headlineLarge = TextStyle(
        fontFamily = FortnightlyFontFamily,
        fontWeight = FontWeight.Black,
        fontSize = 30.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = FortnightlyFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    )
)
