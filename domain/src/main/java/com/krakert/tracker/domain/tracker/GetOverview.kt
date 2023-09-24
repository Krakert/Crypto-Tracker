package com.krakert.tracker.domain.tracker

import com.krakert.tracker.domain.tracker.model.CoinOverview
import javax.inject.Inject

class GetOverview @Inject constructor(
    private val trackerRepository: TrackerRepository
) {
    suspend operator fun invoke(): Result<CoinOverview> = trackerRepository.getOverview()
}