package com.krakert.tracker.app.application

import android.app.Application
import com.google.firebase.analytics.FirebaseAnalytics
import com.krakert.tracker.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

/**
 * Application class
 */
@HiltAndroidApp
class AppClass : Application() {
    override fun onCreate() {
        super.onCreate()

        initializeApp()
    }

    private fun initializeApp() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        FirebaseAnalytics.getInstance(applicationContext).setAnalyticsCollectionEnabled(!BuildConfig.DEBUG)
    }
}