package com.krakert.tracker.data.tracker.entity

import kotlinx.serialization.Serializable

@Serializable
data class ListCoinsItemEntity(
    val id: String?,
    val image: String?,
    val name: String?,
)