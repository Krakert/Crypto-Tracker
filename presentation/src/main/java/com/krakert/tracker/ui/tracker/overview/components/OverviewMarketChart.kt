package com.krakert.tracker.ui.tracker.overview.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.krakert.tracker.ui.theme.themeValues


@Composable
fun OverviewMarketChart(marketChart: Path) {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(105.dp)
    ) {
        // Get the canvas size
        val canvasWidth = size.width

        // Calculate the transformation
        val pathWidth = marketChart.getBounds().width
        val scaleX = canvasWidth / pathWidth

        // Calculate translation factors to center the path horizontally
        val translateX = -(marketChart.getBounds().left * scaleX)

        // Draw the path with the applied transformation
        drawContext.canvas.apply {
            scale(scaleX, 1f)
            translate(translateX, 0f)
            drawPath(
                path = marketChart,
                color = themeValues[3].colors.secondary,
                style = Stroke(width = 6.0f)
            )
        }
    }
}