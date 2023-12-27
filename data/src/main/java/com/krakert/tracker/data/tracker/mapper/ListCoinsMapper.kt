package com.krakert.tracker.data.tracker.mapper

import com.krakert.tracker.data.extension.requireNotNull
import com.krakert.tracker.data.tracker.entity.FavoriteCoinEntity
import com.krakert.tracker.data.tracker.entity.ListCoinsItemEntity
import com.krakert.tracker.domain.tracker.model.ListCoins
import com.krakert.tracker.domain.tracker.model.ListCoinsItem
import com.krakert.tracker.domain.tracker.model.ListFavouriteCoinsItem
import javax.inject.Inject

class ListCoinsMapper @Inject constructor(
    private val listCoinItemMapper: ListCoinItemMapper,
) {
    fun map(
        entities: List<ListCoinsItemEntity?>,
        listFavoriteCoins: List<FavoriteCoinEntity>,
    ): ListCoins {
        return ListCoins(
            result = entities.map {
                listCoinItemMapper.mapApiToDomain(it, listFavoriteCoins)
            }
        )
    }

    fun remap(
        entities: List<ListCoinsItem>,
        listFavoriteCoins: List<ListFavouriteCoinsItem>
    ): ListCoins {
        return ListCoins(
            result = entities.map {
                listCoinItemReMapper(it, listFavoriteCoins)
            }
        )
    }

    private fun listCoinItemReMapper(
        entity: ListCoinsItem,
        listFavoriteCoins: List<ListFavouriteCoinsItem>
    ): ListCoinsItem {
        return ListCoinsItem(
            id = entity.id.requireNotNull(),
            imageUrl = entity.imageUrl.requireNotNull(),
            name = entity.name.requireNotNull(),
            isFavouriteCoin = listFavoriteCoins.contains(
                ListFavouriteCoinsItem(
                    id = entity.id,
                    name = entity.name.lowercase(),
                )
            )
        )
    }
}
