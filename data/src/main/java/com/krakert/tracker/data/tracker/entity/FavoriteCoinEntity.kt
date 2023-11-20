package com.krakert.tracker.data.tracker.entity

import kotlinx.serialization.Serializable

@Serializable
data class FavoriteCoinEntity(
    val id: String?,
    val name: String?,
)
