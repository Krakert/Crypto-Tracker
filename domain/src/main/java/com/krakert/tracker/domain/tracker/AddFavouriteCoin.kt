package com.krakert.tracker.domain.tracker

import javax.inject.Inject

class AddFavouriteCoin @Inject constructor(
    private val repository: TrackerRepository,
) {
    suspend operator fun invoke(id: String, name: String): Result<Boolean> = repository.addFavouriteCoin(id = id, name = name)
}