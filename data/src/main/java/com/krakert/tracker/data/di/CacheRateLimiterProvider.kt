package com.krakert.tracker.data.di

import android.content.SharedPreferences
import com.krakert.tracker.data.SharedPreference.MinutesCache
import com.krakert.tracker.data.components.storage.CacheRateLimiter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CacheRateLimiterProvider {
    @Provides
    @Singleton
    fun provideMinutesCacheRateLimiter(sharedPreferences: SharedPreferences) =
        CacheRateLimiter(
            timeout = sharedPreferences.MinutesCache,
            timeUnit = TimeUnit.MINUTES
        )
}