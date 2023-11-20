package com.krakert.tracker.domain.tracker

import com.krakert.tracker.domain.tracker.model.Preferences
import javax.inject.Inject

class GetAllPreferences @Inject constructor(
    private val repository: PreferencesRepository
) {
    operator fun invoke(): Preferences = repository.getAllPreferences()
}