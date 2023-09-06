package com.krakert.tracker.data.components.net.model

import io.ktor.client.statement.HttpResponse

data class Response<T>(
    val response: HttpResponse
)
