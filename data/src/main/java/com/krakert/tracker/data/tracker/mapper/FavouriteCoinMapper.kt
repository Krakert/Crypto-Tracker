package com.krakert.tracker.data.tracker.mapper

import com.krakert.tracker.data.extension.requireNotNull
import com.krakert.tracker.data.tracker.entity.FavoriteCoinEntity
import com.krakert.tracker.domain.tracker.model.ListFavouriteCoinsItem
import javax.inject.Inject

class FavouriteCoinMapper @Inject constructor(

) {
    fun map(favoriteCoinEntity: FavoriteCoinEntity): ListFavouriteCoinsItem {
        return ListFavouriteCoinsItem(
            id = favoriteCoinEntity.id.requireNotNull(),
            name = favoriteCoinEntity.name.requireNotNull()
        )
    }
}
