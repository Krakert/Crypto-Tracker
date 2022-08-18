package com.krakert.tracker.models

class FavoriteCoins: ArrayList<FavoriteCoin>()

data class FavoriteCoin(
    val id: String,
    val name: String
)