package com.krakert.tracker.state

import com.krakert.tracker.models.*

sealed class ViewStateOverview {
    // Represents different states for the ListCoins screen
    object Empty : ViewStateOverview()
    object Loading : ViewStateOverview()
    data class Success(val favorite: FavoriteCoins) : ViewStateOverview()
    data class Error(val exception: Throwable) : ViewStateOverview()
}

sealed class ViewStateDataCoins {
    object Loading : ViewStateDataCoins()
    data class Success(val data: List<String>) : ViewStateDataCoins()
    data class Error(val exception: Throwable) : ViewStateDataCoins()
}

sealed class ViewStateDetailsCoins {
    object Empty : ViewStateDetailsCoins()
    object Loading : ViewStateDetailsCoins()
    data class Success(val details: CoinFullData) : ViewStateDetailsCoins()
    data class Error(val exception: Throwable) : ViewStateDetailsCoins()
}