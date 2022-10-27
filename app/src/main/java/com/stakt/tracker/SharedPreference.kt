package com.stakt.tracker

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import com.stakt.tracker.models.ui.FavoriteCoin
import java.lang.reflect.Type

object SharedPreference {
    private const val currency = "CURRENCY"
    private const val amountDaysTracking = "DAY_TRACKING"
    private const val favoriteCoin = "FAVORITE_COIN"
    private const val favoriteCoins = "FAVORITE_COINS"
    private const val minutesCache = "MINUTES_CACHE"

    fun sharedPreference(context: Context): SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    private inline fun SharedPreferences.editMe(operation: (SharedPreferences.Editor) -> Unit) {
        val editMe = edit()
        operation(editMe)
        editMe.apply()
    }

    var SharedPreferences.Currency
        get() = getString(currency, "eur")
        set(value) {
            editMe {
                it.putString(currency, value)
            }
        }

    var SharedPreferences.AmountDaysTracking
        get() = getInt(amountDaysTracking, 7)
        set(value) {
            editMe {
                it.putInt(amountDaysTracking, value)
            }
        }

    var SharedPreferences.FavoriteCoin
        get() = getString(favoriteCoin, "")
        set(value) {
            editMe {
                it.putString(favoriteCoin, value)
            }
        }

    fun SharedPreferences.getFavoriteCoinList(): ArrayList<FavoriteCoin> {
        val dataSharedPreference = FavoriteCoins.toString()
        val typeOfT: Type = object : TypeToken<ArrayList<FavoriteCoin>>() {}.type
        var listFavoriteCoins = arrayListOf<FavoriteCoin>()

        if (dataSharedPreference.isNotEmpty()) {
            listFavoriteCoins = Gson().fromJson(dataSharedPreference, typeOfT)
        }
        return listFavoriteCoins
    }

    var SharedPreferences.FavoriteCoins
        get() = getString(favoriteCoins, "")
        set(value) {
            editMe {
                it.putString(favoriteCoins, value)
            }
        }

    var SharedPreferences.MinutesCache
        get() = getInt(minutesCache, 5)
        set(value) {
            editMe {
                it.putInt(minutesCache, value)
            }
        }

}