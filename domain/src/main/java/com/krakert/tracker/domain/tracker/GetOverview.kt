package com.krakert.tracker.domain.tracker

import com.krakert.tracker.domain.tracker.model.CoinOverview
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetOverview @Inject constructor(
    private val trackerRepository: TrackerRepository
) {
    suspend operator fun invoke(): Flow<Result<CoinOverview>> = trackerRepository.getOverview()
}