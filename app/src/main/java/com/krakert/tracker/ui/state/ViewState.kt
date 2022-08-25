package com.krakert.tracker.ui.state

import com.krakert.tracker.api.Resource
import com.krakert.tracker.models.responses.ListCoins

sealed class ViewStateAddCoin {
    // Represents different states for the add coin screen
    object Empty : ViewStateAddCoin()
    object Loading : ViewStateAddCoin()
    data class Success(val coins: Resource<ListCoins>) : ViewStateAddCoin()
    data class Error(val exception: String) : ViewStateAddCoin()
}