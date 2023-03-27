package com.krakert.tracker.data.components.tracker.entity.database

import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class DBImagesEntity(
//    @PrimaryKey val id: Long,
    @SerializedName("thumb") val thumb: String?,
    @SerializedName("small") val small: String?,
    @SerializedName("large") val large: String?,
)
