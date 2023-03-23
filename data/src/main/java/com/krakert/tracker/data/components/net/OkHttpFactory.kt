package com.krakert.tracker.data.components.net

import android.content.Context
import com.krakert.tracker.data.BuildConfig
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OkHttpFactory @Inject constructor(
    @ApplicationContext private val context: Context
) {

    companion object {
        private const val MAX_CACHE_SIZE = 10 * 1024 * 1024L
        private const val NETWORK_TIMEOUT = 10
    }

    fun build(): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .cache(Cache(context.cacheDir, MAX_CACHE_SIZE))
            .connectTimeout(NETWORK_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .readTimeout(NETWORK_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .writeTimeout(NETWORK_TIMEOUT.toLong(), TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            val httpInterceptor = HttpLoggingInterceptor()
            httpInterceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.interceptors().add(httpInterceptor)
        }

        return builder.build()
    }
}
