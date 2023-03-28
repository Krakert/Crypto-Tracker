package com.krakert.tracker.ui.tracker.model

enum class Currency(val nameFull: List<String>) {
    usd(listOf("Dollar","$")),
    eur(listOf("Euro", "€")),
    gbp(listOf("Pound", "£"))
}