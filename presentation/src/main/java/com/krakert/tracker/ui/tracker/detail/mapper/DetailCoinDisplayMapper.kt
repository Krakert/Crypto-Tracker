package com.krakert.tracker.ui.tracker.detail.mapper

import com.krakert.tracker.domain.tracker.model.CoinDetails
import com.krakert.tracker.ui.tracker.detail.model.DetailCoinDisplay
import javax.inject.Inject

class DetailCoinDisplayMapper @Inject constructor(
    private val marketDataDisplayMapper: MarketDataDisplayMapper
) {
    fun map(details: CoinDetails): DetailCoinDisplay {
        return  DetailCoinDisplay(
            id = details.id,
            name = details.name,
            imageUrl = details.images.large,
            symbol = details.symbol,
            marketData = marketDataDisplayMapper.map(details.marketData)
        )
    }
}