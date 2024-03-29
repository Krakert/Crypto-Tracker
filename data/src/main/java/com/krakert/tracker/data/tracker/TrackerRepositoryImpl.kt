package com.krakert.tracker.data.tracker

import com.krakert.tracker.data.components.storage.CacheRateLimiter
import com.krakert.tracker.data.tracker.PreferencesRepositoryImpl.Companion.CACHE_KEY_DETAILS_COIN
import com.krakert.tracker.data.tracker.PreferencesRepositoryImpl.Companion.CACHE_KEY_LIST_COINS
import com.krakert.tracker.data.tracker.PreferencesRepositoryImpl.Companion.CACHE_KEY_OVERVIEW
import com.krakert.tracker.data.tracker.mapper.ListCoinsMapper
import com.krakert.tracker.domain.tracker.ApiRepository
import com.krakert.tracker.domain.tracker.PreferencesRepository
import com.krakert.tracker.domain.tracker.TrackerRepository
import com.krakert.tracker.domain.tracker.model.CoinDetails
import com.krakert.tracker.domain.tracker.model.CoinOverview
import com.krakert.tracker.domain.tracker.model.ListCoins
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TrackerRepositoryImpl @Inject constructor(
    private val api: ApiRepository,
    private val preferencesRepository: PreferencesRepository,
    private val cacheRateLimiter: CacheRateLimiter,
    private val listCoinsMapper: ListCoinsMapper,
) : TrackerRepository {

    // Cache of the API calls
    private lateinit var listCoins: ListCoins
    private lateinit var coinOverview: CoinOverview
    private lateinit var coinDetails: CoinDetails

    // Store the ID of the last details call
    private var prevCoinId = ""

    override suspend fun getListCoins(): Flow<Result<ListCoins>> {
        return flow {
            if (cacheRateLimiter.shouldFetch(CACHE_KEY_LIST_COINS)) {
                api.fetchListCoins()
                    .onSuccess {
                        listCoins = it
                        emit(Result.success(listCoins))
                    }
                    .onFailure {
                        if (!::listCoins.isInitialized) {
                            cacheRateLimiter.removeForKey(CACHE_KEY_LIST_COINS)
                        }
                        emit(Result.failure(it))
                    }
            } else {
                if (!::listCoins.isInitialized) {
                    cacheRateLimiter.removeForKey(CACHE_KEY_LIST_COINS)
                    emit(Result.failure(Throwable()))
                } else {
                    preferencesRepository.getFavouriteCoins().mapCatching {
                        listCoins = listCoinsMapper.remap(listCoins.result, it.result)
                        emit(Result.success(listCoins))
                    }
                }
            }
        }
    }

    override suspend fun getOverview(): Flow<Result<CoinOverview>> {
        return flow {
            if (cacheRateLimiter.shouldFetch(CACHE_KEY_OVERVIEW)) {
                api.fetchOverview()
                    .onSuccess {
                        coinOverview = it
                        emit(Result.success(coinOverview))
                    }
                    .onFailure {
                        emit(Result.failure(it))
                    }
            } else {
                if (!::coinOverview.isInitialized) {
                    cacheRateLimiter.removeForKey(CACHE_KEY_OVERVIEW)
                    emit(Result.failure(Throwable()))
                } else {
                    emit(Result.success(coinOverview))
                }
            }
        }
    }

    override suspend fun getDetailsCoin(coinId: String): Flow<Result<CoinDetails>> {
        return flow {
            if (prevCoinId != coinId) {
                cacheRateLimiter.shouldFetch(CACHE_KEY_DETAILS_COIN)
                api.fetchDetailsCoin(coinId = coinId)
                    .onSuccess {
                        coinDetails = it
                        emit(Result.success(coinDetails))
                        prevCoinId = coinId
                    }
                    .onFailure {
                        emit(Result.failure(it))
                    }
            } else {
                if (cacheRateLimiter.shouldFetch(CACHE_KEY_DETAILS_COIN)) {
                    api.fetchDetailsCoin(coinId = coinId)
                        .onSuccess {
                            coinDetails = it
                            emit(Result.success(coinDetails))
                        }
                        .onFailure {
                            emit(Result.failure(it))
                        }
                } else {
                    if (!::coinDetails.isInitialized) {
                        cacheRateLimiter.removeForKey(CACHE_KEY_DETAILS_COIN)
                        emit(Result.failure(Throwable()))
                    } else {
                        emit(Result.success(coinDetails))
                    }
                }
            }
        }
    }
}