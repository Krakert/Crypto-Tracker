package com.krakert.tracker.domain.tracker

import javax.inject.Inject

class SetAmountDaysTracking @Inject constructor(
    private val repository: PreferencesRepository,
) {
    operator fun invoke(days: Int) = repository.setAmountDaysTracking(days)
}