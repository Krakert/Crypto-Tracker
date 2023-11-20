package com.krakert.tracker.domain.tracker

import javax.inject.Inject

class AddFavouriteCoin @Inject constructor(
    private val repository: PreferencesRepository,
) {
    operator fun invoke(id: String, name: String) = repository.addFavouriteCoin(id = id, name = name)
}