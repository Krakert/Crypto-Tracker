package com.krakert.tracker.domain.tracker

import com.krakert.tracker.domain.tracker.model.CoinDetails
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDetailsCoin @Inject constructor(
    private val trackerRepository: TrackerRepository
) {
    suspend operator fun invoke(coinId: String): Flow<Result<CoinDetails>> = trackerRepository.getDetailsCoin(coinId = coinId)
}