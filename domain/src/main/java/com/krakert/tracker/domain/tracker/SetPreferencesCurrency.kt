package com.krakert.tracker.domain.tracker

import javax.inject.Inject

class SetPreferencesCurrency @Inject constructor(
    private val repository: PreferencesRepository,
) {
    operator fun invoke(currency: String) = repository.setCurrency(currency)
}