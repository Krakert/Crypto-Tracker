package com.krakert.tracker.models

import com.google.gson.annotations.SerializedName

class CoinPriceData(
    @SerializedName("usd")
    val usd: String,
    @SerializedName("usd_24h_change")
    val usd_24h_change: String
)