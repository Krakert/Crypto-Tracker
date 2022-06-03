package com.krakert.tracker.model

enum class Currency(val nameFull: List<String>) {
    USD(listOf("Dollar","$")),
    EUR(listOf("Euro", "€")),
    GBP(listOf("Pound", "£"))
}