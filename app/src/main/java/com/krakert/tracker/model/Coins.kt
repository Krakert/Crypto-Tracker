package com.krakert.tracker.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class Coins (
    val Coins: List<Coin>? = null
)
@Parcelize
data class Coin (
    val id: String? = null,
    val idCoin: String? = null,
    val name: String? = null,
    val symbol: String? = null
) : Parcelable
