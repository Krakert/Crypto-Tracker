package com.krakert.tracker.ui.tracker.add.mapper

import com.krakert.tracker.domain.tracker.model.ListCoins
import com.krakert.tracker.ui.tracker.add.model.ListCoinsDisplay
import javax.inject.Inject

class ListCoinsDisplayMapper @Inject constructor(
    private val listCoinsItemDisplayMapper: ListCoinsItemDisplayMapper,
) {
    fun map(coins: ListCoins): ListCoinsDisplay {
        return ListCoinsDisplay(
            result = coins.result.map(listCoinsItemDisplayMapper::map)
        )
    }
}
