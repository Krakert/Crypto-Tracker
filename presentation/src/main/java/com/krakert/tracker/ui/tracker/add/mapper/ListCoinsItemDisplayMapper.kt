package com.krakert.tracker.ui.tracker.add.mapper

import android.content.SharedPreferences
import com.krakert.tracker.domain.tracker.model.ListCoinsItem
import com.krakert.tracker.ui.tracker.add.model.ListCoinsItemDisplay
import javax.inject.Inject

class ListCoinsItemDisplayMapper @Inject constructor(
    private val sharedPreferences: SharedPreferences,
) {
    fun map(item: ListCoinsItem): ListCoinsItemDisplay {
        with(item) {
            return ListCoinsItemDisplay(
                id = id,
                name = name.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() },
                imageUrl = imageUrl,
                isFavourite = false // Todo: Add Preferences
            )
        }
    }
}
