package com.krakert.tracker.model

data class Coins (
    val Coins: List<Coin>? = null
)
data class Coin (
    val id: String? = null,
    val idCoin: String? = null,
    val name: String? = null,
    val symbol: String? = null
)

data class Favorite (
    val Favorite: List<Coin>? = null
)

data class MarketChart(
    val prices: List<List<String>>,
)