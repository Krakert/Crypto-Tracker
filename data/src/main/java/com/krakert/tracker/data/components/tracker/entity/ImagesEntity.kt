package com.krakert.tracker.data.components.tracker.entity

import com.google.gson.annotations.SerializedName

data class ImagesEntity(
    @SerializedName("thumb")
    val thumb: String?,
    @SerializedName("small")
    val small: String?,
    @SerializedName("large")
    val large: String?,
)