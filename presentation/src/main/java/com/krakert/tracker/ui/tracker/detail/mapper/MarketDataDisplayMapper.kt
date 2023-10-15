package com.krakert.tracker.ui.tracker.detail.mapper

import com.krakert.tracker.domain.tracker.model.MarketData
import com.krakert.tracker.ui.tracker.detail.model.DetailCoinMarketDataDisplay
import javax.inject.Inject

class MarketDataDisplayMapper @Inject constructor() {

    fun map(marketData: MarketData): DetailCoinMarketDataDisplay {
        return DetailCoinMarketDataDisplay(
            currentPrice = marketData.currentPrice,
            marketCap = marketData.marketCap,
            high24h = marketData.high24h,
            low24h = marketData.low24h,
            priceChange24h = marketData.priceChange24h,
            priceChangePercentage24h = marketData.priceChangePercentage24h,
            priceChangePercentage7d = marketData.priceChangePercentage7d,
            marketCapChangePercentage24h = marketData.marketCapChangePercentage24h,
            circulatingSupply = marketData.circulatingSupply,
            lastUpdated = marketData.lastUpdated
        )
    }
}
