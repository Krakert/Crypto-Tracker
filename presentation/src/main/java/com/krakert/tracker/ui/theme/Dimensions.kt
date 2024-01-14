package com.krakert.tracker.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.MaterialTheme

class Dimensions {
    val radiusSmall: Dp = 4.dp
    val radiusMedium: Dp = 8.dp
    val radiusLarge: Dp = 16.dp
    val radiusExtraLarge: Dp = 24.dp
    val spacingExtraSmall: Dp = 2.dp
    val spacingSmall: Dp = 4.dp
    val spacingMedium: Dp = 8.dp
    val spacingLarge: Dp = 12.dp
    val spacingExtraLarge: Dp = 20.dp
}

class CustomDimensions {
    val detailImageSize: Dp = 40.dp
}

private val localMaterialDimensions = staticCompositionLocalOf { Dimensions() }

val LocalDimensions = staticCompositionLocalOf { CustomDimensions() }

val MaterialTheme.dimensions: Dimensions
    @Composable
    @ReadOnlyComposable
    get() = localMaterialDimensions.current