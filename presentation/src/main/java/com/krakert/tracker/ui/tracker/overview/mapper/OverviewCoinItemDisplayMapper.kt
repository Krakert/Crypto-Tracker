package com.krakert.tracker.ui.tracker.overview.mapper

import com.krakert.tracker.domain.tracker.model.CoinOverviewItem
import com.krakert.tracker.ui.tracker.overview.model.OverviewCoinItemDisplay
import java.util.Locale
import javax.inject.Inject

class OverviewCoinItemDisplayMapper @Inject constructor(
    private val marketChartDisplayMapper: MarketChartDisplayMapper
) {
    fun map(item: CoinOverviewItem): OverviewCoinItemDisplay {
        return OverviewCoinItemDisplay(
            id = item.id,
            name = item.name.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() },
            currentPrice = item.currentPrice.toString(),
            marketChart = marketChartDisplayMapper.map(item.marketChart)
        )
    }
}
