package com.krakert.tracker.data.tracker.mapper

import com.krakert.tracker.domain.tracker.model.CoinOverviewItem
import com.krakert.tracker.domain.tracker.model.MarketChart
import javax.inject.Inject

class OverviewItemMapper @Inject constructor() {
    fun map(): CoinOverviewItem {
        return CoinOverviewItem(
            name = "",
            id = "",
            currentPrice = 0.0,
            MarketChart(emptyList())
        )
    }
}
