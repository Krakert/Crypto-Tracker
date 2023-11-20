package com.krakert.tracker.data.tracker.mapper

import com.krakert.tracker.data.tracker.entity.FavoriteCoinEntity
import com.krakert.tracker.domain.tracker.model.ListFavouriteCoins
import com.krakert.tracker.domain.tracker.model.ListFavouriteCoinsItem
import javax.inject.Inject

class FavouriteCoinsMapper @Inject constructor(
    private val favouriteCoinMapper: FavouriteCoinMapper,
) {
    fun map(favoriteCoinEntities: List<FavoriteCoinEntity>): ListFavouriteCoins {
        val coins = mutableListOf<ListFavouriteCoinsItem>()
        favoriteCoinEntities.forEach { coins.add(favouriteCoinMapper.map(it)) }
        return ListFavouriteCoins(result = coins)
    }
}
