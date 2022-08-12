package com.krakert.tracker.model

class FavoriteCoins: ArrayList<FavoriteCoin>()

data class FavoriteCoin(
    val id: String,
    val name: String
)