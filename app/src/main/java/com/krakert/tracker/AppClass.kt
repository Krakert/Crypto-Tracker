package com.krakert.tracker

import android.app.Application
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.hilt.android.HiltAndroidApp

/**
 * Application class
 */
@HiltAndroidApp
class AppClass : Application() {
    override fun onCreate() {
        super.onCreate()

        setupCrashlytics()
    }

    private fun setupCrashlytics() {
        FirebaseAnalytics.getInstance(applicationContext)
            .setAnalyticsCollectionEnabled(!BuildConfig.DEBUG)
    }
}