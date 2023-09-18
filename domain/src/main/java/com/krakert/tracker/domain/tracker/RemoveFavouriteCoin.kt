package com.krakert.tracker.domain.tracker

import javax.inject.Inject


class RemoveFavouriteCoin @Inject constructor(
    private val repository: TrackerRepository,
) {
    suspend operator fun invoke(id: String, name: String): Result<Boolean> = repository.removeFavouriteCoin(id = id, name = name)
}