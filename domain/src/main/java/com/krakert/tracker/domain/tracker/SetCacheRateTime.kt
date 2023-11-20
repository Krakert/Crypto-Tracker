package com.krakert.tracker.domain.tracker

import javax.inject.Inject

class SetCacheRateTime @Inject constructor(
    private val repository: PreferencesRepository,
) {
    operator fun invoke(minutes: Int) = repository.setCacheRate(minutes)
}