package com.krakert.tracker.ui.tracker.overview.mapper

import android.graphics.PointF
import androidx.compose.ui.graphics.Path
import com.krakert.tracker.domain.tracker.model.MarketChart
import javax.inject.Inject

class MarketChartDisplayMapper @Inject constructor() {

    fun map(marketChart: MarketChart): Path {
        val path = Path()
        val points = ArrayList<PointF>()
        val pointsCon1 = ArrayList<PointF>()
        val pointsCon2 = ArrayList<PointF>()

        val maxData = marketChart.result.maxBy { it.price }.price
        val minData = marketChart.result.minBy { it.price }.price

        val pointsMean = marketChart.result.chunked(5) {
            it.map { marketData -> marketData.price }.average().toFloat()
        }

        val distance = 400 / (pointsMean.size + 1)
        var currentX = 0F

        pointsMean.forEach { point ->
            val y = (point - maxData) / (minData - maxData) * 200
            val x = currentX + distance
            points.add(PointF(x, y))
            currentX += distance
        }

        for (i in 1 until points.size) {
            pointsCon1.add(PointF((points[i].x + points[i - 1].x) / 2, points[i - 1].y))
            pointsCon2.add(PointF((points[i].x + points[i - 1].x) / 2, points[i].y))
        }

        path.moveTo(points.first().x, points.first().y)
        for (i in 1 until points.size) {
            path.cubicTo(
                pointsCon1[i - 1].x,
                pointsCon1[i - 1].y,
                pointsCon2[i - 1].x,
                pointsCon2[i - 1].y,
                points[i].x,
                points[i].y
            )
        }
        return path
    }
}