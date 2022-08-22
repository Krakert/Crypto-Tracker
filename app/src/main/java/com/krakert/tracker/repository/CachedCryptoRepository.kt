package com.krakert.tracker.repository

import android.util.Log
import com.krakert.tracker.api.CoinGeckoDataSource
import com.krakert.tracker.api.Resource
import com.krakert.tracker.database.CryptoCacheDao
import com.krakert.tracker.models.ListCoins
import com.krakert.tracker.models.responses.CoinFullData
import com.krakert.tracker.models.responses.MarketChart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withTimeout
import retrofit2.HttpException
import javax.inject.Inject

class CachedCryptoRepository
    @Inject
    constructor(
        private val coinGeckoDataSource: CoinGeckoDataSource,
        private val cryptoCacheDao: CryptoCacheDao
    ) : CryptoRepository {

    override suspend fun getListCoins(
        currency: String,
        ids: String?,
        order: String,
        perPage: Int,
        page: Int,
    ): Resource<ListCoins> {
        val response = try {
            withTimeout(10_000) {
                coinGeckoDataSource.getListCoins(
                    currency = currency,
                    ids = ids,
                    order = order,
                    perPage = perPage,
                    page = page
                )
            }
        } catch (e: HttpException) {
            Log.e("CryptoApiRepository",
                "Retrieval of a list of coins was unsuccessful -> got code: ${e.code()}, details: ${e.message}")
            return Resource.Error(e.code().toString())
        }

        return Resource.Success(response)
    }

    override fun getPriceCoins(idCoins: String, currency: String): Flow<Resource<MutableMap<String, MutableMap<String, Any>>>> {
        return flow {
//            emit(fetchTrendingMoviesCached())
            emit(Resource.Loading())
            val result = coinGeckoDataSource.fetchCoinsPriceById(idCoins, currency)

            //Cache to database if response is successful
            if (result is Resource.Success) {
//                result.data.let { it ->
//                    cryptoCacheDao.deleteAll(it)
//                    cryptoCacheDao.insertAll(it)
//                }
            }
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

//    private fun fetchCachedListPrice(): Result<TrendingMovieResponse>? =
//        cryptoCacheDao.getListCoins()?.let {
//            Result.success(TrendingMovieResponse(it))
//        }


    override suspend fun getDetailsCoinByCoinId(
        coinId: String,
        localization: String,
        tickers: Boolean,
        markerData: Boolean,
        communityData: Boolean,
        developerData: Boolean,
        sparkline: Boolean,
    ): Resource<CoinFullData> {
        val response = try {
            withTimeout(10_000) {
                coinGeckoDataSource.getDetailsCoinByCoinId(
                    id = coinId,
                    localization = localization,
                    tickers = tickers,
                    markerData = markerData,
                    communityData = communityData,
                    developerData = developerData,
                    sparkline = sparkline
                )
            }
        } catch (e: HttpException) {
            Log.e("CryptoApiRepository",
                "Retrieval of details of the coin was unsuccessful -> got code: ${e.code()}, details: ${e.message}")
            return Resource.Error(e.code().toString())
        }
        return Resource.Success(response)
    }

    override suspend fun getHistoryByCoinId(
        coinId: String,
        currency: String,
        days: String,
    ): Resource<MarketChart> {
        val response = try {
            withTimeout(10_000) {
                coinGeckoDataSource.getHistoryByCoinId(
                    id = coinId,
                    currency = currency,
                    days = days
                )
            }
        } catch (e: HttpException) {
            Log.e("CryptoApiRepository",
                "Retrieval of history data of coin was unsuccessful -> got code: ${e.code()}, details: ${e.message}")
            return Resource.Error(e.code().toString())
        }
        return Resource.Success(response)
    }


    class CoinGeckoExceptionError(message: String) : Exception(message)

    override fun shouldFetch(): Boolean {
        TODO("Not yet implemented")
    }
}