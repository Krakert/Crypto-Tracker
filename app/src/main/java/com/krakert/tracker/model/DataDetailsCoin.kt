package com.krakert.tracker.model

import android.media.Image


data class DataDetailsCoin(
    val name: String,
    val image: Image,
    val priceChangePercentage24h: Double,
    val priceChangePercentage7d: Double,
    val priceCurrent: Double,
    val circulatingSupply: Double,
    val high24h: Double,
    val low24h: Double,
    val marketCap: Double,
    val marketCapChangePercentage24h: Double
)
