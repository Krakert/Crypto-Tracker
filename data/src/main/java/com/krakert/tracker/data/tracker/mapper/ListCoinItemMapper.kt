package com.krakert.tracker.data.tracker.mapper

import com.krakert.tracker.data.extension.requireNotNull
import com.krakert.tracker.data.tracker.entity.FavoriteCoinEntity
import com.krakert.tracker.data.tracker.entity.ListCoinsItemEntity
import com.krakert.tracker.domain.tracker.model.ListCoinsItem
import javax.inject.Inject

class ListCoinItemMapper @Inject constructor() {
    fun mapApiToDomain(
        entity: ListCoinsItemEntity?,
        listFavoriteCoins: List<FavoriteCoinEntity>
    ): ListCoinsItem {
        return ListCoinsItem(
            id = entity?.id.requireNotNull(),
            imageUrl = entity?.image.requireNotNull(),
            name = entity?.name.requireNotNull(),
            isFavouriteCoin = listFavoriteCoins.contains(
                FavoriteCoinEntity(
                    id = entity?.id,
                    name = entity?.name?.lowercase(),
                )
            )
        )
    }
}
