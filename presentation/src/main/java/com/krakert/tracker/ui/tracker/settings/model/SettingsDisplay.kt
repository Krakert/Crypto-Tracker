package com.krakert.tracker.ui.tracker.settings.model

import com.krakert.tracker.ui.tracker.model.Currency

data class SettingsDisplay(
    val daysOfTracking: Int,
    val minutesOfCache: Int,
    val currency: Currency,
)
