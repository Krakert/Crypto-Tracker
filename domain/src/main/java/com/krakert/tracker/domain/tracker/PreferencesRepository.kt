package com.krakert.tracker.domain.tracker

import com.krakert.tracker.domain.tracker.model.ListFavouriteCoins
import com.krakert.tracker.domain.tracker.model.Preferences

interface PreferencesRepository {

    fun setAmountDaysTracking(days: Int)

    fun resetSettings()

    fun setCacheRate(minutes: Int)

    fun setCurrency(currency: String)

    fun getAllPreferences(): Preferences

    fun addFavouriteCoin(id: String, name: String)

    fun removeFavouriteCoin(id: String, name: String)

    fun getFavouriteCoins(): Result<ListFavouriteCoins>

    fun favouriteCoinsIsEmpty(): Boolean
}