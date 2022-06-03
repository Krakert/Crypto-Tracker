package com.krakert.tracker.navigation

sealed class Screen(val route: String) {
    object Overview : Screen("overview_coins")
    object Add : Screen("add_coin")
    object Details : Screen("details_coin")
    object Settings : Screen("settings_app")
}