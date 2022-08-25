package com.krakert.tracker

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

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
        get() = getFloat(amountDaysTracking, 7.0F)
        set(value) {
            editMe {
                it.putFloat(amountDaysTracking, value)
            }
        }

    var SharedPreferences.FavoriteCoin
        get() = getString(favoriteCoin, "")
        set(value) {
            editMe {
                it.putString(favoriteCoin, value)
            }
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

    @Suppress("UNUSED_PARAMETER")
    var SharedPreferences.ClearValues
        get() = { }
        set(value) {
            editMe {
                it.clear()
            }
        }


}