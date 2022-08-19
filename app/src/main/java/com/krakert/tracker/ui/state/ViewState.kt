package com.krakert.tracker.ui.state

import com.krakert.tracker.api.Resource
import com.krakert.tracker.models.*
import com.krakert.tracker.models.responses.CoinFullData

sealed class ViewStateAddCoin {
    // Represents different states for the add coin screen
    object Empty : ViewStateAddCoin()
    object Loading : ViewStateAddCoin()
    data class Success(val coins: Resource<ListCoins>) : ViewStateAddCoin()
    data class Error(val exception: Throwable) : ViewStateAddCoin()
}

sealed class ViewStateOverview {
    // Represents different states for the overview screen
    object Empty : ViewStateOverview()
    object Loading : ViewStateOverview()
    data class Success(val favorite: ArrayList<FavoriteCoin>) : ViewStateOverview()
    data class Error(val exception: Throwable) : ViewStateOverview()
}

sealed class ViewStateDataCoins {
    // Represents different states for the data that is shown on the overview screen
    object Loading : ViewStateDataCoins()
    data class Success(val data: Resource<MutableMap<String, MutableMap<String, Any>>>) : ViewStateDataCoins()
    data class Error(val exception: Throwable) : ViewStateDataCoins()
}

sealed class ViewStateDetailsCoins {
    object Empty : ViewStateDetailsCoins()
    object Loading : ViewStateDetailsCoins()
    data class Success(val details: Resource<CoinFullData>) : ViewStateDetailsCoins()
    data class Error(val exception: Throwable) : ViewStateDetailsCoins()
}