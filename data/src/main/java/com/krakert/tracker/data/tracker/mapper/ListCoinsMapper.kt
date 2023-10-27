package com.krakert.tracker.data.tracker.mapper

import com.krakert.tracker.data.extension.requireNotNull
import com.krakert.tracker.data.tracker.entity.FavoriteCoinEntity
import com.krakert.tracker.data.tracker.entity.ListCoinsItemEntity
import com.krakert.tracker.data.tracker.entity.database.DBListCoinItemEntity
import com.krakert.tracker.domain.tracker.model.ListCoins
import javax.inject.Inject

class ListCoinsMapper @Inject constructor(
    private val listCoinItemMapper: ListCoinItemMapper,
) {
    fun mapApiToDomain(
        entities: List<ListCoinsItemEntity?>,
        listFavoriteCoins: List<FavoriteCoinEntity>,
    ): ListCoins {
        return ListCoins(
            result = entities.map {
                listCoinItemMapper.mapApiToDomain(it, listFavoriteCoins)
            }
        )
    }

    fun mapDatabaseToDomain(
        entity: List<DBListCoinItemEntity>,
        listFavoriteCoins: List<FavoriteCoinEntity>,
    ): ListCoins {
        return ListCoins(
            result = entity.map {
                listCoinItemMapper.mapDatabaseToDomain(it, listFavoriteCoins)
            }
        )
    }

    fun mapDomainToDatabase(listCoins: ListCoins): List<DBListCoinItemEntity> {
        val list = ArrayList<DBListCoinItemEntity>()
        listCoins.result.forEach { listCoinItem ->
            list.add(
                DBListCoinItemEntity(
                    id = listCoinItem.id.requireNotNull(),
                    name = listCoinItem.name.requireNotNull(),
                    imageUrl = listCoinItem.imageUrl.requireNotNull()
                )
            )
        }
        return list.toList()
    }
}
