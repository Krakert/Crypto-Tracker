package com.krakert.tracker.data.components.net

import javax.inject.Inject
import javax.inject.Singleton
import kotlin.reflect.KClass

@Singleton
class ApiFactory @Inject constructor(
    private val retrofitFactory: RetrofitFactory
) {

    inline fun <reified T : Any> createApi(baseUrl: String): T {
        return createApi(baseUrl, T::class)
    }

    fun <T : Any> createApi(baseUrl: String, kClass: KClass<T>): T {
        return retrofitFactory.build(baseUrl).create(kClass.java)
    }
}