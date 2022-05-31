package com.krakert.tracker.model

// Result of the Firebase call
data class FavoriteCoins (
    val Coins: List<FavoriteCoin>? = null
)
data class FavoriteCoin (
    val id: String? = null,
    val idCoin: String? = null,
    val name: String? = null,
    val symbol: String? = null
)