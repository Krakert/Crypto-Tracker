package com.krakert.tracker.data.tracker

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import com.krakert.tracker.data.tracker.entity.FavoriteCoinEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.Currency
import javax.inject.Inject


class UserPreferencesRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) {
    private object PreferencesKeys {
        val CURRENCY = stringPreferencesKey("CURRENCY")
        val DAY_TRACKING = intPreferencesKey("DAY_TRACKING")
        val FAVORITE_COINS = stringPreferencesKey("FAVORITE_COINS")
        val MINUTES_CACHE = intPreferencesKey("MINUTES_CACHE")
    }

    fun getCurrency(): Flow<String> = dataStore.data.map { preferences ->
        preferences[PreferencesKeys.CURRENCY] ?: "eur"
    }

    suspend fun setCurrency(currency: String) = dataStore.edit { mutablePreferences ->
        mutablePreferences[PreferencesKeys.CURRENCY] = currency
    }

    fun getFavouriteCoins(): Flow<List<FavoriteCoinEntity>> = dataStore.data.map { preferences ->
        Json.decodeFromString(
            (preferences[PreferencesKeys.FAVORITE_COINS]
                ?: emptyList<FavoriteCoinEntity>()).toString()
        )
    }

    suspend fun setFavouriteCoins(list: List<FavoriteCoinEntity>) =
        dataStore.edit { mutablePreferences ->
            mutablePreferences[PreferencesKeys.FAVORITE_COINS] = Json.encodeToString(list)
        }

    fun getDaysOfChart(): Flow<Int> = dataStore.data.map {preferences ->
        preferences[PreferencesKeys.DAY_TRACKING] ?: 5
    }

    suspend fun setDaysOfChart(days: Int) = dataStore.edit { mutablePreferences ->
        mutablePreferences[PreferencesKeys.DAY_TRACKING] = days
    }

    fun getCacheTimeSpan(): Flow<Int> = dataStore.data.map { preferences ->
        preferences[PreferencesKeys.MINUTES_CACHE] ?: 10
    }

    suspend fun setCacheTimeSpan(minutes: Int) = dataStore.edit {mutablePreferences ->
        mutablePreferences[PreferencesKeys.MINUTES_CACHE] = minutes
    }

}