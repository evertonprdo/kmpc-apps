package com.prdo.todolist.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.Font
import todolist.composeapp.generated.resources.Res
import todolist.composeapp.generated.resources.inter_bold
import todolist.composeapp.generated.resources.inter_regular

@Composable
fun getInterFontFamily() = FontFamily(
    Font(Res.font.inter_regular, FontWeight.Normal),
    Font(Res.font.inter_bold, FontWeight.Bold)
)

@Composable
fun getAppTypography(): Typography {
    val interFontFamily = getInterFontFamily()
    val lineHeight = 1.4.em

    val smRegular = TextStyle(
        fontSize = 12.sp,
        lineHeight = lineHeight,
        fontWeight = FontWeight.Normal,
        fontFamily = interFontFamily
    )

    val smBold = TextStyle(
        fontSize = 12.sp,
        lineHeight = lineHeight,
        fontWeight = FontWeight.Bold,
        fontFamily = interFontFamily
    )

    val mdRegular = TextStyle(
        fontSize = 14.sp,
        lineHeight = lineHeight,
        fontWeight = FontWeight.Normal,
        fontFamily = interFontFamily
    )

    val mdBold = TextStyle(
        fontSize = 14.sp,
        lineHeight = lineHeight,
        fontWeight = FontWeight.Bold,
        fontFamily = interFontFamily
    )

    val lgRegular = TextStyle(
        fontSize = 16.sp,
        lineHeight = lineHeight,
        fontWeight = FontWeight.Normal,
        fontFamily = interFontFamily
    )

    val lgBold = TextStyle(
        fontSize = 16.sp,
        lineHeight = lineHeight,
        fontWeight = FontWeight.Bold,
        fontFamily = interFontFamily
    )

    return Typography(
        titleLarge = lgBold,
        titleMedium = mdBold,
        titleSmall = smBold,
        bodyLarge = lgRegular,
        bodyMedium = mdRegular,
        bodySmall = smRegular,
    )
}

