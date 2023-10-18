package com.krakert.tracker.data.tracker

import android.content.SharedPreferences
import com.krakert.tracker.data.SharedPreference.AmountDaysTracking
import com.krakert.tracker.data.SharedPreference.Currency
import com.krakert.tracker.data.SharedPreference.MinutesCache
import com.krakert.tracker.domain.tracker.PreferencesRepository
import com.krakert.tracker.domain.tracker.model.Preferences
import javax.inject.Inject

class PreferencesRepositoryImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences,
) : PreferencesRepository {
    override fun setAmountDaysTracking(days: Int) {
        sharedPreferences.AmountDaysTracking = days
    }

    override fun resetSettings() {
        sharedPreferences.edit().clear().apply()
    }

    override fun setCacheRate(minutes: Int) {
        sharedPreferences.MinutesCache = minutes
    }

    override fun setCurrency(currency: String) {
        sharedPreferences.Currency = currency.lowercase()
    }

    override fun getAllPreferences(): Preferences {
        return Preferences(
            marketChartAmountDays = sharedPreferences.AmountDaysTracking,
            currency = sharedPreferences.Currency,
            cacheRateTime = sharedPreferences.MinutesCache
        )
    }


}