package com.krakert.tracker.data

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.krakert.tracker.data.tracker.entity.FavoriteCoinEntity
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

object SharedPreference {
    private const val currency = "CURRENCY"
    private const val amountDaysTracking = "DAY_TRACKING"
    private const val tileCoin = "TILE_COIN"
    private const val favoriteCoins = "FAVORITE_COINS"
    private const val minutesCache = "MINUTES_CACHE"

    fun sharedPreference(context: Context): SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(context)

    private inline fun SharedPreferences.editMe(operation: (SharedPreferences.Editor) -> Unit) {
        val editMe = edit()
        operation(editMe)
        editMe.apply()
    }

    var SharedPreferences.Currency
        get() = getString(currency, "eur").toString()
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

    var SharedPreferences.TileCoin
        get() = getString(tileCoin, "").toString()
        set(value) {
            editMe {
                it.putString(tileCoin, value)
            }
        }

    fun SharedPreferences.getListFavoriteCoins(): List<FavoriteCoinEntity> {
        val dataSharedPreference = FavoriteCoins
        val listFavoriteCoins = arrayListOf<FavoriteCoinEntity>()

        if (dataSharedPreference.isEmpty()) {
            return listFavoriteCoins
        }
        return Json.decodeFromString(FavoriteCoins)

    }

    var SharedPreferences.FavoriteCoins
        get() = getString(favoriteCoins, "").toString()
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