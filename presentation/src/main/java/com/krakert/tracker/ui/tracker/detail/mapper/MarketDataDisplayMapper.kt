package com.krakert.tracker.ui.tracker.detail.mapper

import android.app.Application
import com.krakert.tracker.domain.tracker.model.MarketData
import com.krakert.tracker.presentation.R
import com.krakert.tracker.ui.tracker.detail.model.DetailCoinMarketDataDisplay
import com.krakert.tracker.ui.tracker.formatter.CurrentPriceFormatter
import javax.inject.Inject

class MarketDataDisplayMapper @Inject constructor(
    private val currentPriceFormatter: CurrentPriceFormatter,
    private val application: Application,
) {

    fun map(marketData: MarketData, symbol: String): DetailCoinMarketDataDisplay {
        return DetailCoinMarketDataDisplay(
            currentPrice = application.getString(
                R.string.txt_value_formatted,
                symbol,
                currentPriceFormatter.format(marketData.currentPrice)
            ),
            marketCap = currentPriceFormatter.format(marketData.marketCap),
            high24h = application.getString(
                R.string.txt_value_formatted, symbol, marketData.high24h.toString()
            ),
            low24h = application.getString(
                R.string.txt_value_formatted, symbol, marketData.low24h.toString()
            ),
            priceChange24h = marketData.priceChange24h,
            priceChangePercentage24h = marketData.priceChangePercentage24h,
            priceChangePercentage7d = marketData.priceChangePercentage7d,
            marketCapChangePercentage24h = marketData.marketCapChangePercentage24h,
            circulatingSupply = currentPriceFormatter.format(marketData.circulatingSupply),
            lastUpdated = marketData.lastUpdated
        )
    }
}
