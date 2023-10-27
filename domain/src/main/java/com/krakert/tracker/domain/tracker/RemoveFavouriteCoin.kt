package com.krakert.tracker.domain.tracker

import javax.inject.Inject


class RemoveFavouriteCoin @Inject constructor(
    private val repository: PreferencesRepository,
) {
    operator fun invoke(id: String, name: String) = repository.removeFavouriteCoin(id = id, name = name)
}