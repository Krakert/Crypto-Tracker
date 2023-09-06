package com.krakert.tracker.data.components.net


import com.krakert.fish.data.component.network.models.ApiMethod
import com.krakert.fish.data.component.network.models.ApiRequest
import com.krakert.fish.data.component.network.models.Response
import io.ktor.client.HttpClient
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.request
import io.ktor.client.request.url
import io.ktor.http.HttpMethod
import io.ktor.http.takeFrom
import io.ktor.util.InternalAPI
import javax.inject.Inject

class KtorRequest @Inject constructor(
    private val client: HttpClient,
) : Api {

    @OptIn(InternalAPI::class)
    override suspend fun <T>request(request: ApiRequest<T>): Response<T> {
        val response = client.request {
            method(request.method)
            url(request.url, request.path)
            if (request.requestBody != null) {
                body = request.requestBody
            }
            if (request.parameters?.isNotEmpty() == true) {
                request.parameters.forEach {
                    url.parameters.append(it.key, it.value)
                }
            }
        }
        return Response(response)
    }

    @OptIn(InternalAPI::class)
    override suspend fun <T>requestNoBaseURL(request: ApiRequest<T>): Response<T> {
        val response = client.request {
            method(request.method)
            url(request.path)
            if (request.requestBody != null) {
                body = request.requestBody
            }
            if (request.parameters?.isNotEmpty() == true) {
                request.parameters.forEach {
                    url.parameters.append(it.key, it.value)
                }
            }
        }
        return Response(response)
    }

    private fun HttpRequestBuilder.method(originalMethod: ApiMethod) {
        method = when (originalMethod) {
            ApiMethod.GET -> HttpMethod.Get
            ApiMethod.POST -> HttpMethod.Post
            ApiMethod.PUT -> HttpMethod.Put
            ApiMethod.DELETE -> HttpMethod.Delete
        }
    }

    private fun HttpRequestBuilder.url(newUrl: String, path: String) = url {
        takeFrom(newUrl + path)
    }
}
