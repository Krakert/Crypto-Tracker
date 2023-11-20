package com.krakert.tracker.ui.tracker.detail.mapper

import com.krakert.tracker.domain.tracker.model.MarketData
import com.krakert.tracker.ui.tracker.detail.model.DetailCoinMarketDataDisplay
import com.krakert.tracker.ui.tracker.formatter.CurrencyDisplayFormatter
import com.krakert.tracker.ui.tracker.formatter.DoubleDisplayFormatter
import javax.inject.Inject

class MarketDataDisplayMapper @Inject constructor(
    private val currencyDisplayFormatter: CurrencyDisplayFormatter,
    private val doubleDisplayFormatter: DoubleDisplayFormatter
) {

    fun map(marketData: MarketData, symbol: String): DetailCoinMarketDataDisplay {
        return DetailCoinMarketDataDisplay(
            currentPrice = currencyDisplayFormatter.format(
                symbol,
                marketData.currentPrice
            ),
            marketCap = doubleDisplayFormatter.format(
                formatter = "%,.2f",
                value = marketData.marketCap
            ),
            high24h = currencyDisplayFormatter.format(
                symbol,
                marketData.high24h
            ),
            low24h = currencyDisplayFormatter.format(
                symbol,
                marketData.low24h
            ),
            priceChange24h = marketData.priceChange24h,
            priceChangePercentage24h = marketData.priceChangePercentage24h,
            priceChangePercentage7d = marketData.priceChangePercentage7d,
            marketCapChangePercentage24h = marketData.marketCapChangePercentage24h,
            circulatingSupply = doubleDisplayFormatter.format(
                formatter = "%,.0f",
                value = marketData.circulatingSupply
            ),
            lastUpdated = marketData.lastUpdated
        )
    }
}
