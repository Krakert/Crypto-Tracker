package com.krakert.tracker.ui.tracker.detail.model

data class DetailCoinDisplay(
    val id: String,
    val symbol: String,
    val name: String,
    val imageUrl: String,
    val marketData: DetailCoinMarketDataDisplay,
)
