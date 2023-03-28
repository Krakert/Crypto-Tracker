package com.krakert.tracker.data.tracker.mapper

import com.krakert.tracker.data.components.tracker.entity.MarketChartEntity
import com.krakert.tracker.domain.tracker.model.MarketChart
import javax.inject.Inject

class MarketChartMapper @Inject constructor() {

    fun map(entity: MarketChartEntity): MarketChart {
        return MarketChart(
            result = listOf()
        )
    }
}
