package com.krakert.tracker.app.application

import android.app.Application
import com.krakert.tracker.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

/**
 * Application class
 */
@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()

//        setupCrashlytics()
        initializeApp()
    }

    private fun initializeApp() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    //    private fun setupCrashlytics() {
    //        FirebaseAnalytics.getInstance(applicationContext)
    //            .setAnalyticsCollectionEnabled(!BuildConfig.DEBUG)
    //    }
}