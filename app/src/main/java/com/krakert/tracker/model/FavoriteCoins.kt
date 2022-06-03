package com.krakert.tracker.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

// Result of the Firebase call
@Parcelize
data class FavoriteCoins (
    val Favorite: List<Coin>? = null
) : Parcelable