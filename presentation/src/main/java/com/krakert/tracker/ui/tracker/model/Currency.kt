package com.krakert.tracker.ui.tracker.model

enum class Currency(val fullName: String, symbol: String) {
    Usd(fullName = "Dollar", symbol = "$"),
    Eur(fullName = "Euro", symbol = "€"),
    Jpy(fullName = "Japanese Yen", symbol = "¥"),
    Gbp(fullName = "Pound sterling", symbol = "£"),
    Aud(fullName = "Australian dollar", symbol = "$"),
    Cad(fullName = "Canadian dollar", symbol = "\$"),
    Chf(fullName = "Swiss franc", symbol = "CHF"),
    Cnh(fullName = "Chinese renminbi", symbol = "¥"),
}