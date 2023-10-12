package com.krakert.tracker.ui.tracker.overview.mapper

import com.krakert.tracker.domain.tracker.model.CoinOverview
import com.krakert.tracker.ui.tracker.overview.model.OverviewCoinDisplay
import javax.inject.Inject

class OverviewCoinDisplayMapper @Inject constructor(
    private val itemDisplayMapper: OverviewCoinItemDisplayMapper
) {
    fun map(item: CoinOverview): OverviewCoinDisplay {
        return OverviewCoinDisplay(
            result = item.result.map(itemDisplayMapper::map),
            tileCoin = item.tileCoin
        )
    }
}