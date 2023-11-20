package com.krakert.tracker.data.tracker.mapper

import com.krakert.tracker.data.tracker.entity.FavoriteCoinEntity
import com.krakert.tracker.data.tracker.entity.ListCoinsItemEntity
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
}
