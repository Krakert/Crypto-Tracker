package com.krakert.tracker.data.tracker.entity.database

import androidx.room.ColumnInfo

data class DBValueInCurrencyEntity(
    @ColumnInfo(name = "aud") val aud: Double?,
    @ColumnInfo(name = "chf") val chf: Double?,
    @ColumnInfo(name = "cny") val cny: Double?,
    @ColumnInfo(name = "cad") val cad: Double?,
    @ColumnInfo(name = "eur") val eur: Double?,
    @ColumnInfo(name = "gbp") val gbp: Double?,
    @ColumnInfo(name = "jpy") val jpy: Double?,
    @ColumnInfo(name = "usd") val usd: Double?
)