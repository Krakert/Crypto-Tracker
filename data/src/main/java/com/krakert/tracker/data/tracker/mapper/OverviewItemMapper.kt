package com.krakert.tracker.data.tracker.mapper

import com.krakert.tracker.data.extension.requireNotNull
import com.krakert.tracker.data.tracker.entity.MarketChartEntity
import com.krakert.tracker.domain.tracker.model.CoinOverviewItem
import javax.inject.Inject

class OverviewItemMapper @Inject constructor(
    private val marketChartMapper: MarketChartMapper,
) {
    fun map(
        name: String?,
        id: String?,
        currentPrice: Double?,
        marketChart: MarketChartEntity?,
    ): CoinOverviewItem {
        return CoinOverviewItem(
            name = name.requireNotNull(),
            id = id.requireNotNull(),
            currentPrice = currentPrice.requireNotNull(),
            marketChart = marketChartMapper.map(marketChart.requireNotNull())
        )
    }
}
