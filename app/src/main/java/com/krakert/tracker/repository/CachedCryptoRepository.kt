package com.krakert.tracker.repository

import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
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

const val CACHE_KEY_PRICE_COINS  = "cache_key_prices_coins_data"
const val CACHE_KEY_LIST_COINS   = "cache_key_list_coins_data"
const val CACHE_KEY_DETAILS_COIN = "cache_key_details_coin_data"
const val BASE_CACHE_KET_MARKET_CHART = "cache_key_market_chart_data_"

class CachedCryptoRepository
@Inject
constructor(
    private val coinGeckoDataSource: CoinGeckoDataSource,
    private val cryptoCacheDao: CryptoCacheDao,
    private val sharedPreferences: SharedPreferences,
    private val context: Context
) : CryptoRepository {
    // Setup of the limits for the different data in the DB
    private val cacheRateLimit = CacheRateLimiter<String>(sharedPreferences.MinutesCache, TimeUnit.MINUTES)
    private val cacheRateLimitList = CacheRateLimiter<String>(1, TimeUnit.DAYS)

    override suspend fun getListCoins(
        currency: String,
        ids: String?,
        order: String,
        perPage: Int,
        page: Int,
    ): Flow<Resource<ListCoins>> {
        return flow {
            emit(Resource.Loading())

            if (!cacheRateLimitList.shouldFetch(CACHE_KEY_LIST_COINS, sharedPreferences)) {
                Log.i("cacheRateLimiter: getListCoins", "getting data for: $ids out the DB")
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

            if (!cacheRateLimit.shouldFetch(CACHE_KEY_PRICE_COINS, sharedPreferences)) {
                Log.i("cacheRateLimiter: getPriceCoins", "getting data for: $idCoins out the DB")
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

            if (!cacheRateLimit.shouldFetch(CACHE_KEY_DETAILS_COIN, sharedPreferences)) {
                Log.i("cacheRateLimiter: getDetailsCoinByCoinId", "getting data for: $coinId out the DB")
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

            if (!cacheRateLimit.shouldFetch(BASE_CACHE_KET_MARKET_CHART + coinId, sharedPreferences)) {
                Log.i("cacheRateLimiter: getHistoryByCoinId", "getting data for: $coinId out the DB")
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

    override fun isOnline(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        return capabilities !== null
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
                    Log.i("cacheRateLimiter: fetchHistoryByCoinId", "data is being stored for: $coinId")
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
                    Log.i("cacheRateLimiter: fetchListCoins", "data is being stored for: $ids")
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
                Log.i("cacheRateLimiter: fetchCoinsPriceById", "data is being stored for: $idCoins")
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
                    Log.i("cacheRateLimiter: fetchDetailsCoinByCoinId", "data is being stored for: $coinId")
                    cryptoCacheDao.deleteDetailsCoin(it)
                    cryptoCacheDao.insertDetailsCoin(it)
                }
            }
        }
        return result
    }
}