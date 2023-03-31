package com.krakert.tracker.ui.tracker.settings

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.krakert.tracker.data.SharedPreference.AmountDaysTracking
import com.krakert.tracker.data.SharedPreference.Currency
import com.krakert.tracker.data.SharedPreference.MinutesCache
import com.krakert.tracker.data.components.storage.CacheRateLimiter
import com.krakert.tracker.data.tracker.TrackerRepositoryImpl.Companion.CACHE_KEY_DETAILS_COIN
import com.krakert.tracker.data.tracker.TrackerRepositoryImpl.Companion.CACHE_KEY_PRICE_COINS
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class TrackerSettingsViewModel
@Inject constructor(
    private val sharedPreferences: SharedPreferences,
//    private val cryptoRoomDb: CryptoDatabase,
) : ViewModel() {

    private val cacheRateLimit = CacheRateLimiter<String>(sharedPreferences.MinutesCache, TimeUnit.MINUTES)

    fun resetSettings() {
        sharedPreferences.edit().clear().apply()
        CoroutineScope(Dispatchers.IO).launch {
//            cryptoRoomDb.clearAllTables()
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
           this.MinutesCache = minutesCache
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
//        val typeOfT: Type = object : TypeToken<ArrayList<FavoriteCoin>>() {}.type
//        val listFavoriteCoins: ArrayList<FavoriteCoin> = Gson().fromJson(sharedPreferences.FavoriteCoins, typeOfT)
//
//        if (listFavoriteCoins.isNotEmpty()){
//            listFavoriteCoins.forEach {
//                cacheRateLimit.removeForKey(sharedPreferences, BASE_CACHE_KET_MARKET_CHART + it.id)
//            }
//        }
    }
}