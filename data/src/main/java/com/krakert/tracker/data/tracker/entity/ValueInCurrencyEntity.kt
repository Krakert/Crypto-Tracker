package com.krakert.tracker.data.tracker.entity

import androidx.room.ColumnInfo

data class ValueInCurrencyEntity(
    val aud: Double?,
    val chf: Double?,
    val cny: Double?,
    val cad: Double?,
    val eur: Double?,
    val gbp: Double?,
    val jpy: Double?,
    val usd: Double?
)