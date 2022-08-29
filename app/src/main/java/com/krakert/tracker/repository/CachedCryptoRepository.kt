package com.krakert.tracker.repository

import android.content.SharedPreferences
import android.util.Log
import com.krakert.tracker.SharedPreference.MinutesCache
import com.krakert.tracker.api.CacheRateLimiter
import com.krakert.tracker.api.CoinGeckoDataSource
import com.krakert.tracker.api.Resource
import com.krakert.tracker.database.CryptoCacheDao
import com.krakert.tracker.models.database.DataGraph
import com.krakert.tracker.models.database.PriceCoins
import com.krakert.tracker.models.responses.CoinFullData
import com.krakert.tracker.models.responses.ListCoins
import com.krakert.tracker.models.responses.MarketChart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class CachedCryptoRepository
@Inject
constructor(
    private val coinGeckoDataSource: CoinGeckoDataSource,
    private val cryptoCacheDao: CryptoCacheDao,
    private val sharedPreferences: SharedPreferences,
) : CryptoRepository {
    // Setup of the limits for the different data in the DB
    private val cacheRateLimit = CacheRateLimiter<String>(sharedPreferences.MinutesCache, TimeUnit.MINUTES)
    private val cacheRateLimitList = CacheRateLimiter<String>(1, TimeUnit.DAYS)

    private val cacheKeyPricesCoins = "cache_key_prices_coins_data"
    private val cacheKeyListCoins = "cache_key_list_coins_data"
    private val cacheKeyDetailsCoin = "cache_key_details_coin_data"
    private val cacheKeyMarketChart = "cache_ket_market_chart_data"

    override suspend fun getListCoins(
        currency: String,
        ids: String?,
        order: String,
        perPage: Int,
        page: Int,
    ): Flow<Resource<ListCoins>> {
        return flow {
            emit(Resource.Loading())

            if (!cacheRateLimitList.shouldFetch(cacheKeyListCoins, sharedPreferences)) {
                Log.i("cacheRateLimit: getListCoins", "data should be fetched")
                val dbResult = cryptoCacheDao.getListCoins()
                if (dbResult.isNotEmpty()) {
                    val listFlow = ListCoins()
                    dbResult.forEach {
                        listFlow.add(it)
                    }
                    // Emit data form the DB
                    emit(Resource.Success(listFlow))
                    // If result could not be found in the DB, fetch from API
                } else emit(fetchListCoins(currency, ids, order, perPage, page))
                // Data needs to be fetched, to old!
            } else emit(fetchListCoins(currency, ids, order, perPage, page))
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getPriceCoins(
        idCoins: String,
        currency: String,
    ): Flow<Resource<Map<String, MutableMap<String, Any>>>> {
        return flow {
            emit(Resource.Loading())

            if (!cacheRateLimit.shouldFetch(cacheKeyPricesCoins, sharedPreferences)) {
                Log.i("cacheRateLimit: getPriceCoins", "data should be fetched")
                val dbResult = cryptoCacheDao.getPriceCoins()?.data
                if (dbResult != null) {
                    // Emit data form the DB
                    if (dbResult.isNotEmpty()) emit(Resource.Success(dbResult))
                    // If result could not be found in the DB, fetch from API
                } else emit(fetchCoinsPriceById(idCoins, currency))
                // Data needs to be fetched, to old!
            } else emit(fetchCoinsPriceById(idCoins, currency))

        }.flowOn(Dispatchers.IO)
    }


    override suspend fun getDetailsCoinByCoinId(
        coinId: String,
    ): Flow<Resource<CoinFullData>> {
        return flow {
            emit(Resource.Loading())

            if (!cacheRateLimit.shouldFetch(cacheKeyDetailsCoin, sharedPreferences)) {
                Log.i("cacheRateLimit: getDetailsCoinByCoinId", "data should be fetched for: $coinId")
                val dbResult = cryptoCacheDao.getDetailsCoin(coinId)
                // Emit data form the DB
                if (dbResult != null) emit(Resource.Success(dbResult))
                // If result could not be found in the DB, fetch from API
                else emit(fetchDetailsCoinByCoinId(coinId))
                // Data needs to be fetched, to old!
            } else emit(fetchDetailsCoinByCoinId(coinId))
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getHistoryByCoinId(
        coinId: String,
        currency: String,
        days: String,
    ): Flow<Resource<MarketChart>> {
        return flow {
            emit(Resource.Loading())

            if (!cacheRateLimit.shouldFetch(cacheKeyMarketChart, sharedPreferences)) {
                Log.i("cacheRateLimit: getHistoryByCoinId", "data should be fetched for: $coinId")
                val dbResult = cryptoCacheDao.getMarketChartCoin(coinId = coinId)?.data
                if (dbResult != null) {
                    // Emit data form the DB
                    if (dbResult.prices.isNotEmpty()) emit(Resource.Success(dbResult))
                    // If result could not be found in the DB, fetch from API
                } else emit(fetchHistoryByCoinId(coinId, currency, days))
                // Data needs to be fetched, to old!
            } else emit(fetchHistoryByCoinId(coinId, currency, days))
        }.flowOn(Dispatchers.IO)
    }

    private suspend fun fetchHistoryByCoinId(
        coinId: String,
        currency: String,
        days: String,
    ): Resource<MarketChart> {
        val result = coinGeckoDataSource.fetchHistoryByCoinId(coinId, currency, days)

        if (result is Resource.Success) {
            result.data.let {
                if (it != null) {
                    Log.i("cacheRateLimit: fetchHistoryByCoinId", "data is being stored for: $coinId")
                    cryptoCacheDao.deleteMarketChartCoin(DataGraph(coinId, it))
                    cryptoCacheDao.insertMarketChartCoin(DataGraph(coinId, it))
                }
            }

        }
        return result
    }

    private suspend fun fetchListCoins(
        currency: String,
        ids: String?,
        order: String,
        perPage: Int,
        page: Int,
    ): Resource<ListCoins> {
        val result = coinGeckoDataSource.fetchListCoins(currency, ids, order, perPage, page)

        if (result is Resource.Success) {
            result.data.let {
                if (it != null) {
                    Log.i("cacheRateLimit: FetchListCoins", "data is being stored")
                    cryptoCacheDao.deleteListCoins(it)
                    cryptoCacheDao.insertListCoins(it)
                }
            }
        }
        return result
    }

    private suspend fun fetchCoinsPriceById(
        idCoins: String,
        currency: String,
    ): Resource<Map<String, MutableMap<String, Any>>> {
        val result = coinGeckoDataSource.fetchCoinsPriceById(idCoins, currency)

        if (result is Resource.Success) {
            if (result.data?.isNotEmpty() == true) {
                Log.i("cacheRateLimit: getPriceCoins", "data is being stored for: $idCoins")
                cryptoCacheDao.deletePriceCoins()
                cryptoCacheDao.insertPriceCoins(PriceCoins(result.data))
            }
        }
        return result
    }

    private suspend fun fetchDetailsCoinByCoinId(coinId: String): Resource<CoinFullData> {
        val result = coinGeckoDataSource.fetchDetailsCoinByCoinId(coinId = coinId)

        if (result is Resource.Success) {
            result.data.let {
                if (it != null) {
                    Log.i("cacheRateLimit: getDetailsCoinByCoinId", "data is being stored for: $coinId")
                    cryptoCacheDao.deleteDetailsCoin(it)
                    cryptoCacheDao.insertDetailsCoin(it)
                }
            }
        }
        return result
    }
}