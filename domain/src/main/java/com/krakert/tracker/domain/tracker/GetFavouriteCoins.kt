package com.krakert.tracker.domain.tracker

import com.krakert.tracker.domain.tracker.model.ListCoins
import javax.inject.Inject

class GetFavouriteCoins @Inject constructor(
    private val repository: TrackerRepository,
) {
//    suspend operator fun invoke(): Result<ListF> = repository.getListCoins()
}