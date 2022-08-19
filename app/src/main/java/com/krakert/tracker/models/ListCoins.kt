package com.krakert.tracker.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * This classed is used to store all the data for each coin in the "add coin" screen of the app
 */
class ListCoins : ArrayList<Coin>()

@Parcelize
data class Coin(
    val id: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("name")
    val name: String,
) : Parcelable {
    fun getIcon() = image
}