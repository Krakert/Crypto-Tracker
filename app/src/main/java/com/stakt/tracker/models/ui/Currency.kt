package com.stakt.tracker.models.ui

enum class Currency(val nameFull: List<String>) {
    usd(listOf("Dollar","$")),
    eur(listOf("Euro", "€")),
    gbp(listOf("Pound", "£"))
}