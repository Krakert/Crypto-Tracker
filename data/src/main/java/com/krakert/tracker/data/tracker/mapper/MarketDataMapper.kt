package com.krakert.tracker.data.tracker.mapper

import com.krakert.tracker.data.extension.requireNotNull
import com.krakert.tracker.data.tracker.entity.MarketDataEntity
import com.krakert.tracker.domain.tracker.model.MarketData
import javax.inject.Inject

class MarketDataMapper @Inject constructor(
    private val currencyMapper: CurrencyMapper
) {
    fun mapApiToDomain(entity: MarketDataEntity): MarketData {
        return MarketData(
            currentPrice = currencyMapper.map(entity.currentPrice),
            priceChange24h = currencyMapper.map(entity.priceChange24hInCurrency),
            priceChangePercentage24h = entity.priceChangePercentage24h.requireNotNull(),
            priceChangePercentage7d = entity.priceChangePercentage7d.requireNotNull(),
            circulatingSupply = entity.circulatingSupply.requireNotNull(),
            high24h = currencyMapper.map(entity.high24h),
            low24h = currencyMapper.map(entity.low24h),
            marketCap = currencyMapper.map(entity.marketCap),
            marketCapChangePercentage24h = currencyMapper.map(entity.marketCapChangePercentage24hInCurrency),
            lastUpdated = entity.lastUpdated.requireNotNull()
        )
    }
}