package com.krakert.tracker.data.components.net.model

import com.krakert.tracker.data.components.DataConfig.COIN_GECKO_API_BASE_URL


data class ApiRequest<T>(
    val method: ApiMethod,
    val url: String = COIN_GECKO_API_BASE_URL,
    val path: String,
    val requestBody: Any? = null,
    val parameters: List<Query>? = null
)
