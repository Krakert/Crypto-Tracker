package com.krakert.tracker.data.tracker.mapper

import android.content.SharedPreferences
import com.krakert.tracker.data.SharedPreference.Currency
import com.krakert.tracker.data.extension.requireNotNull
import javax.inject.Inject

class CurrencyMapper @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {

    fun map(entity: Map<String, Double>?): Double {
        return entity?.get(sharedPreferences.Currency).requireNotNull()
    }
}
