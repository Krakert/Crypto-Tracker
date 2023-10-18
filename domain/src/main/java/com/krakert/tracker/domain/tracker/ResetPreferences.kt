package com.krakert.tracker.domain.tracker

import javax.inject.Inject

class ResetPreferences @Inject constructor(
    private val repository: PreferencesRepository,
) {
    operator fun invoke() = repository.resetSettings()
}