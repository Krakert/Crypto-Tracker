package com.krakert.tracker.domain.tracker

import com.krakert.tracker.domain.tracker.model.CoinDetails
import javax.inject.Inject

class GetDetailsCoin @Inject constructor(
    private val trackerRepository: TrackerRepository
) {
    suspend operator fun invoke(coinId: String): Result<CoinDetails> = trackerRepository.getDetailsCoin(coinId = coinId)
}