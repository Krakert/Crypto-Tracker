package com.krakert.tracker.data.tracker

import android.content.Context
import android.content.SharedPreferences
import com.krakert.tracker.data.SharedPreference.Currency
import com.krakert.tracker.data.SharedPreference.MinutesCache
import com.krakert.tracker.data.SharedPreference.getListFavoriteCoins
import com.krakert.tracker.data.components.net.KtorRequest
import com.krakert.tracker.data.components.net.mapper.ResponseMapper
import com.krakert.tracker.data.components.storage.CacheRateLimiter
import com.krakert.tracker.data.components.storage.TrackerDao
import com.krakert.tracker.data.extension.guard
import com.krakert.tracker.data.tracker.api.ApiCalls
import com.krakert.tracker.data.tracker.mapper.DetailCoinMapper
import com.krakert.tracker.data.tracker.mapper.ListCoinsMapper
import com.krakert.tracker.data.tracker.mapper.OverviewMapper
import com.krakert.tracker.domain.tracker.TrackerRepository
import com.krakert.tracker.domain.tracker.model.ListCoins
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class TrackerRepositoryImpl @Inject constructor(
    private val ktor: KtorRequest,
    private val cryptoCacheDao: TrackerDao,
    private val sharedPreferences: SharedPreferences,
    private val context: Context,
    private val responseMapper: ResponseMapper,
    private val listCoinsMapper: ListCoinsMapper,
    private val detailCoinMapper: DetailCoinMapper,
    private val overviewMapper: OverviewMapper,
) : TrackerRepository {

    companion object {
        const val CACHE_KEY_OVERVIEW = "cache_key_overview_data"
        const val CACHE_KEY_LIST_COINS = "cache_key_list_coins_data"
        const val CACHE_KEY_DETAILS_COIN = "cache_key_details_coin_data"
    }

    // Setup of the limits for the different data in the DB
    private val cacheRateLimit = CacheRateLimiter<String>(sharedPreferences.MinutesCache, TimeUnit.MINUTES)
    private val cacheRateLimitList = CacheRateLimiter<String>(1, TimeUnit.DAYS)

    override suspend fun getListCoins(): Result<ListCoins> {
//        if (!cacheRateLimitList.shouldFetch(CACHE_KEY_LIST_COINS, sharedPreferences)) {
//            val responseDatabase = cryptoCacheDao.getListCoins()
//            if (responseDatabase.isNotEmpty()) return Result.success(
//                listCoinsMapper.mapDatabaseToDomain(
//                    responseDatabase
//                )
//            )
//        }

        val response = Result.runCatching {
            ktor.request(
                ApiCalls.getListCoins(
                    currency = sharedPreferences.Currency
                )
            )
        }.guard { return it }
        val entity = responseMapper.map(response)
        return entity.mapCatching { listCoinsMapper.mapApiToDomain(it) }
    }


//    override suspend fun getDetailsCoin(coinId: String): Result<CoinDetails> {
//        if (!cacheRateLimit.shouldFetch(CACHE_KEY_DETAILS_COIN, sharedPreferences)) {
//            val responseDatabase = cryptoCacheDao.getDetailsCoin(coinId)
//            if (responseDatabase != null) return Result.success(
//                detailCoinMapper.mapDatabaseToDomain(
//                    responseDatabase
//                )
//            )
//        }
//        val response =
//            Result.runCatching { coinGeckoApi.fetchDetailsCoinByCoinId(coinId) }.guard { return it }
//        val entity = responseMapper.map(response)
//        //        cryptoCacheDao.insertDetailsCoin()
//        return entity.mapCatching { detailCoinMapper.mapApiToDomain(it) }
//    }
//
//    override suspend fun getOverview(): Result<CoinOverview> {
//        val responsePriceCoins = Result.runCatching {
//            coinGeckoApi.fetchCoinsPriceById(
//                getCoinsIdString(),
//                sharedPreferences.Currency
//            )
//        }.guard { return it }
//        val entityPriceCoins = responseMapper.map(responsePriceCoins)
//        val listEntityMarketChart = arrayListOf<MarketChartEntity>()
//        sharedPreferences.getListFavoriteCoins().forEach {
//            val response = Result.runCatching {
//                coinGeckoApi.fetchHistoryByCoinId(
//                    it.id.toString(),
//                    sharedPreferences.Currency,
//                    sharedPreferences.AmountDaysTracking.toString()
//                )
//            }.guard { return it }
//            listEntityMarketChart.add(responseMapper.map(response).guard { return it })
//        }
//        return Result.runCatching {
//            overviewMapper.map(
//                entityPriceCoins.guard { return it },
//                listEntityMarketChart
//            )
//        }
//    }
//
    private fun getCoinsIdString(): String {
        return sharedPreferences.getListFavoriteCoins().joinToString(",")
    }
//
//
//    override fun isOnline(): Boolean {
//        val connectivityManager =
//            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//        val capabilities =
//            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
//        return capabilities !== null
//    }
}