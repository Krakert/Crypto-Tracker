package com.krakert.tracker.models

import com.google.gson.annotations.SerializedName
import com.krakert.tracker.models.data.Image
import com.krakert.tracker.models.data.Links

data class CoinFullData(
    @SerializedName("id")
    val id: String,
    @SerializedName("symbol")
    val symbol: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("hashing_algorithm")
    val hashingAlgorithm: String? = null,
    @SerializedName("block_time_in_minutes")
    val blockTimeInMinutes: Long = 0,
    @SerializedName("links")
    val links: Links,
    @SerializedName("image")
    val image: Image,
    @SerializedName("country_origin")
    val countryOrigin: String? = null,
    @SerializedName("genesis_date")
    val genesisDate: String? = null,
    @SerializedName("contract_address")
    val contractAddress: String? = null,
    @SerializedName("market_cap_rank")
    val marketCapRank: Long = 0,
    @SerializedName("coingecko_rank")
    val coingeckoRank: Long = 0,
    @SerializedName("coingecko_score")
    val coingeckoScore: Double = 0.0,
    @SerializedName("developer_score")
    val developerScore: Double = 0.0,
    @SerializedName("community_score")
    val communityScore: Double = 0.0,
    @SerializedName("liquidity_score")
    val liquidityScore: Double = 0.0,
    @SerializedName("public_interest_score")
    val publicInterestScore: Double = 0.0,
    @SerializedName("market_data")
    val marketData: MarketData? = null,
    @SerializedName("last_updated")
    val lastUpdated: String? = null,
    @SerializedName("sentiment_votes_up_percentage")
    val sentimentVotesUpPercentage: Float = 50f,
    @SerializedName("sentiment_votes_down_percentage")
    val sentimentVotesDownPercentage: Float = 50f,
    @SerializedName("asset_platform_id")
    val assetPlatformId: String?,
    @SerializedName("public_notice")
    val publicNotice: String? = null,
    @SerializedName("additional_notices")
    val additionalNotices: List<String> = emptyList(),
)