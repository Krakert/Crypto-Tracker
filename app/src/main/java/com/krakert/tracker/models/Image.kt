package com.krakert.tracker.models

data class Image(
    val thumb: String,
    val small: String,
    val large: String? = null,
)
