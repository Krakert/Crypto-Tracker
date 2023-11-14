package com.krakert.tracker.data.tracker

import android.content.SharedPreferences
import com.krakert.tracker.data.SharedPreference.AmountDaysTracking
import com.krakert.tracker.data.SharedPreference.Currency
import com.krakert.tracker.data.SharedPreference.FavoriteCoins
import com.krakert.tracker.data.SharedPreference.MinutesCache
import com.krakert.tracker.data.SharedPreference.getListFavoriteCoins
import com.krakert.tracker.data.components.storage.CacheRateLimiter
import com.krakert.tracker.data.tracker.entity.FavoriteCoinEntity
import com.krakert.tracker.data.tracker.mapper.FavouriteCoinsMapper
import com.krakert.tracker.domain.tracker.PreferencesRepository
import com.krakert.tracker.domain.tracker.model.ListFavouriteCoins
import com.krakert.tracker.domain.tracker.model.Preferences
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class PreferencesRepositoryImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val favouriteCoinsMapper: FavouriteCoinsMapper,
) : PreferencesRepository {

    companion object {
        const val CACHE_KEY_OVERVIEW = "cache_key_overview_data"
        const val CACHE_KEY_PRICE_COINS = "cache_key_prices_coins_data"
        const val CACHE_KEY_LIST_COINS = "cache_key_list_coins_data"
        const val CACHE_KEY_DETAILS_COIN = "cache_key_details_coin_data"
        const val BASE_CACHE_KET_MARKET_CHART = "cache_key_market_chart_data_"
    }

    private val cacheRateLimit = CacheRateLimiter(sharedPreferences.MinutesCache, TimeUnit.MINUTES)

    override fun setAmountDaysTracking(days: Int) {
        sharedPreferences.AmountDaysTracking = days
        clearKeysForMarketChart()
    }

    override fun resetSettings() {
        sharedPreferences.edit().clear().apply()
    }

    override fun setCacheRate(minutes: Int) {
        sharedPreferences.MinutesCache = minutes
    }

    override fun setCurrency(currency: String) {
        sharedPreferences.Currency = currency.lowercase()
        cacheRateLimit.removeForKey(sharedPreferences, CACHE_KEY_PRICE_COINS)
        cacheRateLimit.removeForKey(sharedPreferences, CACHE_KEY_DETAILS_COIN)
    }

    override fun getAllPreferences(): Preferences {
        return Preferences(
            marketChartAmountDays = sharedPreferences.AmountDaysTracking,
            currency = sharedPreferences.Currency,
            cacheRateTime = sharedPreferences.MinutesCache
        )
    }

    override fun addFavouriteCoin(id: String, name: String) {

        val itemToStore = FavoriteCoinEntity(id = id, name = name.lowercase())
        val dataSharedPreferences = sharedPreferences.getListFavoriteCoins()

        if (!dataSharedPreferences.contains(itemToStore)) {
            val listFavoriteCoins = dataSharedPreferences.toMutableList()
            listFavoriteCoins.add(itemToStore)
            sharedPreferences.FavoriteCoins = Json.encodeToString(listFavoriteCoins)
        }

        cacheRateLimit.removeForKey(sharedPreferences, CACHE_KEY_PRICE_COINS)
    }

    override fun removeFavouriteCoin(id: String, name: String) {

        val itemToRemove = FavoriteCoinEntity(id = id, name = name.lowercase())
        val dataSharedPreferences = sharedPreferences.getListFavoriteCoins()

        if (dataSharedPreferences.contains(itemToRemove)) {
            val listFavoriteCoins = dataSharedPreferences.toMutableList()
            listFavoriteCoins.remove(itemToRemove)
            sharedPreferences.FavoriteCoins = Json.encodeToString(listFavoriteCoins)
        }

        cacheRateLimit.removeForKey(sharedPreferences, CACHE_KEY_PRICE_COINS)
    }

    override fun getFavouriteCoins(): Result<ListFavouriteCoins> {
        val data = Result.runCatching {
            sharedPreferences.getListFavoriteCoins()
        }
        return data.mapCatching { favouriteCoinsMapper.map(it) }
    }


    private fun clearKeysForMarketChart() {
        val listFavoriteCoins = sharedPreferences.getListFavoriteCoins()

        if (listFavoriteCoins.isNotEmpty()) {
            listFavoriteCoins.forEach {
                cacheRateLimit.removeForKey(sharedPreferences, BASE_CACHE_KET_MARKET_CHART + it.id)
            }
        }
    }
}