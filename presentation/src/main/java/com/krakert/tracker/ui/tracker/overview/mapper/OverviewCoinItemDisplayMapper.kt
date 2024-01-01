package com.krakert.tracker.ui.tracker.overview.mapper

import android.app.Application
import com.krakert.tracker.domain.tracker.model.CoinOverviewItem
import com.krakert.tracker.presentation.R
import com.krakert.tracker.ui.extension.capitalize
import com.krakert.tracker.ui.tracker.formatter.CurrentPriceFormatter
import com.krakert.tracker.ui.tracker.model.Currency
import com.krakert.tracker.ui.tracker.overview.model.OverviewCoinItemDisplay
import java.util.Locale
import javax.inject.Inject

class OverviewCoinItemDisplayMapper @Inject constructor(
    private val marketChartDisplayMapper: MarketChartDisplayMapper,
    private val currentPriceFormatter: CurrentPriceFormatter,
    private val application: Application
) {
    fun map(item: CoinOverviewItem): OverviewCoinItemDisplay {
        return OverviewCoinItemDisplay(
            id = item.id,
            name = item.name.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() },
            currentPrice = application.getString(
                R.string.txt_value_formatted,
                Currency.entries.find { it.name == item.currency.capitalize() }?.symbol
                    ?: item.currency,
                currentPriceFormatter.format(item.currentPrice)
            ),
            marketChart = marketChartDisplayMapper.map(item.marketChart)
        )
    }
}
