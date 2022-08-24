package com.krakert.tracker.ui.state

import com.krakert.tracker.api.Resource
import com.krakert.tracker.models.*
import com.krakert.tracker.models.responses.CoinFullData

sealed class ViewStateAddCoin {
    // Represents different states for the add coin screen
    object Empty : ViewStateAddCoin()
    object Loading : ViewStateAddCoin()
    data class Success(val coins: Resource<ListCoins>) : ViewStateAddCoin()
    data class Error(val exception: String) : ViewStateAddCoin()
}

sealed class ViewStateOverview {
    // Represents different states for the overview screen
    object Empty : ViewStateOverview()
    data class Loading (val favorite: ArrayList<FavoriteCoin>) : ViewStateOverview()
    data class Loaded (val httpResponse: Resource<MutableMap<String, MutableMap<String, Any>>>) : ViewStateOverview()
    data class Error(val exception: String) : ViewStateOverview()
}

sealed class ViewStateDetailsCoins {
    object Empty : ViewStateDetailsCoins()
    object Loading : ViewStateDetailsCoins()
    data class Success(val details: Resource<CoinFullData>) : ViewStateDetailsCoins()
    data class Error(val exception: String) : ViewStateDetailsCoins()
}