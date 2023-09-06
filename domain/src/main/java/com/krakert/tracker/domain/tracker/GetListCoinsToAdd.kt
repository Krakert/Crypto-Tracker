package com.krakert.tracker.domain.tracker

import com.krakert.tracker.domain.tracker.model.ListCoins
import javax.inject.Inject

class GetListCoinsToAdd @Inject constructor(
    private val repository: TrackerRepository,
) {
    suspend operator fun invoke(): Result<ListCoins> = repository.getListCoins()
}