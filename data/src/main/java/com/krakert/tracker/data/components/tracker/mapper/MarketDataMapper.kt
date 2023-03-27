package com.krakert.tracker.data.components.tracker.mapper

import com.krakert.tracker.data.components.extension.requireNotNull
import com.krakert.tracker.data.components.tracker.entity.MarketDataEntity
import com.krakert.tracker.data.components.tracker.entity.database.DBMarketDataEntity
import com.krakert.tracker.domain.tracker.model.CoinDetails
import com.krakert.tracker.domain.tracker.model.MarketData
import javax.inject.Inject

class MarketDataMapper @Inject constructor(
) {
    fun mapApiToDomain(entity: MarketDataEntity): MarketData {
        return MarketData(
            currentPrice = entity.currentPrice.requireNotNull(),
            priceChange24h = entity.priceChange24h.requireNotNull(),
            priceChangePercentage24h = entity.priceChangePercentage24h.requireNotNull(),
            priceChangePercentage7d = entity.priceChangePercentage7d.requireNotNull(),
            circulatingSupply = entity.circulatingSupply.requireNotNull(),
            high24h = entity.high24h.requireNotNull(),
            low24h = entity.low24h.requireNotNull(),
            marketCap = entity.marketCap.requireNotNull(),
            marketCapChangePercentage24h = entity.marketCapChangePercentage24h.requireNotNull(),
        )
    }

    fun mapDatabaseToDomain(dbMarketDataEntity: DBMarketDataEntity): MarketData {
        return MarketData(
            currentPrice = dbMarketDataEntity.currentPrice.requireNotNull(),
            priceChange24h = dbMarketDataEntity.priceChange24h.requireNotNull(),
            priceChangePercentage24h = dbMarketDataEntity.priceChangePercentage24h.requireNotNull(),
            priceChangePercentage7d = dbMarketDataEntity.priceChangePercentage7d.requireNotNull(),
            circulatingSupply = dbMarketDataEntity.circulatingSupply.requireNotNull(),
            high24h = dbMarketDataEntity.high24h.requireNotNull(),
            low24h = dbMarketDataEntity.low24h.requireNotNull(),
            marketCap = dbMarketDataEntity.marketCap.requireNotNull(),
            marketCapChangePercentage24h = dbMarketDataEntity.marketCapChangePercentage24h.requireNotNull(),
        )
    }

    fun mapDomainToDatabase(coinDetails: CoinDetails): DBMarketDataEntity {
        return DBMarketDataEntity(
            id = coinDetails.id,
            currentPrice = coinDetails.marketData.currentPrice,
            priceChange24h = coinDetails.marketData.priceChange24h,
            priceChangePercentage24h = coinDetails.marketData.priceChangePercentage24h,
            priceChangePercentage7d = coinDetails.marketData.priceChangePercentage7d,
            circulatingSupply = coinDetails.marketData.circulatingSupply,
            high24h = coinDetails.marketData.high24h,
            low24h = coinDetails.marketData.low24h,
            marketCap = coinDetails.marketData.marketCap,
            marketCapChangePercentage24h = coinDetails.marketData.marketCapChangePercentage24h,
        )
    }
}