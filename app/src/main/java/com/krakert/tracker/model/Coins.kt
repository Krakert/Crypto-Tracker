package com.krakert.tracker.model

data class Coins (
    val coin: List<String>
)
data class Coin (
    val id: String,
    val name: String,
    val symbol: String
)