package com.krakert.tracker.data.tracker.api

import com.krakert.tracker.data.components.net.model.ApiMethod
import com.krakert.tracker.data.components.net.model.ApiRequest
import com.krakert.tracker.data.components.net.model.Query
import com.krakert.tracker.data.tracker.entity.CoinCurrentDataEntity
import com.krakert.tracker.data.tracker.entity.ListCoinsItemEntity
import com.krakert.tracker.data.tracker.entity.MarketChartEntity

object ApiCalls {

    fun getListCoins(
        currency: String = "usd",
        order: String = "market_cap_desc",
        perPage: Int = 250,
        page: Int = 1,
    ) = ApiRequest<List<ListCoinsItemEntity?>>(
        method = ApiMethod.GET,
        path = "coins/markets",
        parameters = listOf(
            Query("vs_currency", currency),
            Query("order", order),
            Query("per_page", perPage),
            Query("page", page),
        )
    )

    fun getPriceByListCoinIds(
        ids: String,
        currency: String,
        marketCap: String = "",
        dayVol: String = "",
        dayChange: String = "",
        lastUpdated: String = ""
    ) = ApiRequest<Map<String, Map<String, Double>>>(
        method = ApiMethod.GET,
        path = "simple/price",
        parameters = listOf(
            Query("ids", ids),
            Query("vs_currencies", currency),
            Query("include_market_cap", marketCap),
            Query("include_24hr_vol", dayVol),
            Query("include_24hr_change", dayChange),
            Query("include_last_updated_at", lastUpdated),
            Query("precision", "full"),
        )
    )

    fun getHistoryByCoinId(
        coinId: String,
        currency: String,
        days: String
    ) = ApiRequest<MarketChartEntity>(
        method = ApiMethod.GET,
        path = "coins/$coinId/market_chart",
        parameters = listOf(
            Query("vs_currency", currency),
            Query("days", days),
        )
    )

    fun getDetailsCoinByCoinId(
        coinId: String,
        localization: String = "false",
        tickers: Boolean = false,
        markerData: Boolean = true,
        communityData: Boolean = false,
        developerData: Boolean = false,
        sparkline: Boolean = false
    ) = ApiRequest<CoinCurrentDataEntity>(
        method = ApiMethod.GET,
        path = "coins/$coinId",
        parameters = listOf(
            Query("localization", localization),
            Query("tickers", tickers),
            Query("market_data", markerData),
            Query("community_data", communityData),
            Query("developer_data", developerData),
            Query("sparkline", sparkline),
        )
    )
}