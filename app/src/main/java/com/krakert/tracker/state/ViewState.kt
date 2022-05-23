package com.krakert.tracker.state

import com.krakert.tracker.model.Coins

sealed class ViewState {
    // Represents different states for the ListCoins screen
    object Empty : ViewState()
    object Loading : ViewState()
    data class Success(val coins: Coins) : ViewState()
    data class Error(val exception: Throwable) : ViewState()
}