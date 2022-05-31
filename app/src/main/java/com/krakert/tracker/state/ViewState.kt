package com.krakert.tracker.state

import com.krakert.tracker.model.DataCoin
import com.krakert.tracker.model.DataDetailsCoin
import com.krakert.tracker.model.FavoriteCoins

sealed class ViewStateAddCoin {
    // Represents different states for the ListCoins screen
    object Empty : ViewStateAddCoin()
    object Loading : ViewStateAddCoin()
    data class Success(val coins: FavoriteCoins) : ViewStateAddCoin()
    data class Error(val exception: Throwable) : ViewStateAddCoin()
}

sealed class ViewStateOverview {
    // Represents different states for the ListCoins screen
    object Empty : ViewStateOverview()
    object Loading : ViewStateOverview()
    data class Success(val favorite: FavoriteCoins) : ViewStateOverview()
    data class Error(val exception: Throwable) : ViewStateOverview()
}

sealed class ViewStateDataCoins {
    object Loading : ViewStateDataCoins()
    data class Success(val data: List<DataCoin>) : ViewStateDataCoins()
    data class Error(val exception: Throwable) : ViewStateDataCoins()
}

sealed class ViewStateDetailsCoins {
    object Loading : ViewStateDetailsCoins()
    data class Success(val details: DataDetailsCoin) : ViewStateDetailsCoins()
    data class Error(val exception: Throwable) : ViewStateDetailsCoins()
}