package com.stakt.tracker.api

import android.content.SharedPreferences
import android.os.SystemClock
import android.util.ArrayMap
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

import java.util.concurrent.TimeUnit

/**
 * Utility class that decides whether we should fetch some data or not.
 * Expanded this class with disk stored timestamps
 */
class CacheRateLimiter<in KEY>(timeout: Int, timeUnit: TimeUnit) {
    private val mGson = Gson()
    private val TAG = "CacheRateLimiter"
    private val mTimeStamps = ArrayMap<KEY, Long>()
    private val mTimeout = timeUnit.toMillis(timeout.toLong())

    companion object {
        const val KEY_CACHE_TIMESTAMPS = "key_cache_timestamps"
    }

    @Synchronized
    fun shouldFetch(key: KEY, prefs: SharedPreferences): Boolean {
        val lastFetched = getTimeStampForKey(prefs, key)

        val now = now()
        if (lastFetched == null) {
            mTimeStamps[key] = now
            storeTimeStamps(prefs, key, now)

            return true
        }
        if (now - lastFetched > mTimeout) {
            Log.i(TAG,String.format("cache expired for key %s", key))
            mTimeStamps[key] = now
            storeTimeStamps(prefs, key, now)
            return true
        }

        Log.i(TAG, String.format("Cache valid for key %s: ", key))

        return false
    }

    private fun now() = SystemClock.uptimeMillis()

    private fun storeTimeStamps(sharedPreferences: SharedPreferences, key: KEY, time: Long?) {
        //get from shared prefs
        val arrayMap = getStoredTimestamps(sharedPreferences)

        arrayMap?.let {
            arrayMap[key] = time

            writeToPrefs(sharedPreferences, arrayMap)
        }
    }

    private fun writeToPrefs(sharedPreferences: SharedPreferences, arrayMap: ArrayMap<KEY, Long>) {
        //convert to string using gson
        val arrayMapJson = mGson.toJson(arrayMap)

        //save in shared prefs
        sharedPreferences.edit().putString(KEY_CACHE_TIMESTAMPS, arrayMapJson).apply()
    }

    private fun getTimeStampForKey(prefs: SharedPreferences, key: KEY): Long? {
        //get from shared prefs
        val arrayMap: ArrayMap<KEY, Long> = getStoredTimestamps(prefs) ?: return null

        return arrayMap[key]
    }


    private fun getStoredTimestamps(sharedPreferences: SharedPreferences): ArrayMap<KEY, Long> {
        val storedTimeStamps = sharedPreferences.getString(KEY_CACHE_TIMESTAMPS, null)
                ?: return ArrayMap()

        val type = object : TypeToken<ArrayMap<String, Long>>() {
        }.type

        return mGson.fromJson(storedTimeStamps, type) ?: return ArrayMap()
    }

    fun removeForKey(prefs: SharedPreferences, key: KEY) {
        val timestamps: ArrayMap<KEY, Long> = getStoredTimestamps(prefs) ?: return

        timestamps.remove(key)

        writeToPrefs(prefs, timestamps)

    }

    fun removeAll(prefs: SharedPreferences) {
        prefs.edit().remove(KEY_CACHE_TIMESTAMPS).apply()
    }

}
