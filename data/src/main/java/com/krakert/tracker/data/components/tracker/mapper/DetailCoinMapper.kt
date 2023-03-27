package com.krakert.tracker.data.components.tracker.mapper

import com.krakert.tracker.data.components.extension.requireNotNull
import com.krakert.tracker.data.components.tracker.entity.CoinCurrentDataEntity
import com.krakert.tracker.data.components.tracker.entity.database.DBCoinCurrentDataEntity
import com.krakert.tracker.domain.tracker.model.CoinDetails
import javax.inject.Inject

class DetailCoinMapper @Inject constructor(
    private val marketDataMapper: MarketDataMapper,
    private val imageMapper: ImageMapper
) {
    fun mapApiToDomain(entity: CoinCurrentDataEntity): CoinDetails {
        return CoinDetails(
            id = entity.id.requireNotNull(),
            symbol = entity.symbol.requireNotNull(),
            name = entity.name.requireNotNull(),
            images = entity.images.requireNotNull().let { imageMapper.mapApiToDomain(it) },
            marketData = entity.marketData.requireNotNull().let { marketDataMapper.mapApiToDomain(it) },
        )
    }

    fun mapDatabaseToDomain(dbCoinCurrentDataEntity: DBCoinCurrentDataEntity) : CoinDetails {
        return CoinDetails(
            id = dbCoinCurrentDataEntity.id.requireNotNull(),
            symbol = dbCoinCurrentDataEntity.symbol.requireNotNull(),
            name = dbCoinCurrentDataEntity.name.requireNotNull(),
            images = dbCoinCurrentDataEntity.images.requireNotNull().let { imageMapper.mapDatabaseToDomain(it) },
            marketData = dbCoinCurrentDataEntity.marketData.requireNotNull().let { marketDataMapper.mapDatabaseToDomain(it) }
        )
    }

    fun mapDomainToDatabase(coinDetails: CoinDetails) : DBCoinCurrentDataEntity {
        return DBCoinCurrentDataEntity(
            id = coinDetails.id,
            symbol = coinDetails.symbol,
            name = coinDetails.name,
            images = coinDetails.images.let { imageMapper.mapDomainToDatabase(it) }.requireNotNull(),
            marketData = coinDetails.marketData.let { marketDataMapper.mapDomainToDatabase(coinDetails) }
        )
    }


}