package com.krakert.tracker.data.components.storage

import android.os.SystemClock
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Utility class that decides whether we should fetch some data or not.
 * Expanded this class with disk stored timestamps
 */
class CacheRateLimiter @Inject constructor(timeout: Int, timeUnit: TimeUnit) {
    private val TAG = "CacheRateLimiter"
    private val mTimeStamps = mutableMapOf<String, Long>()
    private val mTimeout = timeUnit.toMillis(timeout.toLong())

    @Synchronized
    fun shouldFetch(key: String): Boolean {
        val lastFetched = mTimeStamps[key]

        val now = now()
        if (lastFetched == null) {
            Timber.tag(TAG).i("No key stored for: $key")
            storeTimeStamps(key, now)
            return true
        }
        if (now - lastFetched > mTimeout) {
            Timber.tag(TAG).i("cache expired for key: $key")
            storeTimeStamps(key, now)
            return true
        }

        Timber.tag(TAG).i("cache valid for key: $key")

        return false
    }

    private fun now() = SystemClock.uptimeMillis()

    private fun storeTimeStamps(key: String, time: Long) {
        mTimeStamps[key] = time
    }

    fun removeForKey(key: String) {
        mTimeStamps.remove(key)
    }

    fun removeAll() {
        mTimeStamps.clear()
    }

}