package com.krakert.tracker.data.components.storage

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SharedPreference @Inject constructor(
    @ApplicationContext context: Context
) {

    private val preferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE)

    companion object {
        private const val NAME = "StorageDataRepository"
    }

    private fun editPreference(fn: SharedPreferences.Editor.() -> Unit) = with(preferences.edit()) {
        fn()
        apply()
    }
}