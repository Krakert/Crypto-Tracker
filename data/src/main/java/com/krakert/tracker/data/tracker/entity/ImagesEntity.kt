package com.krakert.tracker.data.tracker.entity

import kotlinx.serialization.Serializable

@Serializable
data class ImagesEntity(
    val thumb: String?,
    val small: String?,
    val large: String?,
)