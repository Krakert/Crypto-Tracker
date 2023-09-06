package com.krakert.fish.data.component.network.models

import io.ktor.client.statement.HttpResponse

data class Response<T>(
    val response: HttpResponse
)
