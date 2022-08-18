package com.krakert.tracker.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CoinGeckoApi {
    companion object {
        private const val baseUrl = "https://api.coingecko.com/api/v3/"

        fun createApi(): CoinGeckoApiService {
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build()

            val coinGeckoApi = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return coinGeckoApi.create(CoinGeckoApiService::class.java)
        }
    }
}