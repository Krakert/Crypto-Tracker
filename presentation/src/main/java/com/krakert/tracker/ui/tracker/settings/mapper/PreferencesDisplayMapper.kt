package com.krakert.tracker.ui.tracker.settings.mapper

import com.krakert.tracker.domain.tracker.model.Preferences
import com.krakert.tracker.ui.tracker.model.Currency
import com.krakert.tracker.ui.tracker.settings.model.SettingsDisplay
import javax.inject.Inject

class PreferencesDisplayMapper @Inject constructor() {

    fun map(preferences: Preferences): SettingsDisplay {
        return SettingsDisplay(
            daysOfTracking = preferences.marketChartAmountDays,
            minutesOfCache = preferences.cacheRateTime,
            currency = Currency.values().find { it.name.lowercase() == preferences.currency } ?: Currency.Eur
        )
    }
}
