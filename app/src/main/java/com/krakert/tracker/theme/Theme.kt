package com.krakert.tracker.theme

import androidx.compose.runtime.Composable
import androidx.wear.compose.material.Colors
import androidx.wear.compose.material.MaterialTheme

@Composable
fun WearAppTheme(
    colors: Colors = themeValues[3].colors,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = colors,
        typography = WearTypography,
        // For shapes, we generally recommend using the default Material Wear shapes which are
        // optimized for round and non-round devices.
        content = content
    )
}