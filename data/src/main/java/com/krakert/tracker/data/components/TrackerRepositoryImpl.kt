package com.krakert.tracker.data.components

import android.content.Context
import android.content.SharedPreferences
import com.krakert.tracker.SharedPreference.AmountDaysTracking
import com.krakert.tracker.SharedPreference.Currency
import com.krakert.tracker.SharedPreference.MinutesCache
import com.krakert.tracker.SharedPreference.getListFavoriteCoins
import com.krakert.tracker.data.components.extension.guard
import com.krakert.tracker.data.components.net.mapper.ResponseMapper
import com.krakert.tracker.data.components.storage.CacheRateLimiter
import com.krakert.tracker.data.components.storage.TrackerDao
import com.krakert.tracker.data.components.tracker.api.CoinGeckoApi
import com.krakert.tracker.data.components.tracker.entity.MarketChartEntity
import com.krakert.tracker.data.components.tracker.mapper.DetailCoinMapper
import com.krakert.tracker.data.components.tracker.mapper.ListCoinsMapper
import com.krakert.tracker.data.components.tracker.mapper.OverviewMapper
import com.krakert.tracker.domain.tracker.TrackerRepository
import com.krakert.tracker.domain.tracker.model.CoinDetails
import com.krakert.tracker.domain.tracker.model.CoinOverview
import com.krakert.tracker.domain.tracker.model.ListCoins
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class TrackerRepositoryImpl @Inject constructor(
    private val coinGeckoApi: CoinGeckoApi,
    private val cryptoCacheDao: TrackerDao,
    private val sharedPreferences: SharedPreferences,
    private val context: Context,
    private val responseMapper: ResponseMapper,
    private val listCoinsMapper: ListCoinsMapper,
    private val detailCoinMapper: DetailCoinMapper,
    private val overviewMapper: OverviewMapper,
) : TrackerRepository {

    companion object {
        const val CACHE_KEY_PRICE_COINS = "cache_key_prices_coins_data"
        const val CACHE_KEY_OVERVIEW = "cache_key_overview_data"
        const val CACHE_KEY_LIST_COINS = "cache_key_list_coins_data"
        const val CACHE_KEY_DETAILS_COIN = "cache_key_details_coin_data"
        const val BASE_CACHE_KET_MARKET_CHART = "cache_key_market_chart_data_"
    }

    // Setup of the limits for the different data in the DB
    private val cacheRateLimit = CacheRateLimiter<String>(sharedPreferences.MinutesCache, TimeUnit.MINUTES)
    private val cacheRateLimitList = CacheRateLimiter<String>(1, TimeUnit.DAYS)

    override suspend fun getListCoins(
        currency: String,
        ids: String?,
        order: String,
        perPage: Int,
        page: Int,
    ): Result<ListCoins> {
        if (!cacheRateLimitList.shouldFetch(CACHE_KEY_LIST_COINS, sharedPreferences)) {
            val responseDatabase = cryptoCacheDao.getListCoins()
            if (responseDatabase.isNotEmpty()) return Result.success(
                listCoinsMapper.mapDatabaseToDomain(
                    responseDatabase
                )
            )
        }

        val response = Result.runCatching { coinGeckoApi.getListCoins(currency, ids, order, perPage, page) }.guard { return it }
        val entity = responseMapper.map(response)
        //        cryptoCacheDao.deleteListCoins(entity)
        //        cryptoCacheDao.insertListCoins()
        return entity.mapCatching { listCoinsMapper.mapApiToDomain(it) }
    }


    override suspend fun getDetailsCoin(coinId: String): Result<CoinDetails> {
        if (!cacheRateLimit.shouldFetch(CACHE_KEY_DETAILS_COIN, sharedPreferences)) {
            val responseDatabase = cryptoCacheDao.getDetailsCoin(coinId)
            if (responseDatabase != null) return Result.success(detailCoinMapper.mapDatabaseToDomain(responseDatabase))
        }
        val response = Result.runCatching { coinGeckoApi.getDetailsCoin(coinId) }.guard { return it }
        val entity = responseMapper.map(response)
        //        cryptoCacheDao.insertDetailsCoin()
        return entity.mapCatching { detailCoinMapper.mapApiToDomain(it) }
    }

    override suspend fun getOverview(): Result<CoinOverview> {
        val responsePricesDatabase: Map<String, MutableMap<String, Any>?>?
        if (!cacheRateLimit.shouldFetch(CACHE_KEY_OVERVIEW, sharedPreferences)) {
            responsePricesDatabase = cryptoCacheDao.getPriceCoins()?.data
            if (responsePricesDatabase != null) {

            }
        }
        val responsePriceCoins = Result.runCatching { coinGeckoApi.getPriceByListCoinIds(getCoinsIdString(), sharedPreferences.Currency) }.guard { return it }
        val entityPriceCoins = responseMapper.map(responsePriceCoins)
        val listEntityMarketChart = arrayListOf<MarketChartEntity>()
        sharedPreferences.getListFavoriteCoins().forEach {
            val response = Result.runCatching {
                coinGeckoApi.getHistoryByCoinId(it.id.toString(), sharedPreferences.Currency, sharedPreferences.AmountDaysTracking.toString())
            }. guard { return it }
            listEntityMarketChart.add(responseMapper.map(response).guard { return it })
        }
        return Result.runCatching { overviewMapper.map(entityPriceCoins.guard { return it }, listEntityMarketChart) }
    }

    override fun getCoinsIdString(): String {
        return sharedPreferences.getListFavoriteCoins().joinToString(",")
    }


    override fun isOnline(): Boolean {
//        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//        val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
//        return capabilities !== null
        return true
    }


}