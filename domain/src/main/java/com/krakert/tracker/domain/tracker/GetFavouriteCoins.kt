package com.krakert.tracker.domain.tracker

import com.krakert.tracker.domain.tracker.model.ListFavouriteCoins
import javax.inject.Inject

class GetFavouriteCoins @Inject constructor(
    private val repository: PreferencesRepository,
) {
    operator fun invoke(): Result<ListFavouriteCoins> = repository.getFavouriteCoins()
}