package com.krakert.tracker.repository

import android.content.SharedPreferences
import android.util.Log
import com.krakert.tracker.api.CacheRateLimiter
import com.krakert.tracker.api.CoinGeckoDataSource
import com.krakert.tracker.api.Resource
import com.krakert.tracker.database.CryptoCacheDao
import com.krakert.tracker.models.responses.ListCoins
import com.krakert.tracker.models.responses.CoinFullData
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
    private val cacheRateLimit = CacheRateLimiter<String>(1, TimeUnit.MINUTES)
    private val cacheRateLimitListCoins = CacheRateLimiter<String>(10, TimeUnit.MINUTES)

    private val cacheKeyOverview = "cache_key_overview_data"
    private val cacheKeyListCoins = "cache_key_list_data"

    override suspend fun getListCoins(
        currency: String,
        ids: String?,
        order: String,
        perPage: Int,
        page: Int,
    ): Flow<Resource<ListCoins>> {
        return flow {
            emit(Resource.Loading())

            if(!cacheRateLimitListCoins.shouldFetch(cacheKeyListCoins, sharedPreferences)) {
                Log.i("cacheRateLimit", "data should be fetched")
                val dbResult = cryptoCacheDao.getListCoins()
                val listFlow = ListCoins()

                dbResult.forEach {
                    listFlow.add(it)
                }

                emit(Resource.Success(listFlow))
            }

            val result = coinGeckoDataSource.getListCoins(
                currency = currency,
                ids = ids,
                order = order,
                perPage = perPage,
                page = page
            )

            if (result is Resource.Success){
                result.data.let {
                    if (it != null) {
                        Log.i("cacheRateLimit", "data is being stored")
                        cryptoCacheDao.deleteAll(it)
                        cryptoCacheDao.insertAll(it)
                    }

                }
            }

            emit(result)

        }.flowOn(Dispatchers.IO)
    }

    override fun getPriceCoins(
        idCoins: String,
        currency: String
    ): Flow<Resource<Map<String, MutableMap<String, Any>>>> {
        return flow {
            emit(Resource.Loading())

            if(!cacheRateLimit.shouldFetch(cacheKeyOverview, sharedPreferences)) {
                //TODO: return here flow with DAO response

                // return ..
            }

            //else we fetch data and store it in cachedb

            val result = coinGeckoDataSource.fetchCoinsPriceById(idCoins, currency)

            //Cache to database if response is successful
            if (result is Resource.Success) {
                //TODO: create db that allows String, MutableMap<String, Any> - to be stored
//                result.data.let { it ->
//                    cryptoCacheDao.deleteAll(it)
//                    cryptoCacheDao.insertAll(it)
//                }
            }
            emit(result)
        }.flowOn(Dispatchers.IO)
    }


    override suspend fun getDetailsCoinByCoinId(
        coinId: String,
    ): Flow<Resource<CoinFullData>> {
        return flow {
            emit(Resource.Loading())
            val result = coinGeckoDataSource.fetchDetailsCoinByCoinId(coinId = coinId)

            if (result is Resource.Success) {
//                result.data.let { it ->
//                    cryptoCacheDao.deleteAll(it)
//                    cryptoCacheDao.insertAll(it)
//                }
            }
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getHistoryByCoinId(
        coinId: String,
        currency: String,
        days: String,
    ): Flow<Resource<MarketChart>> {
        return flow {
            emit(Resource.Loading())
            val result = coinGeckoDataSource.fetchHistoryByCoinId(
                coinId = coinId,
                currency = currency,
                days = days
            )

            if (result is Resource.Success) {
//                result.data.let { it ->
//                    cryptoCacheDao.deleteAll(it)
//                    cryptoCacheDao.insertAll(it)
//                }
            }
            emit(result)
        }.flowOn(Dispatchers.IO)
    }


}