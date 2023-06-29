package com.krakert.tracker.data.tracker.mapper

import com.krakert.tracker.data.extension.requireNotNull
import com.krakert.tracker.data.tracker.entity.MarketDataEntity
import com.krakert.tracker.data.tracker.entity.database.DBMarketDataEntity
import com.krakert.tracker.domain.tracker.model.CoinDetails
import com.krakert.tracker.domain.tracker.model.MarketData
import javax.inject.Inject

class MarketDataMapper @Inject constructor(
    private val currencyMapper: CurrencyMapper
) {
    fun mapApiToDomain(entity: MarketDataEntity): MarketData {
        return MarketData(
            currentPrice = currencyMapper.map(entity.current_price),
            priceChange24h = currencyMapper.map(entity.price_change_24h_in_currency),
            priceChangePercentage24h = entity.price_change_percentage_24h.requireNotNull(),
            priceChangePercentage7d = entity.price_change_percentage_7d.requireNotNull(),
            circulatingSupply = entity.circulating_supply.requireNotNull(),
            high24h = currencyMapper.map(entity.high_24h),
            low24h = currencyMapper.map(entity.low_24h),
            marketCap = currencyMapper.map(entity.market_cap),
            marketCapChangePercentage24h = currencyMapper.map(entity.market_cap_change_percentage_24h_in_currency),
            lastUpdated = entity.last_updated.requireNotNull()
        )
    }

//    fun mapDatabaseToDomain(dbMarketDataEntity: DBMarketDataEntity): MarketData {
//        return MarketData(
//            currentPrice = currencyMapper.mapDatabaseToDomain(dbMarketDataEntity.current_price),
//            priceChange24hInCurrency = currencyMapper.mapDatabaseToDomain(dbMarketDataEntity.price_change_24h_in_currency),
//            priceChangePercentage24h = dbMarketDataEntity.price_change_percentage_24h.requireNotNull(),
//            priceChangePercentage7d = dbMarketDataEntity.price_change_percentage_7d.requireNotNull(),
//            circulatingSupply = dbMarketDataEntity.circulating_supply.requireNotNull(),
//            high24h = currencyMapper.mapDatabaseToDomain(dbMarketDataEntity.high_24h),
//            low24h = currencyMapper.mapDatabaseToDomain(dbMarketDataEntity.low_24h),
//            marketCap = currencyMapper.mapDatabaseToDomain(dbMarketDataEntity.market_cap),
//            marketCapChangePercentage24hInCurrency = currencyMapper.mapDatabaseToDomain(dbMarketDataEntity.market_cap_change_percentage_24h_in_currency),
//            lastUpdated = dbMarketDataEntity.last_updated.requireNotNull()
//        )
//    }

//    fun mapApiToDatabase(coinDetails: CoinDetails): DBMarketDataEntity {
//        return DBMarketDataEntity(
//            current_price = currencyMapper.mapDomainToDatabase(coinDetails.marketData.currentPrice),
//            price_change_24h_in_currency = currencyMapper.mapDomainToDatabase(coinDetails.marketData.priceChange24hInCurrency),
//            price_change_percentage_24h = coinDetails.marketData.priceChangePercentage24h,
//            price_change_percentage_7d = coinDetails.marketData.priceChangePercentage7d,
//            circulating_supply = coinDetails.marketData.circulatingSupply,
//            high_24h = currencyMapper.mapDomainToDatabase(coinDetails.marketData.high24h),
//            low_24h = currencyMapper.mapDomainToDatabase(coinDetails.marketData.low24h),
//            market_cap = currencyMapper.mapDomainToDatabase(coinDetails.marketData.marketCap),
//            market_cap_change_percentage_24h_in_currency = currencyMapper.mapDomainToDatabase(coinDetails.marketData.marketCapChangePercentage24hInCurrency),
//            last_updated = coinDetails.marketData.lastUpdated
//        )
//    }
}