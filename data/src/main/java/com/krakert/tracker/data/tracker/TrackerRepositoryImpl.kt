package com.krakert.tracker.data.tracker

import android.content.Context
import android.content.SharedPreferences
import com.krakert.tracker.data.SharedPreference.AmountDaysTracking
import com.krakert.tracker.data.SharedPreference.Currency
import com.krakert.tracker.data.SharedPreference.FavoriteCoins
import com.krakert.tracker.data.SharedPreference.MinutesCache
import com.krakert.tracker.data.SharedPreference.TileCoin
import com.krakert.tracker.data.SharedPreference.getListFavoriteCoins
import com.krakert.tracker.data.components.net.KtorRequest
import com.krakert.tracker.data.components.net.mapper.ResponseMapper
import com.krakert.tracker.data.components.storage.CacheRateLimiter
import com.krakert.tracker.data.components.storage.TrackerDao
import com.krakert.tracker.data.extension.guard
import com.krakert.tracker.data.tracker.api.ApiCalls
import com.krakert.tracker.data.tracker.entity.FavoriteCoinEntity
import com.krakert.tracker.data.tracker.entity.MarketChartEntity
import com.krakert.tracker.data.tracker.mapper.DetailCoinMapper
import com.krakert.tracker.data.tracker.mapper.FavouriteCoinsMapper
import com.krakert.tracker.data.tracker.mapper.ListCoinsMapper
import com.krakert.tracker.data.tracker.mapper.OverviewMapper
import com.krakert.tracker.domain.tracker.TrackerRepository
import com.krakert.tracker.domain.tracker.model.CoinOverview
import com.krakert.tracker.domain.tracker.model.ListCoins
import com.krakert.tracker.domain.tracker.model.ListFavouriteCoins
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
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
    private val favouriteCoinsMapper: FavouriteCoinsMapper,
) : TrackerRepository {

    companion object {
        const val CACHE_KEY_OVERVIEW = "cache_key_overview_data"
        const val CACHE_KEY_PRICE_COINS = "cache_key_prices_coins_data"
        const val CACHE_KEY_LIST_COINS = "cache_key_list_coins_data"
        const val CACHE_KEY_DETAILS_COIN = "cache_key_details_coin_data"
    }

    // Setup of the limits for the different data in the DB
    private val cacheRateLimit =
        CacheRateLimiter<String>(sharedPreferences.MinutesCache, TimeUnit.MINUTES)
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
        return entity.mapCatching {
            listCoinsMapper.mapApiToDomain(
                it,
                sharedPreferences.getListFavoriteCoins()
            )
        }
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
    override suspend fun getOverview(): Result<CoinOverview> {
        val favoriteCoins = sharedPreferences.getListFavoriteCoins()
        val responsePriceCoins = Result.runCatching {
            ktor.request(
                ApiCalls.getPriceByListCoinIds(
                    ids = favoriteCoins.mapNotNull { it.id }.joinToString(","),
                    currency = sharedPreferences.Currency
                )
            )
        }.guard { return it }
        val entityPriceCoins = responseMapper.map(responsePriceCoins)
        val listEntityMarketChart = arrayListOf<MarketChartEntity>()
        favoriteCoins.forEach { coin ->
            if (!coin.id.isNullOrBlank()) {
                val responseMarketChart = Result.runCatching {
                    ktor.request(
                        ApiCalls.getHistoryByCoinId(
                            coinId = coin.id,
                            currency = sharedPreferences.Currency,
                            days = sharedPreferences.AmountDaysTracking.toString()
                        )
                    )
                }.guard { return it }
                listEntityMarketChart.add(
                    responseMapper.map(responseMarketChart).guard { return it })
            }
        }
        return Result.runCatching {
            overviewMapper.map(
                favoriteCoins = favoriteCoins,
                tileCoin = sharedPreferences.TileCoin,
                pricesCoinsEntity = entityPriceCoins.guard { return it },
                listMarketChartEntity = listEntityMarketChart,
                currency = sharedPreferences.Currency,
            )
        }
    }

    override suspend fun addFavouriteCoin(id: String, name: String): Result<Boolean> {

        val itemToStore = FavoriteCoinEntity(id = id, name = name.lowercase())
        val dataSharedPreferences = sharedPreferences.getListFavoriteCoins()

        if (!dataSharedPreferences.contains(itemToStore)) {
            val listFavoriteCoins = dataSharedPreferences.toMutableList()
            listFavoriteCoins.add(itemToStore)
            Result.runCatching {
                sharedPreferences.FavoriteCoins = Json.encodeToString(listFavoriteCoins)
            }.guard { return it }
        }

        //cacheRateLimit.removeForKey(sharedPreferences, CACHE_KEY_PRICE_COINS)

        return Result.success(true)
    }

    override suspend fun removeFavouriteCoin(id: String, name: String): Result<Boolean> {

        val itemToRemove = FavoriteCoinEntity(id = id, name = name.lowercase())
        val dataSharedPreferences = sharedPreferences.getListFavoriteCoins()

        if (dataSharedPreferences.contains(itemToRemove)) {
            val listFavoriteCoins = dataSharedPreferences.toMutableList()
            listFavoriteCoins.remove(itemToRemove)
            Result.runCatching {
                sharedPreferences.FavoriteCoins = Json.encodeToString(listFavoriteCoins)
            }.guard { return it }
        }

        //cacheRateLimit.removeForKey(sharedPreferences, CACHE_KEY_PRICE_COINS)

        return Result.success(true)
    }

    override suspend fun getFavouriteCoins(): Result<ListFavouriteCoins> {
        val data = Result.runCatching {
            sharedPreferences.getListFavoriteCoins()
        }
        return data.mapCatching { favouriteCoinsMapper.map(it) }
    }


//    override fun isOnline(): Boolean {
//        val connectivityManager =
//            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//        val capabilities =
//            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
//        return capabilities !== null
//    }
}