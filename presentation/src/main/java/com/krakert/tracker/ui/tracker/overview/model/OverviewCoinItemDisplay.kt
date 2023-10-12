package com.krakert.tracker.ui.tracker.overview.model

import androidx.compose.ui.graphics.Path

data class OverviewCoinItemDisplay(
    val id: String,
    val name: String,
    val currentPrice: String,
    val marketChart: Path
)
