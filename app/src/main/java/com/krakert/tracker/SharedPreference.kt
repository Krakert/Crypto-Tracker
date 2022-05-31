package com.krakert.tracker

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

object SharedPreference {
    private const val currency = "CURRENCY"
    private const val amountDaysTracking = "DAY_TRACKING"

    fun sharedPreference(context: Context): SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    private inline fun SharedPreferences.editMe(operation: (SharedPreferences.Editor) -> Unit) {
        val editMe = edit()
        operation(editMe)
        editMe.apply()
    }

    var SharedPreferences.Currency
        get() = getString(currency, "")
        set(value) {
            editMe {
                it.putString(currency, value)
            }
        }

    var SharedPreferences.AmountDaysTracking
        get() = getFloat(amountDaysTracking, 0.0F)
        set(value) {
            println(value)
            editMe {
                it.putFloat(amountDaysTracking, value)
            }
        }

    @Suppress("UNUSED_PARAMETER")
    var SharedPreferences.clearValues
        get() = run { }
        set(value) {
            editMe {
                it.clear()
            }
        }
}