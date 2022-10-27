package com.stakt.tracker.ui.viewmodel

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import com.stakt.tracker.SharedPreference.AmountDaysTracking
import com.stakt.tracker.SharedPreference.Currency
import com.stakt.tracker.SharedPreference.FavoriteCoins
import com.stakt.tracker.SharedPreference.MinutesCache
import com.stakt.tracker.api.CacheRateLimiter
import com.stakt.tracker.database.CryptoDatabase
import com.stakt.tracker.models.ui.FavoriteCoin
import com.stakt.tracker.repository.BASE_CACHE_KET_MARKET_CHART
import com.stakt.tracker.repository.CACHE_KEY_DETAILS_COIN
import com.stakt.tracker.repository.CACHE_KEY_PRICE_COINS
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.reflect.Type
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel
@Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val cryptoRoomDb: CryptoDatabase,
) : ViewModel() {

    private val cacheRateLimit = CacheRateLimiter<String>(sharedPreferences.MinutesCache, TimeUnit.MINUTES)

    fun resetSettings() {
        sharedPreferences.edit().clear().apply()
        CoroutineScope(Dispatchers.IO).launch {
            cryptoRoomDb.clearAllTables()
        }
    }

    fun setAmountDaysTracking(amountDaysTracking: Int) {
        sharedPreferences.apply {
            this.AmountDaysTracking = amountDaysTracking
        }
        clearKeysForMarketChart()
    }

    fun setCacheRate(minutesCache: Int) {
        sharedPreferences.apply {
           this.AmountDaysTracking = minutesCache
        }
    }

    fun setCurrency(currency: String) {
        sharedPreferences.apply {
            this.Currency = currency
        }
        cacheRateLimit.removeForKey(sharedPreferences, CACHE_KEY_PRICE_COINS)
        cacheRateLimit.removeForKey(sharedPreferences, CACHE_KEY_DETAILS_COIN)
        clearKeysForMarketChart()
    }

    private fun clearKeysForMarketChart(){
        val typeOfT: Type = object : TypeToken<ArrayList<FavoriteCoin>>() {}.type
        val listFavoriteCoins: ArrayList<FavoriteCoin> = Gson().fromJson(sharedPreferences.FavoriteCoins, typeOfT)

        if (listFavoriteCoins.isNotEmpty()){
            listFavoriteCoins.forEach {
                cacheRateLimit.removeForKey(sharedPreferences, BASE_CACHE_KET_MARKET_CHART + it.id)
            }
        }
    }
}