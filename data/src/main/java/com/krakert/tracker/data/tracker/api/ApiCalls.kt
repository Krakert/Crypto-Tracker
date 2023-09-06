package com.krakert.tracker.data.tracker.api

import com.krakert.tracker.data.components.net.model.ApiMethod
import com.krakert.tracker.data.components.net.model.ApiRequest
import com.krakert.tracker.data.components.net.model.Query
import com.krakert.tracker.data.tracker.entity.ListCoinsEntity
import com.krakert.tracker.data.tracker.entity.ListCoinsItemEntity
import com.krakert.tracker.data.tracker.entity.TEST
import com.krakert.tracker.data.tracker.entity.TESTItem

object ApiCalls {

    fun getListCoins(
        currency: String = "usd",
        order: String = "market_cap_desc",
        perPage: Int = 100,
        page: Int = 1,
    ) = ApiRequest<List<ListCoinsItemEntity?>>(
        method = ApiMethod.GET, path = "coins/markets", parameters = listOf(
            Query("vs_currency", currency),
            Query("order", order),
            Query("per_page", perPage),
            Query("page", page),
        )
    )
}