package com.krakert.tracker.data.components.net

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RetrofitFactory @Inject constructor(
    private val okHttpFactory: OkHttpFactory
) {

    fun build(url: String): Retrofit {
        return Retrofit.Builder().run {
            baseUrl(url)
            addConverterFactory(GsonConverterFactory.create())
            client(okHttpFactory.build())
        }.build()
    }
}