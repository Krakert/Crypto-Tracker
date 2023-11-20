package com.krakert.tracker.data.components.net


import com.krakert.tracker.data.components.net.model.ApiMethod
import com.krakert.tracker.data.components.net.model.ApiRequest
import com.krakert.tracker.data.components.net.model.Response
import io.ktor.client.HttpClient
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.request
import io.ktor.http.HttpMethod
import io.ktor.http.takeFrom
import io.ktor.util.InternalAPI
import javax.inject.Inject

class KtorRequest @Inject constructor(
    private val client: HttpClient,
) {

    @OptIn(InternalAPI::class)
    suspend fun <T> request(request: ApiRequest<T>): Response<T> {
        val response = client.request {
            method(request.method)
            url(request.url, request.path)
            if (request.requestBody != null) {
                body = request.requestBody
            }

            request.parameters?.forEach {
                url.parameters.append(it.key, it.value.toString())
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
