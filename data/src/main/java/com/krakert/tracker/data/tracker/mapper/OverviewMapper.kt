package com.krakert.tracker.data.tracker.mapper

import com.krakert.tracker.data.tracker.entity.MarketChartEntity
import com.krakert.tracker.domain.tracker.model.CoinOverview
import javax.inject.Inject

class OverviewMapper @Inject constructor(
    private val marketChartMapper: MarketChartMapper,
    private val overviewItemMapper: OverviewItemMapper,
) {
    fun map(PricesCoinsEntity: Map<String, MutableMap<String, Any>>, listMarketChartEntity: List<MarketChartEntity>): CoinOverview {
        return CoinOverview(
            result = arrayListOf()
        )
    }
}