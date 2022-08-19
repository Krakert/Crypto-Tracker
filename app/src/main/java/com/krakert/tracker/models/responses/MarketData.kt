package com.krakert.tracker.models.responses

import com.google.gson.annotations.SerializedName


data class MarketData(
    @SerializedName("current_price")
    val currentPrice: Map<String, Double>? = null,
    val ath: Map<String, Double>? = null,
    @SerializedName("ath_change_percentage")
    val athChangePercentage: Map<String, Double>? = null,
    @SerializedName("ath_date")
    val athDate: Map<String, String>? = null,
    @SerializedName("market_cap")
    val marketCap: Map<String, Double>? = null,
    @SerializedName("market_cap_rank")
    val marketCapRank: Long = 0,
    @SerializedName("total_volume")
    val totalVolume: Map<String, Double>? = null,
    @SerializedName("high_24h")
    val high24h: Map<String, Double> = emptyMap(),
    @SerializedName("low_24h")
    val low24h: Map<String, Double> = emptyMap(),
    @SerializedName("price_change_24h")
    val priceChange24h: Double = 0.0,
    @SerializedName("price_change_percentage_24h")
    val priceChangePercentage24h: Double = 0.0,
    @SerializedName("price_change_percentage_7d")
    val priceChangePercentage7d: Double = 0.0,
    @SerializedName("price_change_percentage_14d")
    val priceChangePercentage14d: Double = 0.0,
    @SerializedName("price_change_percentage_30d")
    val priceChangePercentage30d: Double = 0.0,
    @SerializedName("price_change_percentage_60d")
    val priceChangePercentage60d: Double = 0.0,
    @SerializedName("price_change_percentage_200d")
    val priceChangePercentage200d: Double = 0.0,
    @SerializedName("price_change_percentage_1y")
    val priceChangePercentage1y: Double = 0.0,
    @SerializedName("market_cap_change_24h")
    val marketCapChange24h: Double = 0.0,
    @SerializedName("market_cap_change_percentage_24h")
    val marketCapChangePercentage24h: Double = 0.0,
    @SerializedName("price_change_24h_in_currency")
    val priceChange24hInCurrency: Map<String, Double>? = null,
    @SerializedName("price_change_percentage_1h_in_currency")
    val priceChangePercentage1hInCurrency: Map<String, Double>? = null,
    @SerializedName("price_change_percentage_24h_in_currency")
    val priceChangePercentage24hInCurrency: Map<String, Double>? = null,
    @SerializedName("price_change_percentage_7d_in_currency")
    val priceChangePercentage7dInCurrency: Map<String, Double>? = null,
    @SerializedName("price_change_percentage_14d_in_currency")
    val priceChangePercentage14dInCurrency: Map<String, Double>? = null,
    @SerializedName("price_change_percentage_30d_in_currency")
    val priceChangePercentage30dInCurrency: Map<String, Double>? = null,
    @SerializedName("price_change_percentage_60d_in_currency")
    val priceChangePercentage60dInCurrency: Map<String, Double>? = null,
    @SerializedName("price_change_percentage_200d_in_currency")
    val priceChangePercentage200dInCurrency: Map<String, Double>? = null,
    @SerializedName("price_change_percentage_1y_in_currency")
    val priceChangePercentage1yInCurrency: Map<String, Double>? = null,
    @SerializedName("market_cap_change_24h_in_currency")
    val marketCapChange24hInCurrency: Map<String, Double>? = null,
    @SerializedName("market_cap_change_percentage_24h_in_currency")
    val marketCapChangePercentage24hInCurrency: Map<String, Double>? = null,
    @SerializedName("total_supply")
    val totalSupply: Double? = null,
    @SerializedName("circulating_supply")
    val circulatingSupply: Double = 0.0,
    @SerializedName("last_updated")
    val lastUpdated: String? = null
)