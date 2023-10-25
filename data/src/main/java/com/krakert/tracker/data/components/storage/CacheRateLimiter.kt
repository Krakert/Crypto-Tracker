package com.krakert.tracker.data.components.storage

import android.content.SharedPreferences
import android.os.SystemClock
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import timber.log.Timber
import java.util.concurrent.TimeUnit

/**
 * Utility class that decides whether we should fetch some data or not.
 * Expanded this class with disk stored timestamps
 */
class CacheRateLimiter(timeout: Int, timeUnit: TimeUnit) {
    private val TAG = "CacheRateLimiter"
    private val mTimeStamps = mutableMapOf<String, Long>()
    private val mTimeout = timeUnit.toMillis(timeout.toLong())

    companion object {
        const val KEY_CACHE_TIMESTAMPS = "key_cache_timestamps"
    }

    @Synchronized
    fun shouldFetch(key: String, prefs: SharedPreferences): Boolean {
        val lastFetched = getTimeStampForKey(prefs, key)

        val now = now()
        if (lastFetched == null) {
            mTimeStamps[key] = now
            storeTimeStamps(prefs, key, now)

            return true
        }
        if (now - lastFetched > mTimeout) {
            Timber.tag(TAG).i("cache expired for key: $key")
            mTimeStamps[key] = now
            storeTimeStamps(prefs, key, now)
            return true
        }

        Timber.tag(TAG).i("cache valid for key: $key")

        return false
    }

    private fun now() = SystemClock.uptimeMillis()

    private fun storeTimeStamps(sharedPreferences: SharedPreferences, key: String, time: Long) {
        //get from shared prefs
        val arrayMap = getStoredTimestamps(sharedPreferences)

        arrayMap.let {
            arrayMap[key] = time

            writeToPrefs(sharedPreferences, arrayMap)
        }
    }

    private fun writeToPrefs(
        sharedPreferences: SharedPreferences,
        arrayMap: MutableMap<String, Long>
    ) {
        //convert to string using gson
        val arrayMapJson = Json.encodeToString(arrayMap.toMap())

        //save in shared prefs
        sharedPreferences.edit().putString(KEY_CACHE_TIMESTAMPS, arrayMapJson).apply()
    }

    private fun getTimeStampForKey(prefs: SharedPreferences, key: String): Long? {
        //get from shared prefs
        val arrayMap: MutableMap<String, Long> = getStoredTimestamps(prefs)

        return arrayMap[key]
    }


    private fun getStoredTimestamps(sharedPreferences: SharedPreferences): MutableMap<String, Long> {
        val storedTimeStamps = sharedPreferences.getString(KEY_CACHE_TIMESTAMPS, null)
            ?: return mutableMapOf()

        return Json.decodeFromString(storedTimeStamps) ?: return mutableMapOf()
    }

    fun removeForKey(prefs: SharedPreferences, key: String) {
        val timestamps: MutableMap<String, Long> = getStoredTimestamps(prefs)

        timestamps.remove(key)

        writeToPrefs(prefs, timestamps)

    }

    fun removeAll(prefs: SharedPreferences) {
        prefs.edit().remove(KEY_CACHE_TIMESTAMPS).apply()
    }

}