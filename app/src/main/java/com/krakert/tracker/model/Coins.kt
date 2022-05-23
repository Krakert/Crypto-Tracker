package com.krakert.tracker.model

data class Coins (
    val Coins: List<Coin>? = null
)
data class Coin (
    val id: String? = null,
    val name: String? = null,
    val symbol: String? = null
)