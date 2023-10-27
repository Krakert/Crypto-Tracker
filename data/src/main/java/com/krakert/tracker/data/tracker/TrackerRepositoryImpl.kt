package com.krakert.tracker.data.tracker

import android.content.SharedPreferences
import com.krakert.tracker.data.SharedPreference.MinutesCache
import com.krakert.tracker.data.SharedPreference.getListFavoriteCoins
import com.krakert.tracker.data.components.storage.CacheRateLimiter
import com.krakert.tracker.data.components.storage.TrackerDao
import com.krakert.tracker.data.tracker.PreferencesRepositoryImpl.Companion.CACHE_KEY_LIST_COINS
import com.krakert.tracker.data.tracker.PreferencesRepositoryImpl.Companion.CACHE_KEY_OVERVIEW
import com.krakert.tracker.data.tracker.mapper.ListCoinsMapper
import com.krakert.tracker.domain.tracker.ApiRepository
import com.krakert.tracker.domain.tracker.TrackerRepository
import com.krakert.tracker.domain.tracker.model.CoinDetails
import com.krakert.tracker.domain.tracker.model.CoinOverview
import com.krakert.tracker.domain.tracker.model.ListCoins
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class TrackerRepositoryImpl @Inject constructor(
    private val api: ApiRepository,
    private val database: TrackerDao,
    private val sharedPreferences: SharedPreferences,
    private val listCoinsMapper: ListCoinsMapper

) : TrackerRepository {

    // Setup of the limits for the different data in the DB
    private val cacheRateLimit = CacheRateLimiter(sharedPreferences.MinutesCache, TimeUnit.MINUTES)
    private val cacheRateLimitList = CacheRateLimiter(1, TimeUnit.DAYS)

    override suspend fun getListCoins(): Result<ListCoins> {
        if (!cacheRateLimitList.shouldFetch(CACHE_KEY_LIST_COINS, sharedPreferences)) {
            val responseDatabase = database.getListCoins()
            if (responseDatabase.isNotEmpty()){
                return Result.success(
                    listCoinsMapper.mapDatabaseToDomain(
                        responseDatabase,
                        sharedPreferences.getListFavoriteCoins()
                    )
                )
            }
        } else {
            return api.fetchListCoins() .onSuccess { list ->
                database.insertListCoins(listCoinsMapper.mapDomainToDatabase(list))
            }
        }
        return Result.failure(UnknownError())
    }

    override suspend fun getOverview(): Result<CoinOverview> {
        if (!cacheRateLimit.shouldFetch(CACHE_KEY_OVERVIEW, sharedPreferences)) {

        } else {
            api.fetchOverview()
        }
        return Result.failure(UnknownError())
    }

    override suspend fun getDetailsCoin(coinId: String): Result<CoinDetails> {
        TODO("Not yet implemented")
    }
}