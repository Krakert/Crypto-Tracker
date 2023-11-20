package com.krakert.tracker.domain.tracker

import javax.inject.Inject

class CheckFavouriteCoins @Inject constructor(
    private val repository: PreferencesRepository,
) {
    operator fun invoke(): Boolean = repository.favouriteCoinsIsEmpty()
}